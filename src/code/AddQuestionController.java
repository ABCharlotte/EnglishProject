package code;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.json.simple.parser.ParseException;

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
    public ScrollPane scrPane;
    public BorderPane scrlBorderPane;
    private int answerNumber =0;

    @Override
    public void initialize() {
        this.addQuestion();
    }

    public void update(){
        this.scrlBorderPane.setPrefWidth(this.scrPane.getWidth());
    }

    @FXML
    private void handleOK() throws IOException, ParseException {
        Question q = this.getQandA();
        JsonTools J = new JsonTools();
        J.addQuestion(q);
        System.out.println("Question added");
        super.switchFXML("fxml/main.fxml");
    }
    @FXML
    private void handleCancel() throws IOException {
        super.switchFXML("fxml/main.fxml");
    }
    public void addQuestion(){
        this.leftVbox.getChildren().remove(answerNumber*2);
        this.answerNumber++;
        this.leftVbox.getChildren().addAll(new Label("Answer "+this.answerNumber),new TextField(),this.plusButton);
        this.rightVbox.getChildren().addAll(new Label("Hint "+this.answerNumber),new TextField());
    }

    private Question getQandA(){
        Answer[] answers = new Answer[answerNumber];
        for (int i = 1; i<= answerNumber; i++){
            TextField answer=(TextField) this.leftVbox.getChildren().get(i*2-1);
            TextField hint=(TextField) this.rightVbox.getChildren().get(i*2-1);
            String[] answerString = {answer.getText()};
            String[] hintString = {"Text", hint.getText()};
            answers[i-1]=new Answer(i,false,answerString,hintString);
        }
        Question result= new Question(0,this.themeTextField.getText(),false,"Text",this.questionTextField.getText(),answers);
        return result;
    }
}
