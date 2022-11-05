package com.yunseojin.attoassignment.host.serviceimpl;

import com.yunseojin.attoassignment.host.dto.HostRequest;
import com.yunseojin.attoassignment.host.dto.HostResponse;
import com.yunseojin.attoassignment.host.mapper.HostMapper;
import com.yunseojin.attoassignment.host.service.HostService;
import com.yunseojin.attoassignment.host.service.InternalHostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HostServiceImpl implements HostService {

    private final InternalHostService hostService;
    private final HostMapper hostMapper;

    @Override
    public HostResponse createHost(HostRequest request) {

        return null;
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
