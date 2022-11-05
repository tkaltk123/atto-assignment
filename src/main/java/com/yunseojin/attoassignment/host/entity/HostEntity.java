package com.yunseojin.attoassignment.host.entity;

import com.yunseojin.attoassignment.host.dto.HostRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.net.InetAddress;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Slf4j
@Table(name = "hosts")
@SecondaryTable(
        name = "host_alive",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "host_id", referencedColumnName = "id")
)
public class HostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    protected Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false, updatable = false, insertable = false)
    protected Date updatedAt;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "ip", nullable = false)
    private String ip;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_alive_time", table = "host_alive")
    protected Date lastAliveTime;

    public void update(HostRequest request) {

        if (request.getName() != null)
            name = request.getName();
        if (request.getIp() != null)
            ip = request.getIp();
    }

    public boolean getIsAlive() {

        var isAlive = false;
        try {

            var address = InetAddress.getByName(ip);
            isAlive = address.isReachable(100);

            if (isAlive)
                lastAliveTime = new Date();
        } catch (Exception ignore) {
        }
        return isAlive;
    }
}