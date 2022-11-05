package com.yunseojin.attoassignment.host.service;

import com.yunseojin.attoassignment.host.entity.HostEntity;

import java.util.List;

public interface InternalHostService {

    void saveHost(HostEntity host);

    void deleteHost(HostEntity host);

    HostEntity getHostById(Long hostId);

    Boolean isDuplicateName(String name);

    Boolean isDuplicateIp(String ip);

    List<HostEntity> getAllHosts();
}
