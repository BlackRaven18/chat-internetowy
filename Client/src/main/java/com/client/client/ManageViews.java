package com.client.client;

import java.io.IOException;
import java.util.Arrays;

public class ManageViews {

    public static boolean changeView(String fxml){
        Main main = new Main();
        try{
            main.changeScene(fxml, 800, 600);
            return true;

        }catch(IOException e){
            System.err.println(e.getMessage());
            System.err.println(Arrays.toString(e.getStackTrace()));
        }
        return false;

    }

    public static boolean changeView(String fxml, double width, double height){
        Main main = new Main();
        try{
            main.changeScene(fxml, width, height);
            return true;

        }catch(IOException e){
            System.err.println(e.getMessage());
            System.err.println(Arrays.toString(e.getStackTrace()));
        }

        return false;
    }
}
