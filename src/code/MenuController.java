package code;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    public Button playButton;
    public BorderPane borderPane;
    public Button resetButton;
    public Label nameLabel;
    public Button loadButton;

    void initialize(){
    }
    void switchFXML(String name) throws IOException {
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
        stage.show();
    }

    @FXML
    public void handlePlayButton() throws IOException {
        switchFXML("fxml/main.fxml");
    }
    public void handleLoadButton() throws IOException {
        this.switchFXML("fxml/Load.fxml");
        this.nameLabel.setText("Save loaded");
    }
    public void handleResetButton() throws IOException {
        switchFXML("fxml/confirmation.fxml");
    }

}
