package code;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class AddQuestionController extends MenuController {
    public BorderPane borderPane;
    public VBox leftVbox;
    public VBox rightVbox;
    public Button cancelButton;
    public Button okButton;
    public Button plusButton;
    public TextField questionTextField;
    public TextField themeTextField;
    private int questionNumber=0;

    @Override
    public void initialize() {
        this.addQuestion();
    }

    @FXML
    private void handleOK() throws IOException {
        this.getQandA();
        super.switchFXML("fxml/main.fxml");
    }
    @FXML
    private void handleCancel() throws IOException {
        super.switchFXML("fxml/main.fxml");
    }
    public void addQuestion(){
        this.leftVbox.getChildren().remove(questionNumber*2);
        this.questionNumber++;
        this.leftVbox.getChildren().addAll(new Label("Question "+this.questionNumber),new TextField(),this.plusButton);
        this.rightVbox.getChildren().addAll(new Label("Hint "+this.questionNumber),new TextField());
    }

    private Question getQandA(){
        Answer[] answers = new Answer[questionNumber];
        for (int i=1;i<=questionNumber;i++){
            TextField answer=(TextField) this.leftVbox.getChildren().get(i*2-1);
            TextField hint=(TextField) this.rightVbox.getChildren().get(i*2-1);
            String[] answerString={answer.getText()};
            String[] hintString={"Text", hint.getText()};
            answers[i-1]=new Answer(i,false,answerString,hintString);
        }
        Question result= new Question(0,this.themeTextField.getText(),false,"Text",this.questionTextField.getText(),answers);
        return result;
    }
}
