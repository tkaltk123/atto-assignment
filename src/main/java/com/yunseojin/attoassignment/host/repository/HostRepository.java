package com.yunseojin.attoassignment.host.repository;

import com.yunseojin.attoassignment.host.entity.HostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostRepository extends JpaRepository<HostEntity, Long> {

    boolean existsByName(String name);

    boolean existsByIp(String ip);
}
