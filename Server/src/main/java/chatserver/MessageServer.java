package chatserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MessageServer {

    private ServerSocket serverSocket;

    public void start(int port){
        try {
            serverSocket = new ServerSocket(port);

            while(true){
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e){
           System.err.println(e.getMessage());
        }
    }

    public void stop(){
        try {
            serverSocket.close();
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    private static class ClientHandler extends Thread{
        private Socket clientSocket;
        private ObjectOutputStream out;
        private DataInputStream in;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new DataInputStream(clientSocket.getInputStream());

                int sender = in.readInt();
                int reciver = in.readInt();
                String msg = in.readUTF();


                User userSender = UserCollection.getInstance().findUser(sender);
                User userReciver = UserCollection.getInstance().findUser(reciver);
                if(userSender != null && userReciver != null) {
                    if (!msg.isEmpty() && sender != -1 && reciver != -1) {
                        if (!userSender.checkIfConversationExist(reciver)) {
                            userSender.addNewConversation(reciver, new MessagesManager());
                            //System.out.println("Utworzono konwersacje");
                        }

                        if(!userReciver.checkIfConversationExist(sender)){
                            userReciver.addNewConversation(sender, new MessagesManager());
                            //System.out.println("Utworzono konwersacje");
                        }


                        userSender.addNewMessageToConversation(reciver, msg);
                        userReciver.addNewMessageToConversation(sender, msg);

                        out.writeObject(userSender.getConversationList().get(reciver).getMessageList());

                    } else {
                        if (userSender.checkIfConversationExist(reciver)) {
                            out.writeObject(userSender.getConversationList().get(reciver).getMessageList());
                        } else {
                            List<String> list = new ArrayList<>();
                            out.writeObject(list);
                        }
                    }
                }

                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e){
                System.err.println(e.getMessage());
            }
        }
    }

}
