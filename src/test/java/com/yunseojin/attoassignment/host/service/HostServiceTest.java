package com.yunseojin.attoassignment.host.service;

import com.yunseojin.attoassignment.etc.enums.ErrorMessage;
import com.yunseojin.attoassignment.etc.exception.RequestInputException;
import com.yunseojin.attoassignment.host.entity.HostEntity;
import com.yunseojin.attoassignment.host.mapper.HostMapper;
import com.yunseojin.attoassignment.host.serviceimpl.HostServiceImpl;
import com.yunseojin.attoassignment.host.serviceimpl.InternalHostServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.yunseojin.attoassignment.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
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
        when(internalHostService.saveHost(any())).thenReturn(HostMapper.INSTANCE.toEntity(request));
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

    @Order(10)
    @DisplayName("updateHost 성공")
    @Test
    void updateHost_success() {

        //given
        var host = getHostEntity("test", "127.0.0.1");
        var request = getHostRequest("test2", "192.168.0.1");

        //when
        when(internalHostService.getHostById(1L)).thenReturn(host);
        var response = hostService.updateHost(1L, request);

        //then
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getIp(), response.getIp());
    }

    @Order(11)
    @DisplayName("updateHost 호스트 없음")
    @Test
    void updateHost_throwException_hostNotExists() {

        //given
        var host = getHostEntity("test", "127.0.0.1");
        var request = getHostRequest("test2", "192.168.0.1");

        //when
        when(internalHostService.getHostById(2L)).thenThrow(new RequestInputException((ErrorMessage.HOST_NOT_FOUND_EXCEPTION)));

        //then
        assertError(ErrorMessage.HOST_NOT_FOUND_EXCEPTION, () -> {
            hostService.updateHost(2L, request);
        });
    }

    @Order(12)
    @DisplayName("updateHost 호스트 이름 중복")
    @Test
    void updateHost_throwException_nameDuplicated() {

        //given
        var host = getHostEntity("test", "127.0.0.1");
        var request = getHostRequest("test3", "192.168.0.1");

        //when
        when(internalHostService.getHostById(1L)).thenReturn(host);
        when(internalHostService.isDuplicateName("test3")).thenReturn(true);

        //then
        assertError(ErrorMessage.DUPLICATED_HOST_NAME_EXCEPTION, () -> {
            hostService.updateHost(1L, request);
        });
    }

    @Order(13)
    @DisplayName("updateHost 호스트 IP 중복")
    @Test
    void updateHost_throwException_ipDuplicated() {

        //given
        var host = getHostEntity("test", "127.0.0.1");
        var request = getHostRequest("test2", "192.168.0.2");

        //when
        when(internalHostService.getHostById(1L)).thenReturn(host);
        when(internalHostService.isDuplicateIp("192.168.0.2")).thenReturn(true);

        //then
        assertError(ErrorMessage.DUPLICATED_HOST_IP_EXCEPTION, () -> {
            hostService.updateHost(1L, request);
        });
    }

    @Order(14)
    @DisplayName("updateHost 이름 변경이 없을 경우 성공")
    @Test
    void updateHost_success_nameNotChanged() {

        //given
        var host = getHostEntity("test", "127.0.0.1");
        var request = getHostRequest("test", "192.168.0.1");

        //when
        when(internalHostService.getHostById(1L)).thenReturn(host);
        var response = hostService.updateHost(1L, request);

        //then
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getIp(), response.getIp());
    }

    @Order(15)
    @DisplayName("updateHost IP 변경이 없을 경우 성공")
    @Test
    void updateHost_success_ipNotChanged() {

        //given
        var host = getHostEntity("test", "127.0.0.1");
        var request = getHostRequest("test2", "127.0.0.1");

        //when
        when(internalHostService.getHostById(1L)).thenReturn(host);
        var response = hostService.updateHost(1L, request);

        //then
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getIp(), response.getIp());
    }

    @Order(20)
    @DisplayName("deleteHost 성공")
    @Test
    void deleteHost_success() {

        //given
        var host = getHostEntity("test", "127.0.0.1");

        //when
        when(internalHostService.getHostById(1L)).thenReturn(host);
        hostService.deleteHost(1L);

        //then
        verify(internalHostService, atMostOnce()).deleteHost(host);
    }

    @Order(21)
    @DisplayName("deleteHost 호스트 없음")
    @Test
    void deleteHost_throwException_hostNotExists() {

        //given
        var host = getHostEntity("test", "127.0.0.1");

        //when
        when(internalHostService.getHostById(2L)).thenThrow(new RequestInputException((ErrorMessage.HOST_NOT_FOUND_EXCEPTION)));
        
        //then
        assertError(ErrorMessage.HOST_NOT_FOUND_EXCEPTION, () -> {
            hostService.deleteHost(2L);
        });
    }

    @Order(30)
    @DisplayName("isAliveHost True")
    @Test
    void isAliveHost_true_ip127_0_0_1() {

        //given
        var host = getHostEntity("test", "127.0.0.1");

        //when
        when(internalHostService.getHostById(1L)).thenReturn(host);
        var result = hostService.isAliveHost(1L);

        //then
        assertTrue(result);
    }

    @Order(31)
    @DisplayName("isAliveHost False")
    @Test
    void isAliveHost_false_ip0_0_0_0() {

        //given
        var host = getHostEntity("test", "0.0.0.0");

        //when
        when(internalHostService.getHostById(1L)).thenReturn(host);
        var result = hostService.isAliveHost(1L);

        //then
        assertFalse(result);
    }

    @Order(32)
    @DisplayName("isAliveHost 호스트 없음")
    @Test
    void isAliveHost_hostNotExists() {

        //given
        var host = getHostEntity("test", "127.0.0.1");

        //when
        when(internalHostService.getHostById(2L)).thenThrow(new RequestInputException((ErrorMessage.HOST_NOT_FOUND_EXCEPTION)));

        //then
        assertError(ErrorMessage.HOST_NOT_FOUND_EXCEPTION, () -> {
            hostService.isAliveHost(2L);
        });
    }

    @Order(40)
    @DisplayName("getHost 연결상태")
    @Test
    void getHost_isAliveTrue_ip127_0_0_1() {

        //given
        var host = getHostEntity("test", "127.0.0.1");

        //when
        when(internalHostService.getHostById(1L)).thenReturn(host);
        var response = hostService.getHost(1L);

        //then
        assertTrue(response.getIsAlive());
    }

    @Order(41)
    @DisplayName("getHost 미연결상태")
    @Test
    void getHost_isAliveFalse_ip0_0_0_0() {

        //given
        var host = getHostEntity("test", "0.0.0.0");

        //when
        when(internalHostService.getHostById(1L)).thenReturn(host);
        var response = hostService.getHost(1L);

        //then
        assertFalse(response.getIsAlive());
    }

    @Order(42)
    @DisplayName("getHost 호스트 없음")
    @Test
    void getHost_hostNotExists() {

        //given
        var host = getHostEntity("test", "127.0.0.1");

        //when
        when(internalHostService.getHostById(2L)).thenThrow(new RequestInputException((ErrorMessage.HOST_NOT_FOUND_EXCEPTION)));

        //then
        assertError(ErrorMessage.HOST_NOT_FOUND_EXCEPTION, () -> {
            hostService.getHost(2L);
        });
    }

    @Order(50)
    @DisplayName("getAllHosts 성공")
    @Test
    void getAllHosts_success() {

        //given
        var list = new ArrayList<HostEntity>();
        list.add(getHostEntity("test1", "192.168.0.2"));
        list.add(getHostEntity("test1", "192.168.0.3"));
        list.add(getHostEntity("test1", "192.168.0.4"));

        //when
        when(internalHostService.getAllHosts()).thenReturn(list);
        var result = hostService.getAllHosts();

        //then
        assertEquals(3, result.size());
    }

    @Order(51)
    @DisplayName("getAllHosts 호스트가 100개 일 때 1초안에 성공")
    @Test
    void getAllHosts_successLessThen1000ms_hostCountEquals100() {

        //given
        var list = getTestHosts();

        //when
        when(internalHostService.getAllHosts()).thenReturn(list);
        long time = System.currentTimeMillis();
        var result = hostService.getAllHosts();
        time = System.currentTimeMillis() - time;

        //then
        System.out.println(time);
        assertTrue(time < 1000L);
        assertEquals(100, result.size());
    }

    private void createHostRepeat100() {

        for (int i = 0; i < 100; ++i) {

            var request = getHostRequest("test" + i, "192.168.0." + i + 2);
            hostService.createHost(request);
        }
    }

    private List<HostEntity> getTestHosts() {

        var list = new ArrayList<HostEntity>();
        for (int i = 0; i < 100; ++i) {
            list.add(getHostEntity("test" + i, "192.168.0." + i));
        }
        return list;
    }
}