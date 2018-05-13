import Concrete.DeviceImpl;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Program {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage <Device type> <ip> <port>.");
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
        System.setProperty("javax.net.ssl.keyStore", "C:\\Program Files\\Java\\jdk1.8.0_144\\bin\\secret_key");
        System.setProperty("javax.net.ssl.keyStorePassword", "nicusor");
        CommunicationManager com = new CommunicationManager(ip, port, new DeviceImpl(deviceType));
        com.doWork();
    }
}
