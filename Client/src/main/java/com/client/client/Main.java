package com.client.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Main extends Application {



    private static Stage fakeStage;
    private static int userID;
    private static String userName;
    private static String userSurname;
    public static ScheduledExecutorService executor;

    public static final String host = "127.0.0.1";
    public static final int port = 5555;



    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        fakeStage = stage;
        userID = -1;


        executor = Executors.newScheduledThreadPool(1);


        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("log-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Chat Application");
        stage.setScene(scene);
        stage.show();
    }



    @Override
    public void stop() throws Exception {
        super.stop();
        executor.shutdown();

    }



    public void changeScene(String fxml, double width, double height) throws IOException{
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
        fakeStage.getScene().setRoot(parent);
        fakeStage.setWidth(width + 14.4);
        fakeStage.setHeight(height + 37.6);
        fakeStage.centerOnScreen();
    }

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int userID) {
        Main.userID = userID;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        Main.userName = userName;
    }

    public static String getUserSurname() {
        return userSurname;
    }

    public static void setUserSurname(String userSurname) {
        Main.userSurname = userSurname;
    }
}