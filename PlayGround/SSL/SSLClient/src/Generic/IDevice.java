package Generic;

import java.io.IOException;
import java.net.InetAddress;

public interface IDevice {
    InetAddress getIp();

    int getPort();

    void open() throws Exception;

    void close() throws Exception;

    void command(Params params) throws Exception;

    Status getStatus() throws Exception;

    Params queryData(Data data) throws Exception;

    String getType() throws Exception;

    Data getParams() throws Exception;

    void closeConnection() throws IOException;
}
