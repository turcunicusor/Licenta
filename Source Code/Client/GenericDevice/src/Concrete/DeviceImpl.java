package Concrete;

import Generic.*;

public class DeviceImpl implements IDevice {
    private String type;
    private AcceptedParams acceptedParams;
    private Params paramsVal;
    private boolean isOpened;

    public DeviceImpl(String type) {
        this.type = type.toLowerCase();
        this.acceptedParams = new AcceptedParams();
        try {
            this.acceptedParams.addData("intensitate=0:int;");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.paramsVal = new Params();
        this.paramsVal.put("intensitate", "0");
        this.isOpened = false;

        // here i should check device status.
        // more logic here.
    }

    @Override
    public void open() throws Exception {
        try {
            System.out.println("--DEBUG--open() called.");
            this.isOpened = true;
        } catch (Exception e) {
            throw new Exception(String.format("Failed to open device. Reason: '%s'.", e.getMessage()));
        }
    }

    @Override
    public Boolean isOpened() {
        System.out.println("--DEBUG--isOpened() called.");
        return isOpened;
    }

    @Override
    public void close() throws Exception {
        try {
            System.out.println("--DEBUG--close() called.");
            this.isOpened = false;
        } catch (Exception e) {
            throw new Exception(String.format("Failed to close device. Reason: '%s'.", e.getMessage()));
        }
    }

    @Override
    public void command(Params params) throws Exception {
        System.out.println("--DEBUG--command() called.");
        this.paramsVal = params;
        System.out.println(paramsVal.toString());
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
