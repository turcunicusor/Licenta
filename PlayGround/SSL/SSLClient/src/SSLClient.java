import Generic.AcceptedParams;
import Generic.IDevice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocketFactory;

public class SSLClient {
    private static final int port = 8000;

    public static void main(String[] args) {
        try {
            InetAddress ip = InetAddress.getByName("192.168.0.106");
            DeviceManager deviceManager = new DeviceManager();
            deviceManager.registerDevice(new HalDevice(ip, port, "led"));
            IDevice device = deviceManager.getDevice(ip, port);
//            device.open();
//            device.close();
            device.getStatus();
//            AcceptedParams acceptedParams = device.getAcceptedParams();
////            System.out.println(acceptedParams.get("intensintate"));
            device.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        comandLineClient();
    }

    public static void comandLineClient() {
        System.setProperty("javax.net.ssl.trustStore", "C:\\Program Files\\Java\\jdk1.8.0_144\\bin\\secret_key");
        SSLSocketFactory sslSocketFactory =
                (SSLSocketFactory) SSLSocketFactory.getDefault();
        try {
            Socket socket = sslSocketFactory.createSocket("localhost", port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            try (BufferedReader bufferedReader =
                         new BufferedReader(
                                 new InputStreamReader(socket.getInputStream()))) {
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    System.out.println("Enter something:");
                    String inputLine = scanner.nextLine();
                    if (inputLine.equals("100")) {
                        socket.close();
                        break;
                    }
                    out.println(inputLine);
                    System.out.println(bufferedReader.readLine());
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(SSLClient.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}