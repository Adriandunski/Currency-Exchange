package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginWindowController {

    @FXML
    private JFXButton buttonSignUp;

    @FXML
    private JFXPasswordField fieldPassword;

    @FXML
    private JFXTextField fieldLogin;

    @FXML
    private JFXButton buttonLogIn;

    @FXML
    private JFXButton buttonForogtYourPassword;

    @FXML
    void forgotPassword(ActionEvent event) {

    }

    @FXML
    void logIn(ActionEvent event) {

    }

    @FXML
    void signUp(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxmls/SignUp.fxml"));
            Scene scene = new Scene(root);

            buttonSignUp.getScene().getWindow().hide();

            Stage stage = new Stage();
            stage.setTitle("Sign Up");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
