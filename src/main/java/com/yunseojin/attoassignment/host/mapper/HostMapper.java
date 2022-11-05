package com.yunseojin.attoassignment.host.mapper;

import com.yunseojin.attoassignment.host.dto.HostRequest;
import com.yunseojin.attoassignment.host.dto.HostResponse;
import com.yunseojin.attoassignment.host.entity.HostEntity;
import org.apache.catalina.Host;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "Spring")
public interface HostMapper {

    HostMapper INSTANCE = Mappers.getMapper(HostMapper.class);

    HostEntity toEntity(HostRequest request);

    HostResponse toResponse(HostEntity host);
}
