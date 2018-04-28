import java.util.List;

public interface IDevice {
    boolean open();
    boolean close();
    String getLastError();
    boolean command(Params params);
    Status getStatus();
    Params queryData(List<String> data);
    String getType();
    List<String> getParams();
}
