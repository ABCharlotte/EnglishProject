package code;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class AddQuestionController extends MenuController {
    public BorderPane borderPane;
    public VBox leftVbox;
    public VBox rightVbox;
    public TextField questionTextField;
    public Button cancelButton;
    public Button okButton;
    private int questionNumber=1;
    //public Button plusButton= new Button("+").setOnAction(event -> addQuestion());

    @Override
    void initialize() {
        //this.leftVbox.getChildren().add(0,this.plusButton);
    }

    @FXML
    private void handleOK(){

    }
    @FXML
    private void handleCancel(){

    }
    private void addQuestion(){
        this.leftVbox.getChildren().remove(0);
        this.leftVbox.getChildren().addAll(new Label("Question "+this.questionNumber),new TextField());
    }
}
