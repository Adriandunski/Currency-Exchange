package controllers;

import classes.Currence;
import classes.DbConnection.DbHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PersonMenuController implements Initializable {

    @FXML
    private JFXButton buttonCheck;

    @FXML
    private GridPane grindPaneCurrence;

    @FXML
    private ChoiceBox<Currence> choiceBoxA;

    @FXML
    private ChoiceBox<Currence> choiceBoxB;

    @FXML
    private JFXTextField fieldCheck;

    @FXML
    private JFXButton buttonLogOut;

    @FXML
    private Label labelDeposit;

    @FXML
    private Label labelQuantity;

    @FXML
    private Label labelExchangeA;

    @FXML
    private Label labelExchangeB;

    @FXML
    private Label labelRecieveA;

    @FXML
    private Label labelReciveB;

    @FXML
    private JFXButton buttonCheckOut;

    @FXML
    private JFXButton buttonCancel;

    private Map<Currence, Double> funds = new HashMap<>();
    private Connection connection = new DbHandler().getConnection();
    private DbHandler handler;
    private int indexOfRow = 0; // Index of GrindPane;

    private LoginWindowController instanceLoginMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadFunds();

        choiceBoxA.addEventFilter(Event.ANY, x -> checkConditions());
        choiceBoxB.addEventFilter(Event.ANY, x -> checkConditions());
        fieldCheck.addEventFilter(Event.ANY, x -> {
            checkConditions();

            if (checkNumbers() && choiceBoxA.getValue() != null) {
                labelQuantity.setText(String.format("%.2f %s",
                        Double.parseDouble(fieldCheck.getText()), choiceBoxA.getValue().getCurrence()));
            }
        });

    }

    @FXML
    void cancelExchange(ActionEvent event) {

        buttonCheck.setVisible(true);
        buttonCheckOut.setVisible(false);
        buttonCancel.setVisible(false);

        labelExchangeA.setVisible(false);
        labelExchangeB.setVisible(false);
        labelRecieveA.setVisible(false);
        labelReciveB.setVisible(false);
    }

    @FXML
    void checkCurrence(ActionEvent event) {

        buttonCheck.setVisible(false);
        buttonCheckOut.setVisible(true);
        buttonCancel.setVisible(true);

        labelExchangeA.setVisible(true);
        labelExchangeB.setVisible(true);
        labelRecieveA.setVisible(true);
        labelReciveB.setVisible(true);

    }

    @FXML
    void checkOutExchange(ActionEvent event) {

    }

    @FXML
    void logOut(ActionEvent event) {

    }

    private void addFund() {

    }

    private void loadFunds() {

        String q1 = "SELECT * FROM customers WHERE names = 'Adrian'";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(q1);

            while (rs.next()) {
                for(Currence current : Currence.values()) {
                    String string = current.getCurrence();
                    double amount = Double.parseDouble(rs.getString(current.getCurrence()));

                    funds.put(current, amount);

                    addGrindPane(string, String.valueOf(amount));
                }
            }

            System.out.println(funds);
            addBoxesToChoiceBox();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addGrindPane(String l1, String l2) {

        Label label = new Label(l1 + ":");
        Label label2 = new Label("  " + l2);

        grindPaneCurrence.add(label, 0, indexOfRow);
        grindPaneCurrence.add(label2, 1, indexOfRow);

        indexOfRow++;
    }

    private void addBoxesToChoiceBox() {

        for (Map.Entry<Currence, Double> entries : funds.entrySet()) {

            if (entries.getValue() > 0) {
                choiceBoxA.getItems().add(entries.getKey());
            }

            choiceBoxB.getItems().add(entries.getKey());
        }
    }

    //Method check conditions to setDisable(false), two choises box must be selected and one textField.
    private void checkConditions() {

            if (choiceBoxA.getValue() != null && choiceBoxB.getValue() != null && checkNumbers()) {

                if(!choiceBoxA.getValue().equals(choiceBoxB.getValue()) && fieldCheck.getText().length() > 0) {
                    buttonCheck.setDisable(false);
                } else{
                    buttonCheck.setDisable(true);
                }

            } else {
                buttonCheck.setDisable(true);
            }
    }

    private boolean checkNumbers() {

        try {
            double temp = Double.parseDouble(fieldCheck.getText());
        } catch (NumberFormatException e) {
            labelQuantity.setText("");
            return false;
        }

        return true;
    }

    public void setInstanceLoginMenu(LoginWindowController instance) {
        this.instanceLoginMenu = instance;
    }
}
