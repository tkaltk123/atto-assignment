package com.yunseojin.attoassignment.host.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
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
}