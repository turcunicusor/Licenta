package com.smarthome.server.entities;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.net.InetAddress;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "device")
@Transactional
public class Device{
    @Id
    @Column(name = "device_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private InetAddress ip;
    private int port;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private User owner;
    @Column(name = "last_accessed")
    private Date lastAccessed;
    private String type;
    private String name;
    private String hash;

    public Device() {
        lastAccessed = new Date();
    }

    public Device(InetAddress ip, int port, String type, String name) {
        this.ip = ip;
        this.port = port;
        this.type = type;
        this.name = name;
        this.hash = UUID.randomUUID().toString();
        lastAccessed = new Date();
    }

    public static Device clone(Device device){
        Device deviceCloned = new Device();
        deviceCloned.setIp(device.getIp());
        deviceCloned.setPort(device.getPort());
        deviceCloned.setOwner(device.getOwner());
        deviceCloned.setLastAccessed(device.getLastAccessed());
        deviceCloned.setType(device.getType());
        deviceCloned.setName(device.getName());
        deviceCloned.setHash(device.getHash());
        return deviceCloned;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
