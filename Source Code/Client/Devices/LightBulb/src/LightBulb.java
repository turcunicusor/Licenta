import Concrete.DeviceImpl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

public class LightBulb {
    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("Usage <Device type> <ip> <port> <red gpio> <green gpio>.");
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
            System.out.println("light gpio invalid.");
            return;
        }
        System.out.println("red gpio " + args[3]);
        System.out.println("green gpio " + args[4]);
        System.setProperty("javax.net.ssl.keyStore", "/home/pi/Desktop/Playground/sslkey/secret_key");
        System.setProperty("javax.net.ssl.keyStorePassword", "nicusor");
        CommunicationManager com = new CommunicationManager(ip, port, new DeviceImpl(deviceType, "GPIO " + args[3], "GPIO " + args[4]));
        com.doWork();
    }
}
