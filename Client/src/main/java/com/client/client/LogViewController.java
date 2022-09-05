package com.client.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LogViewController implements Initializable {

    @FXML
    TextField loginField, passwordField, idField;

    @FXML
    Label warningLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TextFieldFormatConfigurator configurator = new TextFieldFormatConfigurator();
        configurator.configureNumericTextField(idField, TextFieldFormatConfigurator.intFormat);

        warningLabel.setTextFill(Color.RED);
        warningLabel.setText("");

    }

    @FXML
    public void logIntoAccount(){
        warningLabel.setTextFill(Color.RED);

        ApiConnector connector = new ApiConnector();

        List<String> userData;

        if(idField.getText().isEmpty()){
            warningLabel.setText("Musisz podać identyfikator!");
            return;
        }

        userData = connector.GETuser(Integer.parseInt(idField.getText()));


        if(userData == null){
            warningLabel.setText("Nie znaleziono uzytkownika o podanym id!");
            return;
        }


        String login = userData.get(JSONUserDataFormat.LOGIN.ordinal());
        String password = userData.get(JSONUserDataFormat.PASSWORD.ordinal());

        if(!loginField.getText().equals(login)){
            warningLabel.setText("Niepoprawny login!");
            return;
        }

        if(!passwordField.getText().equals(password)){
            warningLabel.setText("Niepoprawne hasło!");
            return;
        }

        goToChatView(userData);
    }

    private void goToChatView(List<String> userData){
        Main.setUserID(Integer.parseInt(userData.get(JSONUserDataFormat.ID.ordinal())));
        Main.setUserName(userData.get(JSONUserDataFormat.NAME.ordinal()));
        Main.setUserSurname(userData.get(JSONUserDataFormat.SURNAME.ordinal()));
        ManageViews.changeView("chat-view.fxml");

    }

    @FXML
    public void goToAddModifyDeleteUser(){
        ManageViews.changeView("add-delete-modify-user-view.fxml");

    }


}
