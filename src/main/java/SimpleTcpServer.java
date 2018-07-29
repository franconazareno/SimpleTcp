import java.net.*;
import java.io.*;

public class SimpleTcpServer {
    private ServerSocket server;

    public SimpleTcpServer(String ipAddress) throws Exception {
        if (ipAddress != null && !ipAddress.isEmpty())
            this.server = new ServerSocket(6789, 1, InetAddress.getByName(ipAddress));
        else
            this.server = new ServerSocket(0, 1, InetAddress.getLocalHost());
    }

    private void listen() throws Exception {
        Socket client = this.server.accept();
        String clientAddress = client.getInetAddress().getHostAddress();
        System.out.println("\r\nNew connection from " + clientAddress);

        DataInputStream is = new DataInputStream(new BufferedInputStream(client.getInputStream()));
        PrintStream os = new PrintStream(new BufferedOutputStream(client.getOutputStream(), 1024), false);
        String inputLine, outputLine;

        outputLine = "Hello from Server";
        os.println(outputLine);
        os.flush();

        while ((inputLine = is.readLine()) != null) {
            System.out.println("Client: " + inputLine);

            if (inputLine.equals("bye")) {
                outputLine = "BYE";
                os.println(outputLine);
                os.flush();
                System.out.println("Exiting..");

                //os.close();
                //is.close();
                //client.close();
                //System.exit(0);
            } else {
                //Simply uppercase input from client and return
                outputLine = inputLine.toUpperCase();
                os.println(outputLine);
                os.flush();
                System.out.println("Server: " + outputLine);
            }
        }
    }

    public InetAddress getSocketAddress() {
        return this.server.getInetAddress();
    }

    public int getPort() {
        return this.server.getLocalPort();
    }

    public static void main(String[] args) throws Exception {
        String ipAddress = "127.0.0.1";
        SimpleTcpServer app = new SimpleTcpServer(ipAddress);
        System.out.println("\r\nRunning Server: " + "Host=" + app.getSocketAddress().getHostAddress() + " Port=" + app.getPort());

        app.listen();
    }
}
