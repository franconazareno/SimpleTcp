import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SimpleTcpClient {
    private Socket socket;
    private Scanner scanner;

    private SimpleTcpClient(InetAddress serverAddress, int serverPort) throws Exception {
        this.socket = new Socket(serverAddress, serverPort);
        this.scanner = new Scanner(System.in);
    }

    private void start() throws Exception {
        PrintStream os = new PrintStream(socket.getOutputStream());
        DataInputStream is = new DataInputStream(socket.getInputStream());
        StringBuffer buffer = new StringBuffer(50);

        int c;
        String fromServer;

        while ((fromServer = is.readLine()) != null) {
            System.out.println("Server: " + fromServer);

            if (fromServer.equals("BYE")) {
                System.out.println("Disconnecting..");
                break;
            }

            while ((c = System.in.read()) != '\n') {
                buffer.append((char)c);
            }
            System.out.println("Client: " + buffer);
            os.println(buffer.toString());
            os.flush();
            buffer.setLength(0);
        }
    }

    public static void main(String[] args) throws Exception {
        String ipAddress = "127.0.0.1";
        int port = 6789;
        SimpleTcpClient client = new SimpleTcpClient(InetAddress.getByName(ipAddress), port);

        System.out.println("\r\nConnected to Server: " + client.socket.getInetAddress());
        client.start();
    }
}
