package com.smarthome.server.repositories;

import com.smarthome.server.entities.Device;
import com.smarthome.server.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findAllByOwner(User user);
}