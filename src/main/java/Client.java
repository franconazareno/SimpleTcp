import java.io.*;
import java.net.*;

public class Client {

    private static Socket socket;
    public static void main(String args[]) {
        try {
            String host = "localhost";
            int port = 25000;
            InetAddress address = InetAddress.getByName(host);
            socket = new Socket(address, port);

            //Send the message to the server
            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            String sendMessage = 4 + "\n";
            bw.write(sendMessage);
            bw.flush();
            System.out.println("Message sent to the server : "+sendMessage);

            //Get the return message from the server
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String fromServer = br.readLine();
            System.out.println("Message received from the server : " +fromServer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //Closing the socket
            try {
                socket.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

}