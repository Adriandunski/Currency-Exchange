package controllers;

import classes.DbConnection.DbHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginWindowController {

    @FXML private JFXButton buttonSignUp;
    @FXML private JFXPasswordField fieldPassword;
    @FXML private JFXTextField fieldLogin;
    @FXML private JFXButton buttonLogIn;
    @FXML private JFXButton buttonForogtYourPassword;
    @FXML private JFXCheckBox checkBoxRememberMe;

    private static LoginWindowController loginWindowControllerInstance;
    private DbHandler handler;
    private Connection connection;

    public LoginWindowController() {
        loginWindowControllerInstance = this;
    }

    public static LoginWindowController getLoginInstance() {
        return loginWindowControllerInstance;
    }

    public String getName() {
        return fieldLogin.getText();
    }

    @FXML
    void initialize() {

        handler = new DbHandler();
        setFields();

        fieldLogin.setStyle("-fx-text-inner-color: #FFFFFF; -fx-prompt-text-fill: #FFFFFF");
        fieldPassword.setStyle("-fx-text-inner-color: #FFFFFF; -fx-prompt-text-fill: #FFFFFF");
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
                    successLogin(event);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxmls/SignUp.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void successLogin(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxmls/PersonWindow.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter fw = new FileWriter("Pass.txt");
            PrintWriter pw = new PrintWriter(fw);

            if (checkBoxRememberMe.isSelected()) {

                pw.print("Login - " + fieldLogin.getText());
                pw.print("Password - " + fieldPassword.getText());
                pw.print("Remember - " + "T");
            } else {
                pw.print("");
            }

            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setFields() {

        File f = new File("Pass.txt");

        if (f.exists()) {
            try {
                FileReader fr = new FileReader("Pass.txt");
                BufferedReader bf = new BufferedReader(fr);

                String output;
                StringBuffer stringBuffer = new StringBuffer();

                while ((output = bf.readLine()) != null) {
                    stringBuffer.append(output);
                }

                bf.close();

                String BFname = stringBuffer.substring(8, stringBuffer.indexOf("Password"));
                String BFpassword = stringBuffer.substring(stringBuffer.indexOf("Password") + 11, stringBuffer.indexOf("Remember"));
                String BFremember = stringBuffer.substring(stringBuffer.length() - 1);

                fieldLogin.setText(BFname);
                fieldPassword.setText(BFpassword);

                if (BFremember.equals("T")) {
                    checkBoxRememberMe.setSelected(true);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (StringIndexOutOfBoundsException e) {

            }
        }
    }
}
