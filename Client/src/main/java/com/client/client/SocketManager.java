package com.client.client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class SocketManager {
    Socket clientSocket;
    DataOutputStream out;
    ObjectInputStream in;

    public boolean startConnection(String ip, int port){
        try {
            clientSocket = new Socket(ip, port);
            out = new DataOutputStream(clientSocket.getOutputStream());//new PrintWriter(clientSocket.getOutputStream(), true);
            in = new ObjectInputStream(clientSocket.getInputStream());
            return true;

        } catch (IOException e){
            System.err.println(e.getMessage());
        }

        return false;
    }

    public void sendMessage(int sender, int reciver, String msg){
        String resp = "";
        try {

            out.writeInt(sender);
            out.writeInt(reciver);
            out.writeUTF(msg);

            ChatViewController.messageList = (ArrayList<String>) in.readObject();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e){
            System.err.println(e.getMessage());
        }

    }

    public boolean stopConnection(){
        try {
            in.close();
            out.close();
            clientSocket.close();
            return true;
        } catch (IOException e){
            System.err.println(e.getMessage());
        }

        return false;
    }

}
