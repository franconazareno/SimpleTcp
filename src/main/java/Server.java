import java.io.*;
import java.net.*;

public class Server {

    private static Socket socket;
    public static void main(String[] args) {
        try {
            int port = 25000;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server Started and listening to the port 25000");

            //Server is running always. This is done using this while(true) loop
            while (true) {
                //Reading the message from the client
                socket = serverSocket.accept();
                InputStream is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String fromClient = br.readLine();
                System.out.println("Message received from client is " + fromClient);

                String returnMessage;
                try {
                    int numberInIntFormat = Integer.parseInt(fromClient);
                    int returnValue = numberInIntFormat * 2;
                    returnMessage = String.valueOf(returnValue) + "\n";
                } catch (NumberFormatException e) {
                    //Input was not a number. Sending proper message back to client.
                    returnMessage = "Please send a proper number\n";
                }

                //Sending the response back to the client.
                OutputStream os = socket.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                bw.write(returnMessage);
                System.out.println("Message sent to the client is " + returnMessage);
                bw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}