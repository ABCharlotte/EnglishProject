package code;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class LoadController extends MenuController{
    public BorderPane borderPane;
    public TextField textField;
    public Button okButton;
    public Button cancelButton;

    @Override
    void initialize() {
        super.initialize();
    }

    public void handleCancel() throws IOException {
        this.switchFXML("fxml/Menu.fxml");
    }

    public void handleOK() throws IOException, ParseException {
        //System.out.println(this.textField.getText());
        JsonTools J = new JsonTools();
        J.load_player(this.textField.getText().toLowerCase());
        Stage stage= (Stage) this.borderPane.getScene().getWindow();
        stage.setTitle("English Project: "+J.getPlayer());
        super.switchFXML("fxml/Menu.fxml");
    }
}
