import Generic.*;

import javax.net.ssl.SSLServerSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommunicationManager implements IMessage {
    private IDevice device;
    private InetAddress ip;
    private int port;
    private PrintWriter outStream;
    private Socket client;

    public CommunicationManager(InetAddress ip, int port, IDevice device) {
        this.ip = ip;
        this.port = port;
        this.device = device;
    }

    public void doWork() {
        try {
            ServerSocket sslServerSocket = (SSLServerSocketFactory.getDefault()).createServerSocket(port, 2, ip);
            System.out.println("Device online " + sslServerSocket.toString() + this.device.getType());
            while (true) {
                client = sslServerSocket.accept();
                System.out.println("Connection made with " + client.getInetAddress().toString() + ":" + client.getPort());
                outStream = new PrintWriter(client.getOutputStream(), true);
                try (BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(client.getInputStream()))) {
                    String command;
                    while ((command = bufferedReader.readLine()) != null) {
                        onCommand(command);
                    }
                }
                outStream.close();
                client.close();
                System.out.println(String.format("Connection closed! Bye bye '%s:%d'.", client.getInetAddress().toString(), client.getPort()));
            }
        } catch (IOException ex) {
            Logger.getLogger(CommunicationManager.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void onCommand(String command) {
        try {
            int header;
            if (command.length() > 2) header = Integer.valueOf(command.substring(0, 3));
            else throw new Exception(String.format("Invalid header '%s' received.", command));
            String payload = command.substring(3);
            System.out.println("Header: " + header);
            switch (header) {
                case Protocol.OPEN:
                    device.open();
                    outStream.println(Protocol.SUCCESS);
                    break;
                case Protocol.IS_OPENED:
                    outStream.println(Protocol.SUCCESS + device.isOpened().toString());
                    break;
                case Protocol.CLOSE:
                    device.close();
                    outStream.println(Protocol.SUCCESS);
                    break;
                case Protocol.GET_TYPE:
                    String type = device.getType();
                    outStream.println(Protocol.SUCCESS + type);
                    break;
                case Protocol.GET_PARAMS:
                    AcceptedParams acceptedParams = device.getAcceptedParams();
                    outStream.println(Protocol.SUCCESS + acceptedParams.toString());
                    break;
                case Protocol.COMMAND:
                    Params params = new Params();
                    params.addData(payload);
                    device.command(params);
                    outStream.println(Protocol.SUCCESS);
                    break;
                case Protocol.QUERRY_DATA:
                    Data querry_data = new Data();
                    querry_data.addData(payload);
                    Params parameters = device.queryData(querry_data);
                    outStream.println(Protocol.SUCCESS + parameters.toString());
                    break;
                default:
                    throw new Exception(String.format("Invalid header '%d' received.", header));
            }
        } catch (Exception e) {
            outStream.println(String.format("%d%s", Protocol.EXCEPTION, e.getMessage()));
//            e.printStackTrace();
        }
    }
}
