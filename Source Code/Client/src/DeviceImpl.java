import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DeviceImpl implements IDevice {
    private Status status;
    private Queue<String> errors;
    private String type;

    DeviceImpl(){
        errors = new LinkedList<>();
        type = "Deviece";
    }

    @Override
    public boolean open() {
        try {
            status = Status.OPENED;
            return true;
        } catch (Exception e) {
            errors.add(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean close() {
        try {
            status = Status.CLOSED;
            return true;
        } catch (Exception e) {
            errors.add(e.getMessage());
            return false;
        }
    }

    @Override
    public String getLastError() {
        return errors.peek();
    }

    @Override
    public boolean command(Params params) {
        return false;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public Params queryData(List<String> data) {
        return null;
    }

    @Override
    public String getType() {
        return type;
    }
}
