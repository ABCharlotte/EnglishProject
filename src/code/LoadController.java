package code;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

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
        this.switchFXML("Menu.fxml");
    }
    public void handleOK() throws IOException {
        System.out.println(this.textField.getText());
        this.handleCancel();
    }
}
