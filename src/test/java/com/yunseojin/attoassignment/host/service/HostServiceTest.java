package com.yunseojin.attoassignment.host.service;

import com.yunseojin.attoassignment.host.repository.HostRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
class HostServiceTest {

    @Mock
    private HostRepository hostRepository;

    @InjectMocks
    private HostService hostService;

    @Test
    void createHost_success() {
    }

    @Test
    void createHost_throwException_nameDuplicated() {
    }

    @Test
    void createHost_throwException_ipDuplicated() {
    }

    @Test
    void createHost_throwException_hostCountGreaterThen100() {
    }


    @Test
    void updateHost_success() {
    }

    @Test
    void updateHost_throwException_hostNotExists() {
    }

    @Test
    void updateHost_throwException_nameDuplicated() {
    }

    @Test
    void updateHost_throwException_ipDuplicated() {
    }

    @Test
    void deleteHost_success() {
    }

    @Test
    void deleteHost_throwException_hostNotExists() {
    }

    @Test
    void isAliveHost_true() {
    }

    @Test
    void isAliveHost_false() {
    }

    @Test
    void isAliveHost_hostNotExists() {
    }

    @Test
    void getHost_success() {
    }

    @Test
    void getHost_hostNotExists() {
    }

    @Test
    void getAllHosts_success() {
    }

    @Test
    void getAllHosts_successLessThen1000ms_hostCountEquals100() {
    }
}