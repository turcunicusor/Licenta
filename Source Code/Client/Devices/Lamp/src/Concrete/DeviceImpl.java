package Concrete;

import Generic.AcceptedParams;
import Generic.Data;
import Generic.IDevice;
import Generic.Params;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import java.util.Map;

public class DeviceImpl implements IDevice {
    private String type;
    private AcceptedParams acceptedParams;
    private Params paramsVal;
    private boolean isOpened;
    private GpioPinDigitalOutput pinRed;
    private GpioPinDigitalOutput pinGreen;
    private GpioPinDigitalOutput pinBlue;

    public DeviceImpl(String type, String redGpio, String greenGpio, String blueGpio) {
        this.type = type.toLowerCase();
        this.acceptedParams = new AcceptedParams();
        try {
            this.acceptedParams.addData("red=0:boolean;green=0:boolean;blue=0:boolean;");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.paramsVal = new Params();
        this.paramsVal.put("red", "false");
        this.paramsVal.put("green", "false");
        this.paramsVal.put("blue", "false");
        this.isOpened = false;

        final GpioController gpio = GpioFactory.getInstance();
        pinRed  = gpio.provisionDigitalOutputPin(RaspiPin.getPinByName(redGpio), "", PinState.LOW);
        pinGreen = gpio.provisionDigitalOutputPin(RaspiPin.getPinByName(greenGpio), "", PinState.LOW);
        pinBlue  = gpio.provisionDigitalOutputPin(RaspiPin.getPinByName(blueGpio), "", PinState.LOW);
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
	System.out.println(params.toString());
        for (String key : params.keySet())
            if(!this.acceptedParams.keySet().contains(key.toLowerCase()))
                throw new Exception(String.format("Invalid parameter: '%s'.", key));
        for (Map.Entry<String, String> entry : params.entrySet())
            this.paramsVal.put(entry.getKey(), entry.getValue());
        executeCommand();
    }

    private void executeCommand() {
        for (Map.Entry<String, String> entry: paramsVal.entrySet()) {
            Boolean currentState = Boolean.parseBoolean(entry.getValue());
            switch (entry.getKey().toLowerCase()) {
                case "red":
                    this.pinRed.setState(currentState);
                    break;
                case "green":
                    this.pinGreen.setState(currentState);
                    break;
                case "blue":
                    this.pinBlue.setState(currentState);
                    break;
                default:
                    // no default, error handled in command function
            }
        }
    }

    public Params queryData(Data data) {
        System.out.println("--DEBUG--queryData() called.");
        Params params = new Params();
        for (String d : data)
            params.put(d, paramsVal.get(d));
	System.out.println(params.toString());
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
