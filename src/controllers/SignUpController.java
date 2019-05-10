package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

public class SignUpController {

    @FXML
    private AnchorPane fieldLogin;

    @FXML
    private JFXPasswordField fieldPassword;

    @FXML
    private JFXTextField fieldLocation;

    @FXML
    private JFXButton buttonLogIn;

    @FXML
    private JFXButton buttonSignIn;

    @FXML
    private JFXRadioButton radioMale;

    @FXML
    private ToggleGroup grander;

    @FXML
    private JFXRadioButton radioFemale;

    @FXML
    void logIn(ActionEvent event) {

    }

    @FXML
    void signIn(ActionEvent event) {

    }
}
