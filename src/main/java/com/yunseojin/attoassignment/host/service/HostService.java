package com.yunseojin.attoassignment.host.service;

import com.yunseojin.attoassignment.host.dto.HostRequest;
import com.yunseojin.attoassignment.host.dto.HostResponse;

import java.util.List;

public interface HostService {

    HostResponse createHost(HostRequest request);

    HostResponse updateHost(Long hostId, HostRequest request);

    void deleteHost(Long hostId);

    boolean isAliveHost(Long hostId);

    HostResponse getHost(Long hostId);

    List<HostResponse> getAllHosts();
}
