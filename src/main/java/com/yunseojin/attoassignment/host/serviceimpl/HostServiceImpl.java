package com.yunseojin.attoassignment.host.serviceimpl;

import com.yunseojin.attoassignment.etc.enums.ErrorMessage;
import com.yunseojin.attoassignment.etc.exception.RequestInputException;
import com.yunseojin.attoassignment.host.dto.HostRequest;
import com.yunseojin.attoassignment.host.dto.HostResponse;
import com.yunseojin.attoassignment.host.mapper.HostMapper;
import com.yunseojin.attoassignment.host.service.HostService;
import com.yunseojin.attoassignment.host.service.InternalHostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class HostServiceImpl implements HostService {

    private final InternalHostService hostService;

    private final Object countLock = new Object();

    private int hostCount;

    @Autowired
    public HostServiceImpl(InternalHostService internalHostService) {

        this.hostService = internalHostService;
        hostCount = hostService.getHostsCount();
    }

    @Override
    public HostResponse createHost(HostRequest request) {

        if (hostService.isDuplicateName(request.getName()))
            throw new RequestInputException(ErrorMessage.DUPLICATED_HOST_NAME_EXCEPTION);

        if (hostService.isDuplicateIp(request.getIp()))
            throw new RequestInputException(ErrorMessage.DUPLICATED_HOST_IP_EXCEPTION);

        synchronized (countLock) {

            if (hostCount >= 100)
                throw new RequestInputException(ErrorMessage.LIMIT_OVER_HOST_EXCEPTION);
            ++hostCount;
        }

        var host = HostMapper.INSTANCE.toEntity(request);
        try {
            host = hostService.saveHost(host);
        } catch (Exception ex) {

            --hostCount;
            throw ex;
        }

        return HostMapper.INSTANCE.toResponse(host);
    }

    @Override
    public HostResponse updateHost(Long hostId, HostRequest request) {

        return null;
    }

    @Override
    public void deleteHost(Long hostId) {

    }

    @Override
    public Boolean isAliveHost(Long hostId) {
        return null;
    }

    @Override
    public HostResponse getHost(Long hostId) {
        return null;
    }

    @Override
    public List<HostResponse> getAllHosts() {
        return null;
    }
}
