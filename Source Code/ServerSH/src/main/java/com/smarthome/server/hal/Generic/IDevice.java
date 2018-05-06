package com.smarthome.server.hal.Generic;

import java.io.IOException;
import java.net.InetAddress;

public interface IDevice {
    InetAddress getIp();

    int getPort();

    void open() throws Exception;

    void close() throws Exception;

    void command(Params params) throws Exception;

    DeviceStatus getStatus() throws Exception;

    Params queryData(Data data) throws Exception;

    String getType() throws Exception;

    AcceptedParams getAcceptedParams() throws Exception;

    void closeConnection() throws IOException;
}
