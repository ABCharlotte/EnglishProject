package code;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller extends MenuController {

    public Button backButton;
    public Button addButton;
    @FXML
    private ScrollPane QuestionScrollPane;
    @FXML
    private GridPane GridQuestion;
    private int column=0;
    private int row=0;
    @FXML
    private BorderPane borderPane;
    public static Question loadedQuestion;
    public void initialize() {
        updateQuestions();
    }

    public void addButton(Button button, GridPane grid) {
        button.setMinSize(100,100);
        button.setMaxSize(100,100);
        button.setWrapText(true);
        grid.add(button, column, row);
        int div = (int) (borderPane.getWidth()/120);
        //System.out.println(this.mainPane.getWidth());
        if (div==0){
            div=6;
        }
        if (column < div) {
            this.column++;
        } else {
            this.column = 0;
            this.row++;
        }
    }
    public void updateQuestions() {
        this.row=0;
        this.column=0;
        this.GridQuestion.getChildren().clear();
        JsonTools J = new JsonTools();
        Question[] q = J.loadQuestions();
        for (int i=1;i<q.length;i++){
            Button QuestionButton = new Button("Question : "+i+"\n \n"+q[i].getTheme());
            if(q[i].isValidated()){
                QuestionButton.setStyle("-fx-background-color: #68e0ee");
            }
            int temp = i;
            QuestionButton.setOnAction(event -> {
                try {
                    handleButtonAction(temp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            addButton(QuestionButton,GridQuestion);
        }
    }
    private void handleButtonAction(int number_q) throws Exception{
        loadedQuestion = JsonTools.loadAnswers(number_q);
        switchFXML("fxml/Question.fxml");
    }

    public void handleBackButton() throws IOException {
        super.switchFXML("fxml/Menu.fxml");
    }
    public void handleAddButton() throws IOException {
        super.switchFXML("fxml/addQuestion.fxml");
    }
}
