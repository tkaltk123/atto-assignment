package com.yunseojin.attoassignment.host.mapper;

import com.yunseojin.attoassignment.host.dto.HostRequest;
import com.yunseojin.attoassignment.host.dto.HostResponse;
import com.yunseojin.attoassignment.host.entity.HostEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface HostMapper {

    HostEntity toEntity(HostRequest request);

    HostResponse toResponse(HostEntity host);
}
