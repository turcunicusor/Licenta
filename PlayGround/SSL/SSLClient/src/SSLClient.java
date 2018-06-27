import Generic.AcceptedParams;
import Generic.Data;
import Generic.IDevice;
import Generic.Params;

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
    private static final int port = 8004;

    public static void main(String[] args) {
        try {
            InetAddress ip = InetAddress.getByName("169.254.207.167");
            DeviceManager deviceManager = new DeviceManager();
            deviceManager.registerDevice(new HalDevice(ip, port, "homeenvironment"));
            IDevice device = deviceManager.getDevice(ip, port);
//            device.open();
//            device.close();
            device.getStatus();
            AcceptedParams acceptedParams = device.getAcceptedParams();
            System.out.println("Prametrii"+acceptedParams.toString());
            Data data = new Data();
            data.addData("temperature;humidity");
            Params params = device.queryData(data);
            System.out.println("Valori" + params.toString());
            device.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        comandLineClient();
    }

    public static void comandLineClient() {
        System.setProperty("javax.net.ssl.trustStore", "c:\\Users\\Turcu Nicusor\\Desktop\\Work\\Licenta\\Source Code\\ServerSH\\secret_key");
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