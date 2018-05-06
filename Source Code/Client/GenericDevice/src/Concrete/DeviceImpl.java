package Concrete;

import Generic.*;

public class DeviceImpl implements IDevice {
    private DeviceStatus status;
    private String type;
    private AcceptedParams acceptedParams;
    private Params paramsVal;

    public DeviceImpl(String type) {
        this.type = type.toLowerCase();
        this.acceptedParams = new AcceptedParams();
        try {
            this.acceptedParams.addData("intensitate=0:int;");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.paramsVal = new Params();
        paramsVal.put("defparam", "0");
    }

    @Override
    public void open() throws Exception {
        try {
            System.out.println("--DEBUG--open() called.");
            status = DeviceStatus.OPENED;
        } catch (Exception e) {
            throw new Exception(String.format("Failed to open device. Reason: '%s'.", e.getMessage()));
        }
    }

    @Override
    public void close() throws Exception {
        try {
            System.out.println("--DEBUG--close() called.");
            status = DeviceStatus.CLOSED;
        } catch (Exception e) {
            throw new Exception(String.format("Failed to close device. Reason: '%s'.", e.getMessage()));
        }
    }

    @Override
    public void command(Params params) throws Exception {
        System.out.println("--DEBUG--command() called.");
    }

    @Override
    public DeviceStatus getStatus() {
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
    public AcceptedParams getAcceptedParams() {
        System.out.println("--DEBUG--getAcceptedParams() called.");
        return acceptedParams;
    }
}
