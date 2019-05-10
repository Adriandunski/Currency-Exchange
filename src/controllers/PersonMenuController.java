package controllers;

import classes.Currence;
import classes.DbConnection.DbHandler;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private JFXButton test;

    @FXML
    private GridPane grindPaneCurrence;

    private Map<Currence, Double> funds = new HashMap<>();
    private Connection connection = new DbHandler().getConnection();
    private DbHandler handler;
    private int indexOfRow = 0; // Index of GrindPane;

    private LoginWindowController instanceLoginMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadFunds();
    }

    @FXML
    void test(ActionEvent event) {

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

    public void setInstanceLoginMenu(LoginWindowController instance) {
        this.instanceLoginMenu = instance;
    }
}
