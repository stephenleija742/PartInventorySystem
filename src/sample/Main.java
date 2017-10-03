package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Parts.PartsFirstRunController;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    public static String screen1ID = "main";
    public static String screen1File = "MainPanel.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{

        //URL location = getClass().getResource("MainView.fxml");

        //Locale locale = new Locale("en", "US");
        //ResourceBundle resources = ResourceBundle.getBundle("cabinetronbundle", locale);
        //FXMLLoader fxmlLoader = new FXMLLoader(location,resources);


        //AnchorPane anchorPane = fxmlLoader.load();
        //PartsFirstRunController partsFirstRunController = fxmlLoader.getController();
        AnchorPane root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        PartsFirstRunController partsFirstRunController = new PartsFirstRunController();
        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
        partsFirstRunController.setStage(stage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
