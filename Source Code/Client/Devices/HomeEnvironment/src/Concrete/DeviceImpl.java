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
    private int environmentGPIO;
    private SensorTH sensorTH;

    public DeviceImpl(String type, int environmentGPIO) {
        this.type = type.toLowerCase();
        this.acceptedParams = new AcceptedParams();
        try {
            this.acceptedParams.addData("temperature=1:float;humidity=1:float");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.paramsVal = new Params();
        this.paramsVal.put("temperature", "0.0");
        this.paramsVal.put("humidity", "0.0");
        this.isOpened = false;

        this.environmentGPIO = environmentGPIO;
        this.sensorTH = new SensorTH();
        this.sensorTH.readData(environmentGPIO);
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
        // comand empty, only read parameters
    }

    private void updateParams() {
        try {
            this.paramsVal.put("temperature",Float.toString(this.sensorTH.getTemperature()));
            this.paramsVal.put("humidity",Float.toString(this.sensorTH.getHumidity()));
        }
        catch (Exception e) {
            System.out.println("Update params error. Reason: " + e.getMessage());
        }
    }

    public Params queryData(Data data) {
        System.out.println("--DEBUG--queryData() called.");
        updateParams();
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
        System.out.println(this.acceptedParams.toString());
        return acceptedParams;
    }
}
