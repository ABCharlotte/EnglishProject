package code;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    public Button backButton;
    @FXML
    private ScrollPane QuestionScrollPane;
    @FXML
    private GridPane GridQuestion;

    private int column=0;
    private int row=0;

    private JsonTools L= new JsonTools();

    public void initialize() {
        updateQuestions();
    }

    private void addButton(Button button, GridPane grid) {
        button.setMinSize(100,100);
        grid.add(button, column, row);
        int div = (int) (grid.getWidth()/120);
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
        Question[] q = L.loadQuestions();
        for (int i=0;i<q.length;i++){
            Button QuestionButton = new Button("Question : "+i+"\n \n"+q[i].getTheme());
            if(q[i].isValidated()){
                QuestionButton.setStyle("-fx-background-color: #00ff00");
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
        Stage stage;
        Parent root;
        //get reference to the button's stage
        stage=(Stage) GridQuestion.getScene().getWindow();
        double height=stage.getHeight();
        double width=stage.getWidth();
        //load up OTHER FXML document
        FXMLLoader fxmlLoader=new FXMLLoader();
        root = fxmlLoader.load(getClass().getResource("Question.fxml").openStream());
        QuestionController questionController= fxmlLoader.getController();
        JsonTools L = new JsonTools();
        Question q = L.loadAnswers(number_q);
        questionController.initialize(q);
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setHeight(height);
        stage.setWidth(width);
        stage.show();
    }

    public void handleBackButton() throws IOException {
        Stage stage;
        Parent root;
        //get reference to the button's stage
        stage=(Stage) GridQuestion.getScene().getWindow();
        double height=stage.getHeight();
        double width=stage.getWidth();
        //load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource("fxml/Menu.fxml"));
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setHeight(height);
        stage.setWidth(width);
        stage.show();
    }
}
