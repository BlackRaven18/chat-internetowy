package com.client.client;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChatViewController implements Initializable {

    @FXML
    ListView<String> userListView, messageListView;

    @FXML
    Label userNameLabel, userSurnameLabel;

    @FXML
    TextArea sendMessageTextArea;

    List<List<String>> mainList;
    String message;
    ApiConnector connector = new ApiConnector();
    Runnable testAreaRefresher = () ->  refreshMessageArea();



    public static ArrayList<String> messageList = new ArrayList<>();



    private void refreshMessageArea(){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                int items = messageList.size();

                message = "";
                getMessages();

                messageListView.getItems().setAll(messageList);
                messageListView.scrollTo(items);
            }
        });

    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.executor = Executors.newScheduledThreadPool(1);
        Main.executor.scheduleAtFixedRate(testAreaRefresher,0, 1, TimeUnit.SECONDS);
        message = "";


        mainList = connector.GETusers();
        String nameShown;

        if(mainList == null){
            return;
        }

        for(List<String> userData : mainList){
            if(Integer.parseInt(userData.get(JSONUserDataFormat.ID.ordinal())) == Main.getUserID()){
                userNameLabel.setText(userData.get(JSONUserDataFormat.NAME.ordinal()));
                userSurnameLabel.setText(userData.get(JSONUserDataFormat.SURNAME.ordinal()));

                continue;
            }
            nameShown = userData.get(JSONUserDataFormat.NAME.ordinal()) + " " + userData.get(JSONUserDataFormat.SURNAME.ordinal());
            userListView.getItems().add(nameShown);
        }

        if(!userListView.getItems().isEmpty()){
            userListView.getSelectionModel().selectFirst();
        }
    }



    @FXML
    public void getMessages(){
        SocketManager socketManager = new SocketManager();
        socketManager.startConnection(Main.host, Main.port);
        socketManager.sendMessage(Main.getUserID(),matchUserOnListWithUserOnServer(),"");
        socketManager.stopConnection();

    }


    @FXML
    public void sendMessage(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime localTime = LocalDateTime.now();
        String convertedTime = localTime.format(formatter);

        message = Main.getUserName() + " " + Main.getUserSurname() + "[ " + convertedTime + "]: " + sendMessageTextArea.getText();
        if(sendMessageTextArea.getText().isEmpty()){
            return;
        }
        SocketManager socketManager = new SocketManager();
        socketManager.startConnection(Main.host, Main.port);
        System.out.println(userListView.getSelectionModel().getSelectedIndex());
        socketManager.sendMessage(Main.getUserID(), matchUserOnListWithUserOnServer(), message);
        sendMessageTextArea.clear();
        socketManager.stopConnection();

    }

    private int matchUserOnListWithUserOnServer(){
        for(List<String> userData : mainList){
            String name = userData.get(JSONUserDataFormat.NAME.ordinal()) + " " + userData.get(JSONUserDataFormat.SURNAME.ordinal());
            String nameOnList = userListView.getSelectionModel().getSelectedItem();
            if(name.equals(nameOnList)){
                return Integer.parseInt(userData.get(JSONUserDataFormat.ID.ordinal()));
            }
        }
        return -1;
    }

    @FXML
    public void logOut(){
        Main.setUserID(-1);
        ManageViews.changeView("log-view.fxml");
        Main.executor.shutdown();

    }





}
