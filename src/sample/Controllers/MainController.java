package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import sample.Models.UserModels.Session;

public class MainController {

    @FXML private TabPane tabPane;
    private Session session;

    public MainController(Session session){
        this.session = session;
    }

    public void init(Stage stage){
        PartsFirstRunController partsFirstRunController = new PartsFirstRunController(session);
        partsFirstRunController.setStage(stage);
    }
}
