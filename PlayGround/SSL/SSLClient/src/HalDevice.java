import Generic.*;

import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class HalDevice implements IDevice {
    private InetAddress ip;
    private int port;
    private String type;
    private DeviceStatus status;
    private AcceptedParams acceptedParams;
    private Params paramsVal;

    private Socket server;
    private PrintWriter outStream;
    private BufferedReader inStream;

    HalDevice(InetAddress ip, int port, String type) throws Exception {
        this.ip = ip;
        this.port = port;
        this.acceptedParams = new AcceptedParams();
        this.paramsVal = new Params();
        try {
            server = (SSLSocketFactory.getDefault()).createSocket(ip, port);
            outStream = new PrintWriter(server.getOutputStream(), true);
            inStream = new BufferedReader(new InputStreamReader(server.getInputStream()));
        } catch (Exception e) {
            throw new Exception(String.format("Cannot connect to '%s:%s' type '%s'. Make sure that device is online. Reason: '%s'.", ip.toString(), port, this.type, e.getMessage()));
        }
        checkType(type);
    }

    @Override
    public InetAddress getIp() {
        return ip;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void open() throws Exception {
        outStream.println(Protocol.OPEN);
        onResponse(inStream.readLine());
    }

    @Override
    public void close() throws Exception {
        outStream.println(Protocol.CLOSE);
        onResponse(inStream.readLine());
    }

    @Override
    public void command(Params params) throws Exception {
        outStream.println(Protocol.COMMAND + params.toString());
        onResponse(inStream.readLine());
    }

    @Override
    public DeviceStatus getStatus() throws Exception {
        outStream.println(Protocol.GET_STATUS);
        this.status = DeviceStatus.valueOf(onResponse(inStream.readLine()));
        return this.status;
    }

    @Override
    public Params queryData(Data data) throws Exception {
        outStream.println(Protocol.QUERRY_DATA + data.toString());
        this.paramsVal.addData(onResponse(inStream.readLine()));
        return this.paramsVal;
    }

    @Override
    public String getType() throws Exception {
        outStream.println(Protocol.GET_TYPE);
        this.type = onResponse(inStream.readLine());
        return this.type;
    }

    @Override
    public AcceptedParams getAcceptedParams() throws Exception {
        outStream.println(Protocol.GET_PARAMS);
        this.acceptedParams.addData(onResponse(inStream.readLine()));
        return this.acceptedParams;
    }

    @Override
    public void closeConnection() throws IOException {
        inStream.close();
        outStream.close();
        server.close();
    }

    private String onResponse(String response) throws Exception {
        System.out.println("LOG: " + response);
        int header;
        if (response.length() > 2) header = Integer.valueOf(response.substring(0, 3));
        else throw new Exception(String.format("Invalid header '%s' received.", response));
        String payload = response.substring(3);
        switch (header) {
            case Protocol.SUCCESS:
                return payload;
            case Protocol.EXCEPTION:
                throw new Exception(payload);
            default:
                throw new Exception(String.format("Invalid header '%d' received.", header));
        }
    }

    private void checkType(String type) throws Exception {
        String deviceType = getType();
        if (!type.equals(deviceType))
            throw new Exception(String.format("Device type provided was '%s' and real device type is '%s'.", type, deviceType));
    }
}
