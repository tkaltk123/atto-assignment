package com.yunseojin.attoassignment.host.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HostRequest implements Serializable {

    private String name;
    private String ip;
}
