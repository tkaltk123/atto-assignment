package com.yunseojin.attoassignment.host.serviceimpl;

import com.yunseojin.attoassignment.host.entity.HostEntity;
import com.yunseojin.attoassignment.host.repository.HostRepository;
import com.yunseojin.attoassignment.host.service.InternalHostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InternalHostServiceImpl implements InternalHostService {

    private final HostRepository hostRepository;

    @Override
    public void saveHost(HostEntity host) {

    }

    @Override
    public void deleteHost(HostEntity host) {

    }

    @Override
    public HostEntity getHostById(Long hostId) {
        return null;
    }

    @Override
    public boolean isDuplicateName(String name) {
        return false;
    }

    @Override
    public boolean isDuplicateIp(String ip) {
        return false;
    }

    @Override
    public List<HostEntity> getAllHosts() {
        return null;
    }
}
