package Generic;

public interface IDevice {
    void open() throws Exception;

    Boolean isOpened();

    void close() throws Exception;

    void command(Params params) throws Exception;

    Params queryData(Data data);

    String getType();

    AcceptedParams getAcceptedParams();
}
