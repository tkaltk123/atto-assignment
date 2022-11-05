package com.yunseojin.attoassignment.host.service;

import com.yunseojin.attoassignment.host.entity.HostEntity;

import java.util.List;

public interface InternalHostService {

    void saveHost(HostEntity host);

    void deleteHost(HostEntity host);

    HostEntity getHostById(Long hostId);

    boolean isDuplicateName(String name);

    boolean isDuplicateIp(String ip);

    List<HostEntity> getAllHosts();
}
