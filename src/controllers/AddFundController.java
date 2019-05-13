package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Currency;

public class AddFundController {

    @FXML
    private ChoiceBox<Currency> choiseBoxFunds;

    @FXML
    private JFXTextField fieldFunds;

    @FXML
    private JFXButton buttonMainMenu;

    @FXML
    private JFXButton buttonAddFunds;

    @FXML
    void addFunds(ActionEvent event) {

    }

    @FXML
    void backToMainMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxmls/PersonWindow.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            System.out.println();
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
