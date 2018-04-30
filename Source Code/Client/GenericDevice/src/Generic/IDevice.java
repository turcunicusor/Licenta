package Generic;

public interface IDevice {
    void open() throws Exception;

    void close() throws Exception;

    void command(Params params) throws Exception;

    Status getStatus();

    Params queryData(Data data);

    String getType();

    Data getParams();
}
