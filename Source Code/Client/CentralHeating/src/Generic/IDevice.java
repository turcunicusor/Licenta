package Generic;

public interface IDevice {
    void open() throws Exception;

    void close() throws Exception;

    void command(Params params) throws Exception;

    DeviceStatus getStatus();

    Params queryData(Data data);

    String getType();

    AcceptedParams getAcceptedParams();
}
