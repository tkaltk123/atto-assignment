package com.yunseojin.attoassignment;

import com.yunseojin.attoassignment.etc.enums.ErrorMessage;
import com.yunseojin.attoassignment.etc.exception.BaseException;
import com.yunseojin.attoassignment.host.dto.HostRequest;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestUtil {

    public static void assertError(ErrorMessage error, Executable executable) {
        assertEquals(error.getCode(), assertThrows(BaseException.class, executable).getCode());
    }


    public static HostRequest getHostRequest(String name, String ip) {

        return HostRequest.builder()
                .name(name)
                .ip(ip)
                .build();
    }
}
