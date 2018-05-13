package com.smarthome.server.hal.Generic;

import com.smarthome.server.entities.Device;

import java.io.IOException;

public interface IDevice {
    Device getDevice();

    void setDevice(Device device);

    void open() throws Exception;

    void close() throws Exception;

    Boolean isOpened() throws Exception;

    void command(Params params) throws Exception;

    DeviceStatus getStatus() throws Exception;

    Params queryData(Data data) throws Exception;

    String getType() throws Exception;

    AcceptedParams getAcceptedParams() throws Exception;

    void connect() throws  Exception;

    void closeConnection() throws IOException;

    void testConnection() throws Exception;
}
