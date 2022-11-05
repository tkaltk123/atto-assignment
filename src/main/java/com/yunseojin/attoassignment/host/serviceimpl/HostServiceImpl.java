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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

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

    @Transactional
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

    @Transactional
    @Override
    public HostResponse updateHost(Long hostId, HostRequest request) {

        var host = hostService.getHostById(hostId);

        if (notNullAndNotEquals(host.getName(), request.getName()) && hostService.isDuplicateName(request.getName()))
            throw new RequestInputException(ErrorMessage.DUPLICATED_HOST_NAME_EXCEPTION);

        if (notNullAndNotEquals(host.getIp(), request.getIp()) && hostService.isDuplicateIp(request.getIp()))
            throw new RequestInputException(ErrorMessage.DUPLICATED_HOST_IP_EXCEPTION);

        host.update(request);

        return HostMapper.INSTANCE.toResponse(host);
    }

    @Transactional
    @Override
    public void deleteHost(Long hostId) {

        var host = hostService.getHostById(hostId);

        synchronized (countLock) {

            --hostCount;
        }

        try {
            hostService.deleteHost(host);
        } catch (Exception ex) {

            ++hostCount;
            throw ex;
        }
    }

    @Transactional
    @Override
    public boolean isAliveHost(Long hostId) {

        var host = hostService.getHostById(hostId);

        return host.getIsAlive();
    }

    @Override
    public HostResponse getHost(Long hostId) {

        var host = hostService.getHostById(hostId);

        return HostMapper.INSTANCE.toResponse(host);
    }

    @Override
    public List<HostResponse> getAllHosts() {

        var hosts = hostService.getAllHosts();
        List<HostResponse> responses = new ArrayList<>();
        try {

            ForkJoinPool myPool = new ForkJoinPool(hosts.size());
            responses = myPool.submit(() -> hosts.stream()
                            .parallel()
                            .map(HostMapper.INSTANCE::toResponse)
                            .collect(Collectors.toList()))
                    .get();
        } catch (Exception ignore) {
        }
        return responses;
    }

    private static boolean notNullAndNotEquals(String origin, String compare) {

        return compare != null && !origin.equals(compare);
    }
}
