package com.yunseojin.attoassignment.host.serviceimpl;

import com.yunseojin.attoassignment.etc.enums.ErrorMessage;
import com.yunseojin.attoassignment.etc.exception.RequestInputException;
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

    @Transactional
    @Override
    public HostEntity saveHost(HostEntity host) {

        return hostRepository.save(host);
    }

    @Transactional
    @Override
    public void deleteHost(HostEntity host) {

        hostRepository.delete(host);
    }

    @Override
    public HostEntity getHostById(Long hostId) {

        return hostRepository.findById(hostId)
                .orElseThrow(() -> new RequestInputException(ErrorMessage.HOST_NOT_FOUND_EXCEPTION));
    }

    @Override
    public boolean isDuplicateName(String name) {

        return hostRepository.existsByName(name);
    }

    @Override
    public boolean isDuplicateIp(String ip) {

        return hostRepository.existsByIp(ip);
    }

    @Override
    public List<HostEntity> getAllHosts() {

        return hostRepository.findAll();
    }

    @Override
    public int getHostsCount() {

        return hostRepository.getHostsCount();
    }
}
