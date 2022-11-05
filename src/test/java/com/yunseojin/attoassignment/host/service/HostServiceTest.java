package com.yunseojin.attoassignment.host.service;

import com.yunseojin.attoassignment.etc.enums.ErrorMessage;
import com.yunseojin.attoassignment.host.serviceimpl.HostServiceImpl;
import com.yunseojin.attoassignment.host.serviceimpl.InternalHostServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import static com.yunseojin.attoassignment.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HostServiceTest {

    @Mock
    private InternalHostServiceImpl internalHostService;

    @InjectMocks
    private HostServiceImpl hostService;

    @Order(1)
    @DisplayName("createHost 성공")
    @Test
    void createHost_success() {

        //given
        var request = getHostRequest("test", "127.0.0.1");

        //when
        var response = hostService.createHost(request);

        //then
        Mockito.verify(internalHostService, atMostOnce()).saveHost(Mockito.any());
        assertEquals("test", response.getName());
        assertEquals("127.0.0.1", response.getIp());
    }

    @Order(2)
    @DisplayName("createHost 이름 중복")
    @Test
    void createHost_throwException_nameDuplicated() {

        //given
        var request = getHostRequest("test", "127.0.0.1");

        //when
        when(internalHostService.isDuplicateName(any())).thenReturn(true);

        //then
        assertError(ErrorMessage.DUPLICATED_HOST_NAME_EXCEPTION, () -> {
            hostService.createHost(request);
        });
    }

    @Order(3)
    @DisplayName("createHost ip 중복")
    @Test
    void createHost_throwException_ipDuplicated() {

        //given
        var request = getHostRequest("test", "127.0.0.1");

        //when
        when(internalHostService.isDuplicateIp(any())).thenReturn(true);

        //then
        assertError(ErrorMessage.DUPLICATED_HOST_IP_EXCEPTION, () -> {
            hostService.createHost(request);
        });
    }

    @Order(4)
    @DisplayName("createHost 호스트 100개 초과")
    @Test
    void createHost_throwException_hostCountGreaterThen100() {

        //given
        createHostRepeat100();
        var request = getHostRequest("test", "127.0.0.1");

        //when

        //then
        assertError(ErrorMessage.LIMIT_OVER_HOST_EXCEPTION, () -> {
            hostService.createHost(request);
        });
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

    private void createHostRepeat100() {

        for (int i = 0; i < 100; ++i) {

            var request = getHostRequest("test" + i, "192.168.0." + i + 2);
            hostService.createHost(request);
        }
    }
}