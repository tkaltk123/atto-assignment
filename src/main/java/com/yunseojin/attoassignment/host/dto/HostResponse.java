package com.yunseojin.attoassignment.host.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HostResponse implements Serializable {

    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private String ip;
    private Boolean isAlive;
    private Date lastAliveTime;
}
