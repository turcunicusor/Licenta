public class DeviceImpl implements IDevice {
    private Status status;
    private String type;
    private Data acceptedParams;
    private Params paramsVal;

    public DeviceImpl(String type) {
        this.type = type;
        this.acceptedParams = new Data();
        this.acceptedParams.addData("defparam");
        this.paramsVal = new Params();
        paramsVal.put("defparam", "0");
    }

    public void open() throws Exception {
        try {
            System.out.println("--DEBUG--open() called.");
            status = Status.OPENED;
        } catch (Exception e) {
            throw new Exception(String.format("Failed to open device. Reason: '%s'.", e.getMessage()));
        }
    }

    public void close() throws Exception {
        try {
            System.out.println("--DEBUG--close() called.");
            status = Status.CLOSED;
        } catch (Exception e) {
            throw new Exception(String.format("Failed to close device. Reason: '%s'.", e.getMessage()));
        }
    }

    public void command(Params params) throws Exception {
        System.out.println("--DEBUG--command() called.");
    }

    public Status getStatus() {
        System.out.println("--DEBUG--getStatus() called.");
        return status;
    }

    public Params queryData(Data data) {
        System.out.println("--DEBUG--queryData() called.");
        Params params = new Params();
        for(String d : data)
            params.put(d, paramsVal.get(d));
        return params;
    }

    public String getType() {
        System.out.println("--DEBUG--getType() called.");
        return type;
    }

    public Data getParams() {
        System.out.println("--DEBUG--getParams() called.");
        return acceptedParams;
    }
}
