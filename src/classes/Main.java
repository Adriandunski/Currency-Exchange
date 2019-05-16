package classes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {

        System.out.println("Hi in Cantor.");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("../fxmls/LoginWindow.fxml"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxmls/LoginWindow.fxml"));
        Parent root = loader.load();

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(root);

        Scene scene = new Scene(borderPane);
        borderPane.setStyle("-fx-background-color: #000000");

        primaryStage.setScene(scene);
        primaryStage.setTitle("International Cantor");
        primaryStage.show();
    }
}
