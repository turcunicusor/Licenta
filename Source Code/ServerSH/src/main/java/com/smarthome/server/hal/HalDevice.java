package com.smarthome.server.hal;

import com.smarthome.server.entities.Device;
import com.smarthome.server.hal.Generic.*;

import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class HalDevice implements IDevice {
    private Device device;
    private DeviceStatus status;
    private AcceptedParams acceptedParams;
    private Params paramsVal;

    private Socket server;
    private PrintWriter outStream;
    private BufferedReader inStream;

    public HalDevice(Device device) {
        this.device = device;
        this.acceptedParams = new AcceptedParams();
        this.paramsVal = new Params();
        this.status = DeviceStatus.CREATED;
    }

    @Override
    public Device getDevice() {
        return this.device;
    }

    @Override
    public void setDevice(Device device) {
        this.device = device;
    }

    @Override
    public void open() throws Exception {
        try {
            outStream.println(Protocol.OPEN);
            onResponse(inStream.readLine());
        } finally {
            this.status = DeviceStatus.OPENED;
        }
    }

    @Override
    public void close() throws Exception {
        try {
            outStream.println(Protocol.CLOSE);
            onResponse(inStream.readLine());
        } finally {
            this.status = DeviceStatus.CLOSED;
        }
    }

    @Override
    public void command(Params params) throws Exception {
        outStream.println(Protocol.COMMAND + params.toString());
        onResponse(inStream.readLine());
    }

    @Override
    public DeviceStatus getStatus() throws Exception {
//        if (DeviceStatus.CONNECTED == this.status) {
//            outStream.println(Protocol.GET_STATUS);
//            this.status = DeviceStatus.valueOf(onResponse(inStream.readLine()));
//        }
        return this.status;
    }

    @Override
    public Params queryData(Data data) throws Exception {
        if (DeviceStatus.CONNECTED == this.status) {
            outStream.println(Protocol.QUERRY_DATA + data.toString());
            this.paramsVal.addData(onResponse(inStream.readLine()));
        }
        return this.paramsVal;
    }

    @Override
    public String getType() throws IOException {
        outStream.println(Protocol.GET_TYPE);
        this.device.setType(onResponse(inStream.readLine()));
        return this.device.getType();
    }

    @Override
    public AcceptedParams getAcceptedParams() throws Exception {
        if (DeviceStatus.CONNECTED == status) {
            outStream.println(Protocol.GET_PARAMS);
            this.acceptedParams.addData(onResponse(inStream.readLine()));
        }
        return this.acceptedParams;
    }

    @Override
    public void closeConnection() throws IOException {
        System.out.println("disconnected called");
        try {
            if (this.status == DeviceStatus.CONNECTED) {
                inStream.close();
                outStream.close();
                server.close();
            }
            this.paramsVal.clear();
            this.acceptedParams.clear();
        } finally {
            this.status = DeviceStatus.DISCONNECTED;
        }
    }

    @Override
    public void connect() throws Exception {
        System.out.println("connected called");
        try {
            server = (SSLSocketFactory.getDefault()).createSocket(device.getIp(), device.getPort());
            outStream = new PrintWriter(server.getOutputStream(), true);
            inStream = new BufferedReader(new InputStreamReader(server.getInputStream()));
        } catch (Exception e) {
            throw new SocketException(String.format("Cannot connect to '%s:%s' type '%s'. Make sure that device is online.",
                    device.getIp().toString(), device.getPort(), device.getType()));
        }
        this.status = DeviceStatus.CONNECTED;
        try {
            // if type mismatch connection is opened and we need to close it
            checkType(device.getType());
        } catch (IOException e) {
            closeConnection();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void testConnection() throws Exception {
        this.connect();
        this.closeConnection();
    }

    private String onResponse(String response) throws IOException {
        System.out.println("LOG: " + response);
        int header;
        if (response.length() > 2) header = Integer.valueOf(response.substring(0, 3));
        else throw new IOException(String.format("Invalid header '%s' received.", response));
        String payload = response.substring(3);
        switch (header) {
            case Protocol.SUCCESS:
                return payload;
            case Protocol.EXCEPTION:
                throw new IOException(payload);
            default:
                throw new IOException(String.format("Invalid header '%d' received.", header));
        }
    }

    private void checkType(String type) throws IOException {
        String deviceType = getType();
        if (!type.equals(deviceType))
            throw new IOException(String.format("Device type provided was '%s' and real device type is '%s'.", type, deviceType));
    }
}
