package com.yunseojin.attoassignment.host.service;

import com.yunseojin.attoassignment.etc.enums.ErrorMessage;
import com.yunseojin.attoassignment.host.entity.HostEntity;
import com.yunseojin.attoassignment.host.repository.HostRepository;
import com.yunseojin.attoassignment.host.serviceimpl.InternalHostServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static com.yunseojin.attoassignment.TestUtil.assertError;
import static com.yunseojin.attoassignment.TestUtil.getHostEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InternalHostServiceTest {

    @Mock
    private HostRepository hostRepository;

    @InjectMocks
    private InternalHostServiceImpl internalHostService;

    @Order(1)
    @DisplayName("saveHost 성공")
    @Test
    void saveHost() {

        //given
        var host = getHostEntity("test", "127.0.0.1");

        //when
        internalHostService.saveHost(host);

        //then
        Mockito.verify(hostRepository, atMostOnce()).save(Mockito.any());
    }

    @Order(2)
    @DisplayName("deleteHost 성공")
    @Test
    void deleteHost() {

        //given
        var host = getHostEntity("test", "127.0.0.1");

        //when
        internalHostService.deleteHost(host);

        //then
        Mockito.verify(hostRepository, atMostOnce()).delete(Mockito.any());
    }

    @Order(3)
    @DisplayName("getHostById 성공")
    @Test
    void getHostById_success() {

        //given
        var host = getHostEntity("test", "127.0.0.1");

        //when
        when(hostRepository.findById(any())).thenReturn(Optional.of(host));
        var dbHost = internalHostService.getHostById(1L);

        //then
        assertEquals(host, dbHost);
    }

    @Order(4)
    @DisplayName("getHostById 호스트 없음")
    @Test
    void getHostById_throwException_hostNotFound() {

        //given

        //when

        //then
        assertError(ErrorMessage.HOST_NOT_FOUND_EXCEPTION, () -> {
            internalHostService.getHostById(1L);
        });
    }

    @Order(5)
    @DisplayName("isDuplicateName True")
    @Test
    void isDuplicateName_true_nameDuplicated() {

        //given

        //when
        when(hostRepository.existsByName(any())).thenReturn(true);
        var result = internalHostService.isDuplicateName("test");

        //then
        assertTrue(result);
    }

    @Order(6)
    @DisplayName("isDuplicateName False")
    @Test
    void isDuplicateName_false_nameNotDuplicated() {

        //given

        //when
        var result = internalHostService.isDuplicateName("test");

        //then
        assertFalse(result);
    }

    @Order(7)
    @DisplayName("isDuplicateIp True")
    @Test
    void isDuplicateIp_true_ipDuplicated() {

        //given

        //when
        when(hostRepository.existsByIp(any())).thenReturn(true);
        var result = internalHostService.isDuplicateIp("127.0.0.1");

        //then
        assertTrue(result);
    }

    @Order(8)
    @DisplayName("isDuplicateIp False")
    @Test
    void isDuplicateIp_false_ipNotDuplicated() {

        //given

        //when
        var result = internalHostService.isDuplicateIp("127.0.0.1");

        //then
        assertFalse(result);
    }

    @Order(9)
    @DisplayName("getAllHosts 호스트 없음")
    @Test
    void getAllHosts_empty_hostNotExists() {

        //given

        //when
        when(hostRepository.findAll()).thenReturn(new ArrayList<>());
        var result = internalHostService.getAllHosts();

        //then
        assertEquals(0, result.size());
    }

    @Order(10)
    @DisplayName("getAllHosts 호스트 세 개")
    @Test
    void getAllHosts_listLength3_hostExists3() {

        //given
        var list = new ArrayList<HostEntity>();
        list.add(getHostEntity("test1", "192.168.0.2"));
        list.add(getHostEntity("test2", "192.168.0.3"));
        list.add(getHostEntity("test3", "192.168.0.4"));

        //when
        when(hostRepository.findAll()).thenReturn(list);
        var result = internalHostService.getAllHosts();

        //then
        assertEquals(3, result.size());
    }

    @Order(11)
    @DisplayName("getHostsCount 호스트 1개")
    @Test
    void getHostsCount_1_host1() {

        //given

        //when
        when(hostRepository.getHostsCount()).thenReturn(1);
        var result = internalHostService.getHostsCount();

        //then
        assertEquals(1, result);
    }


}