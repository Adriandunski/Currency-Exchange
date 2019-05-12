package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Currency;
import java.util.ResourceBundle;

public class AddFundController implements Initializable {

    @FXML
    private ChoiceBox<Currency> choiseBoxFunds;

    @FXML
    private JFXTextField fieldFunds;

    @FXML
    private JFXButton buttonMainMenu;

    @FXML
    private JFXButton buttonAddFunds;

    private PersonMenuController instance;

    @FXML
    void addFunds(ActionEvent event) {


    }

    @FXML
    void backToMainMenu(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxmls/PersonWindow.fxml"));
            Parent root = loader.load();
            buttonMainMenu.getScene().getWindow().hide();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInstance(PersonMenuController main) {
        this.instance = main;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
