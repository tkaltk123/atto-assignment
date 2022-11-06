package com.yunseojin.attoassignment.host.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.yunseojin.attoassignment.TestUtil.getHostRequest;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
class HostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void insert() throws Exception {

        for (int i = 0; i < 100; ++i) {

            var request = getHostRequest("testHost" + i, "192.168.0." + i);

            var result = postWith("/hosts", request);

            result.andExpect(status().isCreated())
                    .andExpect(jsonPath("name").value(request.getName()))
                    .andExpect(jsonPath("ip").value(request.getIp()));
        }
    }

    @Test
    void checkTime() throws Exception {

        long time = System.currentTimeMillis();

        mockMvc.perform(get("/hosts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        time = System.currentTimeMillis() - time;

        System.out.println("time : " + time);
        assertTrue(time <= 1000);
    }

    ResultActions postWith(String url, Object data) throws Exception {

        return mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(data))
                .accept(MediaType.APPLICATION_JSON));
    }
}