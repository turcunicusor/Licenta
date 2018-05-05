package com.smarthome.server.entities;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
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
    private String name;
    private String hash;

    public Device() {
        lastAccessed = new Date();
    }

    public Device(InetAddress ip, int port, String type, String name) throws NoSuchAlgorithmException {
        this.ip = ip;
        this.port = port;
        this.type = type;
        this.name = name;
        String text = ip.toString() + port + type + name;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            this.hash =  URLEncoder.encode(Base64.getEncoder().encodeToString(hash), "UTF-8");
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException("Algorithm SHA-256 was not found.");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHash() {
        return hash;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
