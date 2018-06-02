package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.Controllers.LoginScreenController;
import sample.Controllers.PartsFirstRunController;

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

        GridPane root = FXMLLoader.load(getClass().getResource("Views/LoginScreen.fxml"));
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        LoginScreenController loginScreenController = new LoginScreenController();
        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
        //loginScreenController.setStage(stage);

        /* uncomment this to run program
        AnchorPane root = FXMLLoader.load(getClass().getResource("Views/MainView.fxml"));
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        PartsFirstRunController partsFirstRunController = new PartsFirstRunController();
        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
        partsFirstRunController.setStage(stage);
        */
    }


    public static void main(String[] args) {
        launch(args);
    }
}
