import Generic.IDevice;

import java.net.InetAddress;
import java.util.HashMap;

public class DeviceManager {
    private static final String SSL_KEY = "javax.net.ssl.trustStore";
    private static final String SSL_VALUE = "C:\\Program Files\\Java\\jdk1.8.0_144\\bin\\demo";
    private HashMap<String, IDevice> devices;

    public DeviceManager() {
        System.setProperty(SSL_KEY, SSL_VALUE);
        this.devices = new HashMap<>();
    }

    public void registerDevice(IDevice device) throws Exception {
        try {
            this.devices.put(device.getIp().toString() + device.getPort(), device);
        } catch (Exception e) {
            throw new Exception(String.format("A device with ip '%s' and port '%s' already registered.", device.getIp().toString(), device.getPort()));
        }
    }

    public IDevice getDevice(InetAddress ip, int port) {
        return devices.get(ip.toString()+port);
    }
}
