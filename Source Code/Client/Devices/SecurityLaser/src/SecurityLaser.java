import Concrete.DeviceImpl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

public class SecurityLaser {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage <Device type> <ip> <port> <laser gpio>.");
            return;
        }
        String deviceType = args[0];
        InetAddress ip;
        try {
            ip = InetAddress.getByName(args[1]);
        } catch (UnknownHostException e) {
            System.out.println("Failed to parse ip address! Error: " + e.getMessage());
            return;
        }
        int port = Integer.parseInt(args[2]);
        if (port > 65535 || port < 0) {
            System.out.println("Invalid port range");
            return;
        }
        final Pattern pattern = Pattern.compile("^([0-9]|[1-3][0-9])$");
        if (!pattern.matcher(args[3]).matches()) {
            System.out.println("laser gpio invalid.");
            return;
        }
        System.out.println("laser gpio " + args[3]);
        System.setProperty("javax.net.ssl.keyStore", "/home/pi/Desktop/Playground/sslkey/secret_key");
        System.setProperty("javax.net.ssl.keyStorePassword", "nicusor");
        CommunicationManager com = new CommunicationManager(ip, port, new DeviceImpl(deviceType, "GPIO " + args[3]));
        com.doWork();
    }
}
