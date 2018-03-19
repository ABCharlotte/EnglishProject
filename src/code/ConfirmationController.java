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

public class ConfirmationController extends MenuController {
    public BorderPane borderPane;
    public Button yesButton;
    public Button noButton;

    public void handleYesButton(ActionEvent actionEvent) throws IOException, ParseException {
        JsonTools J = new JsonTools();
        J.json_erase(J.getPlayer());
        Stage stage =(Stage) this.borderPane.getScene().getWindow();
        stage.setTitle("English Project");
        switchFXML("fxml/Menu.fxml");
    }

    public void handleNoButton(ActionEvent actionEvent) throws IOException {
        switchFXML("fxml/Menu.fxml");
    }
}
