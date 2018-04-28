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
    private SSLServerSocketFactory sslServerSocketFactory;

    public CommunicationManager(InetAddress ip, int port, IDevice device) {
        this.ip = ip;
        this.port = port;
        this.device = device;
        this.sslServerSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
    }

    public void doWork() {
        try {
            ServerSocket sslServerSocket = this.sslServerSocketFactory.createServerSocket(port, 2, ip);
            System.out.println("Device online " + sslServerSocket.toString());

            Socket socket = sslServerSocket.accept();
            System.out.println("Connection made with " + socket.getInetAddress().toString() + ":" +socket.getPort());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            try (BufferedReader bufferedReader = new BufferedReader(
                                 new InputStreamReader(socket.getInputStream()))) {
                String command;
                while ((command = bufferedReader.readLine()) != null) {
                    onCommand(command);
                    out.println(command);
                }
            }
            System.out.println("Connection closed!");

        } catch (IOException ex) {
            Logger.getLogger(CommunicationManager.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    public void onCommand(String command) {
        System.out.println("Command: " + command);
    }
}
