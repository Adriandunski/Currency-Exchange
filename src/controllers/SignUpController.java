package controllers;

import classes.DbConnection.DbHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private JFXTextField fieldLogin;

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
    private Label labelBusy;

    private Connection connection;
    private DbHandler handler;
    private PreparedStatement preparedStatement;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        handler = new DbHandler();
    }

    @FXML
    void logIn(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxmls/LoginWindow.fxml"));
            Scene scene = new Scene(root);

            buttonSignIn.getScene().getWindow().hide();

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Login Cantor");
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void signUp(ActionEvent event) {

        connection = handler.getConnection();

        String insert = "INSERT INTO customers(names, password, gender, location)" +
                        "VALUES(?,?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(insert);

            preparedStatement.setString(1, fieldLogin.getText());
            preparedStatement.setString(2, fieldPassword.getText());
            preparedStatement.setString(3, getGender());
            preparedStatement.setString(4, fieldLocation.getText());

            if (checkNameInDatabase(fieldLogin.getText())) {
                System.out.println("Nazwa użytkownika zajęta.");
                labelBusy.setVisible(true);
            } else {
                //preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getGender() {

        if (radioMale.isSelected()) {
            return "Male";
        } else {
            return "Female";
        }
    }

    // Method check if the Name is in the Database
    private boolean checkNameInDatabase(String name) {

        String q1 = "SELECT names FROM customers";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(q1);

            while (rs.next()) {
                String names = rs.getString("names");

                if(names.equals(name)) {
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
