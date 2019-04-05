import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9999); // Listening port of server
            System.out.println("Server successfully booted");

            while (true){ // continuously listen for new connections
                Socket socket = serverSocket.accept(); // Accept connection
                System.out.println("Client connected");

                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream()); // For sending messages
                DataInputStream inputStream = new DataInputStream(socket.getInputStream()); // For receiving messages
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // For user input
                String msgin="", msgout="";

                while (true){ // continuously check for new messages

                    // Server first receive message and then respond

                    msgin = inputStream.readUTF(); // receive message from client

                    if (msgin.equals("exit")){
                        System.out.println("Client disconnected. Listening to other clients");
                        socket.close(); // close connection
                        break;
                    }

                    System.out.println("Client: "+msgin);

                    System.out.println("Enter message: ");
                    msgout = reader.readLine(); // user input

                    if (msgout.equals("exit")){
                        System.out.println("Server closed connection");
                        outputStream.writeUTF(msgout); // let client know that server is disconnected
                        outputStream.flush();
                        socket.close(); // close connection
                        break;
                    }

                    outputStream.writeUTF(msgout); // send message
                    outputStream.flush(); // ensure delivery of complete message
                }

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
