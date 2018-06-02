package sample.Controllers;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.jetbrains.annotations.Contract;
import sample.Models.CabinetronAuthenticator;
import sample.Models.UserModels.Authenticator;
import sample.Models.UserModels.Session;
import sun.plugin.javascript.navig.Anchor;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {

    @FXML private TextField userNameTextBox;
    @FXML private TextField emailField;
    @FXML private Button signInButton;
    private FXMLLoader fxmlLoader;
   // private Scene scene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //fxmlLoader = new FXMLLoader(getClass().getResource("Views/MainView.fxml"));
        signInButton.setOnAction(event -> {
            try {
                Authenticator authenticator = new CabinetronAuthenticator();
                Session session = authenticator.authenticateSession(getUserName(), getEmail());
                if (session != null) {
                    Stage loginStage = (Stage) signInButton.getScene().getWindow();
                    loginStage.hide();
                    //AnchorPane root = FXMLLoader.load(getClass().getResource("../Views/MainView.fxml"));
                    //Scene scene = new Scene(root);
                    //Scene scene = new Scene(new AnchorPane());
                    Stage stage = new Stage();

                    MainController mainController = new MainController(session);
                    mainController.init(stage);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/MainView.fxml"));
                    //RaRloader.setController(mainController);
                    loader.setController(mainController);
                    //loader.setRoot();
                    //scene.setRoot(loader.load());
                    AnchorPane pane = (AnchorPane)loader.load();
                    Scene scene = new Scene(pane);



                    //PartsFirstRunController partsFirstRunController = new PartsFirstRunController();
                    stage.setTitle("FXML Welcome");
                    stage.setScene(scene);
                    stage.show();
                    //partsFirstRunController.setStage(stage);
                } else {
                    System.out.println("Could not create session");
                }
            } catch (SQLException | IOException e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid User");
                alert.setHeaderText("User Not Found");
                e.printStackTrace();
                alert.setContentText(e.getMessage());
                alert.show();
            }
        });
    }

    @Contract(pure = true)
    private StringProperty userNameProperty(){return userNameTextBox.textProperty();}

    @Contract(pure = true)
    private StringProperty emailProperty(){return emailField.textProperty();}

    private String getEmail(){System.out.println(emailProperty().get());return emailProperty().get();}

    private String getUserName(){System.out.println(userNameProperty().get());return userNameProperty().get();}

    public void setUserName(String userName){ this.userNameTextBox.setText(userName); }

    public void setEmailField(String email){ this.emailField.setText(email); }

}
