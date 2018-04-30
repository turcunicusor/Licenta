package com.smarthome.server.entities;

import javax.persistence.*;
import java.net.InetAddress;
import java.util.Date;

@Entity
@Table(name = "device")
public class Device {
    @Id
    @Column(name = "device_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private InetAddress ip;
    private int port;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;
    @Column(name = "last_accessed")
    private Date lastAccessed;
    private String type;

    public Device() {
        lastAccessed = new Date();
    }

    public Device(InetAddress ip, int port, String type) {
        this.ip = ip;
        this.port = port;
        this.type = type;
        lastAccessed = new Date();
    }

    public Long getId() {
        return id;
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public User getOwner() {
        return owner;
    }

    public Date getLastAccessed() {
        return lastAccessed;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setLastAccessed(Date lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
