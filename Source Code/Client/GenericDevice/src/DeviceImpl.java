import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DeviceImpl implements IDevice {
    private Status status;
    private Queue<String> errors;
    private String type;

    public DeviceImpl(String type){
        this.errors = new LinkedList<>();
        this.type = type;
    }

    public boolean open() {
        try {
            status = Status.OPENED;
            return true;
        } catch (Exception e) {
            errors.add(e.getMessage());
            return false;
        }
    }

    public boolean close() {
        try {
            status = Status.CLOSED;
            return true;
        } catch (Exception e) {
            errors.add(e.getMessage());
            return false;
        }
    }

    public String getLastError() {
        return errors.peek();
    }

    public boolean command(Params params) {
        return false;
    }

    public Status getStatus() {
        return status;
    }

    public Params queryData(List<String> data) {
        return null;
    }

    public String getType() {
        return type;
    }

    public List<String> getParams(){
        return null;
    }
}
