package controllers;

import classes.DbConnection.DbHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginWindowController implements Initializable {

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

    private Connection connection;
    private DbHandler handler;

    private PersonMenuController instancePerson;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        handler = new DbHandler();
    }

    @FXML
    void forgotPassword(ActionEvent event) {

    }

    @FXML
    void logIn(ActionEvent event) {

        connection = handler.getConnection();

        String q1 = String.format("SELECT names, password FROM customers WHERE names = '%s'", fieldLogin.getText());

        Label:
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(q1);

            while (rs.next()) {

                String name = rs.getString("names");
                String password = rs.getString("password");

                if (password.equals(fieldPassword.getText())) {
                    System.out.println("Success!");
                    successLogin();
                    break Label;
                }
            }

            System.out.println("Wrong password or Login.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    // Method started when We entry correct Login and Password.
    private void successLogin() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxmls/PersonWindow.fxml"));
            Parent root = loader.load();
            instancePerson = loader.getController();
            instancePerson.setInstanceLoginMenu(this);

            //Parent root = FXMLLoader.load(getClass().getResource("../fxmls/PersonWindow.fxml"));
            Scene scene = new Scene(root);

            buttonLogIn.getScene().getWindow().hide();

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Your Menu: " + fieldLogin.getText());
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
