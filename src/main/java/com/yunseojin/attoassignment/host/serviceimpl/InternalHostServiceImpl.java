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
    public Boolean isDuplicateName(String name) {
        return null;
    }

    @Override
    public Boolean isDuplicateIp(String ip) {
        return null;
    }

    @Override
    public List<HostEntity> getAllHosts() {
        return null;
    }
}
