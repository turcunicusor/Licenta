package Concrete;

import Generic.Data;
import Generic.IDevice;
import Generic.Params;
import Generic.Status;

public class DeviceImpl implements IDevice {
    private Status status;
    private String type;
    private Data acceptedParams;
    private Params paramsVal;

    public DeviceImpl(String type) {
        this.type = type.toLowerCase();
        this.acceptedParams = new Data();
        this.acceptedParams.addData("defparam");
        this.paramsVal = new Params();
        paramsVal.put("defparam", "0");
    }

    @Override
    public void open() throws Exception {
        try {
            System.out.println("--DEBUG--open() called.");
            status = Status.OPENED;
        } catch (Exception e) {
            throw new Exception(String.format("Failed to open device. Reason: '%s'.", e.getMessage()));
        }
    }

    @Override
    public void close() throws Exception {
        try {
            System.out.println("--DEBUG--close() called.");
            status = Status.CLOSED;
        } catch (Exception e) {
            throw new Exception(String.format("Failed to close device. Reason: '%s'.", e.getMessage()));
        }
    }

    @Override
    public void command(Params params) throws Exception {
        System.out.println("--DEBUG--command() called.");
    }

    @Override
    public Status getStatus() {
        System.out.println("--DEBUG--getStatus() called.");
        return status;
    }

    public Params queryData(Data data) {
        System.out.println("--DEBUG--queryData() called.");
        Params params = new Params();
        for (String d : data)
            params.put(d, paramsVal.get(d));
        return params;
    }

    @Override
    public String getType() {
        System.out.println("--DEBUG--getType() called.");
        return type;
    }

    @Override
    public Data getParams() {
        System.out.println("--DEBUG--getParams() called.");
        return acceptedParams;
    }
}
