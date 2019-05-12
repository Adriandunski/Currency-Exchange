package controllers;

import classes.DbConnection.DbHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
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

    @FXML
    private JFXCheckBox checkBoxRememberMe;

    private Connection connection;
    private DbHandler handler;
    private static LoginWindowController loginWindowControllerInstance;

    public LoginWindowController(){
        loginWindowControllerInstance = this;
    }

    public static LoginWindowController getInstance() {
        return loginWindowControllerInstance;
    }

    public String getName() {
        return fieldLogin.getText();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        handler = new DbHandler();
        setFields();
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

            //Parent root = FXMLLoader.load(getClass().getResource("../fxmls/PersonWindow.fxml"));
            Scene scene = new Scene(root);

            buttonLogIn.getScene().getWindow().hide();

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Your Menu: " + fieldLogin.getText());
            stage.show();

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Methd imports data from txt (Remember Me)
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
