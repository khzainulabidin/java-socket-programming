import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            String ip = "localhost"; // IP of host. In this case, we are using same machine for both client and server so it's localhost
            int port = 9999; // Listening port of server. You can choose any port between 1024 and 65535 if it's not already busy.

            Socket socket = new Socket(ip, port); //Creates connection
            System.out.println("Successfully connected to server");

            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream()); // For sending messages
            DataInputStream inputStream = new DataInputStream(socket.getInputStream()); // For receiving messages
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // For user input
            String msgin="", msgout="";

            while (true){ // continuously check for new messages
                System.out.println("Enter message: ");
                msgout = reader.readLine(); // user input

                if (msgout.equals("exit")){
                    System.out.println("Client successfully disconnected from server");
                    outputStream.writeUTF(msgout); // let server know that client is disconnected
                    outputStream.flush();
                    socket.close(); // close connection
                    break;
                }

                outputStream.writeUTF(msgout); // send message
                outputStream.flush(); // ensure delivery of complete message

                msgin = inputStream.readUTF(); // receive message from server

                if (msgin.equals("exit")){
                    System.out.println("Server disconnected");
                    socket.close(); // close connection
                    break;
                }

                System.out.println("Server: "+msgin);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
