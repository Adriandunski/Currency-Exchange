package controllers;

import classes.DbConnection.DbHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class SignUpController {

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

    public SignUpController() {

    }

    @FXML
    void initialize() {
        handler = new DbHandler();
    }

    @FXML
    void logIn(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxmls/LoginWindow.fxml"));
            System.out.println();
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
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
