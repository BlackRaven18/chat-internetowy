package com.client.client;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.text.DecimalFormat;
import java.text.ParsePosition;

public class TextFieldFormatConfigurator {

    public static final DecimalFormat doubleFormat = new DecimalFormat("#.0");
    public static final DecimalFormat intFormat = new DecimalFormat("#");

    public boolean configureNumericTextField(TextField textField, DecimalFormat format){

        if(textField != null) {
            textField.setTextFormatter(new TextFormatter<>(c -> {
                if (c.getControlNewText().isEmpty()) {
                    return c;
                }
                ParsePosition parsePosition = new ParsePosition(0);
                Object object = format.parse(c.getControlNewText(), parsePosition);

                if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                    return null;
                } else {
                    return c;
                }
            }));
            return true;
        }
        return false;
    }

    public boolean configureCharacterTextField(TextField textField){
        if(textField != null) {
            textField.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
                String newText = change.getControlNewText();
                if (newText.length() > 20) {
                    return null;
                } else {
                    if (!change.getText().isEmpty()) {
                        if (!Character.isLetter(change.getText().charAt(0))) {
                            return null;
                        }
                    }
                    return change;
                }
            }));
            return true;
        }
        return false;
    }
}
