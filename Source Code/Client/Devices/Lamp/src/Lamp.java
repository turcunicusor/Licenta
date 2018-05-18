import Concrete.DeviceImpl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

public class Lamp {
    public static void main(String[] args) {
        if (args.length != 6) {
            System.out.println("Usage <Device type> <ip> <port> <red gpio> <green gpio> <blue gpio>.");
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
            System.out.println("Red gpio invalid.");
            return;
        }
        if (!pattern.matcher(args[4]).matches()) {
            System.out.println("Green gpio invalid.");
            return;
        }
        if (!pattern.matcher(args[5]).matches()) {
            System.out.println("Blue gpio invalid.");
            return;
        }
	System.out.println("Red: GPIO " + args[3]);
	System.out.println("Green: GPIO " + args[4]);
	System.out.println("Blue: GPIO " + args[5]); 
        System.setProperty("javax.net.ssl.keyStore", "/home/pi/Desktop/Devices/sslkey/secret_key");
        System.setProperty("javax.net.ssl.keyStorePassword", "nicusor");
        CommunicationManager com = new CommunicationManager(ip, port, new DeviceImpl(deviceType, "GPIO " + args[3], "GPIO " + args[4], "GPIO " + args[5]));
        com.doWork();
    }
}
