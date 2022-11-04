package com.yunseojin.attoassignment.host.service;

import com.yunseojin.attoassignment.host.dto.HostRequest;
import com.yunseojin.attoassignment.host.dto.HostResponse;

import java.util.List;

public interface HostService {

    void createHost(HostRequest request);

    void updateHost(Long hostId, HostRequest request);

    void deleteHost(Long hostId);

    Boolean isAliveHost(Long hostId);

    HostResponse getHost(Long hostId);

    List<HostResponse> getAllHosts();
}
