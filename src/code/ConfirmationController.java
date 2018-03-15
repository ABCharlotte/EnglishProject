package code;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class ConfirmationController {
    public BorderPane borderPane;
    public Button yesButton;
    public Button noButton;

    private void switchFXML(String name) throws IOException {
        Stage stage;
        Parent root;
        //get reference to the button's stage
        stage=(Stage) borderPane.getScene().getWindow();
        double height=stage.getHeight();
        double width=stage.getWidth();
        //load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource(name));
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setHeight(height);
        stage.setWidth(width);
        stage.show();

    }

    public void handleYesButton(ActionEvent actionEvent) throws IOException, ParseException {
        JsonTools J = new JsonTools();
        J.json_erase(J.getPlayer());
        switchFXML("fxml/Menu.fxml");
    }

    public void handleNoButton(ActionEvent actionEvent) throws IOException {
        switchFXML("fxml/Menu.fxml");
    }
}
