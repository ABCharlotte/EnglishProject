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

    private void switchFXML(String name, String playername) throws IOException {
        Stage stage;
        Parent root;
        //get reference to the button's stage
        stage=(Stage) borderPane.getScene().getWindow();
        double height=stage.getHeight();
        double width=stage.getWidth();
        //load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource(name));
        //create a new scene with root and setNum the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setHeight(height);
        stage.setWidth(width);
        stage.setTitle(playername);
        stage.show();
    }
    public void handleCancel() throws IOException {
        this.switchFXML("fxml/Menu.fxml");
    }

    public void handleOK() throws IOException, ParseException {
        //System.out.println(this.textField.getText());
        JsonTools J = new JsonTools();
        J.load_player(this.textField.getText().toLowerCase());
        this.switchFXML("fxml/Menu.fxml","English Project: "+J.getPlayer());
    }
}
