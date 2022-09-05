package com.client.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddDeleteUserController implements Initializable {

    @FXML
    private TextField nameField, surnameField, loginField, passwordField, idField;

    @FXML
    private Label warningLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        writeWarningMessage("", Color.RED);

        TextFieldFormatConfigurator configurator = new TextFieldFormatConfigurator();
        configurator.configureNumericTextField(idField, TextFieldFormatConfigurator.intFormat);
    }

    private void writeWarningMessage(String message, Color color){
        warningLabel.setTextFill(color);
        warningLabel.setText(message);
    }

    @FXML
    public void addUser(){

        int response = ApiConnector.POSTuser(nameField.getText(), surnameField.getText(), loginField.getText(),
                passwordField.getText(), Integer.parseInt(idField.getText()));

        if(response == 200){
            writeWarningMessage("New user added successfully", Color.GREEN);
        } else{
            writeWarningMessage("ERROR: " +response, Color.RED);
        }

    }

    @FXML
    public void deleteUser(){
        int response = ApiConnector.DELETEuser(Integer.parseInt(idField.getText()));

        if(response == 200){
            writeWarningMessage("User deleted successfully", Color.GREEN);
        } else{
            writeWarningMessage("ERROR: " +response, Color.RED);
        }
    }

    @FXML
    public void changeUser(){
        int response = ApiConnector.PUTuser(nameField.getText(), surnameField.getText(), loginField.getText(),
                passwordField.getText(), Integer.parseInt(idField.getText()));

        if(response == 200){
            writeWarningMessage("User's data changed successfully", Color.GREEN);
        } else{
            writeWarningMessage("ERROR: " +response, Color.RED);
        }
    }

    @FXML
    public void exit(){
        Main main = new Main();
        try {
            main.changeScene("log-view.fxml", 800, 600);
        }catch (IOException e){
            System.err.println("ERROR: błąd przy powrocie z add-delete-user do log-view");
        }
    }
}
