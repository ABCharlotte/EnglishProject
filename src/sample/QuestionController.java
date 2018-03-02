package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

public class QuestionController {
    public Button BackButton;
    public Label LabelQuestion;
    public GridPane GridAnswers;
    public javafx.scene.image.ImageView ImageView;
    private int row;
    private int column;
    private Question activeQuestion;

    public void initialize(Question question) throws MalformedURLException {
        this.activeQuestion=question;
        if(question.getNature().equals("Text")){
            this.LabelQuestion.setText(question.getQuestion());
        }
        else{
            this.ImageView.setFitHeight(300);
            this.ImageView.setFitWidth(300);
            try {
                FileInputStream inputStream = new FileInputStream(question.getQuestion());
                Image im = new Image(inputStream);
                this.ImageView.setImage(im);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println(question.getQuestion());
        }
        updateAnswers();

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
    private void updateAnswers() {
            this.row=0;
            this.column=0;
            this.GridAnswers.getChildren().clear();
            for (int i=0;i<this.activeQuestion.getAnswers().length;i++){
                Answers answers =this.activeQuestion.getAnswers()[i];
                Button AnswersButton = new Button(""+i);
                if(answers.isVisibility()){
                    AnswersButton.setStyle("-fx-background-color: #00ff00");
                    AnswersButton.setText(answers.getAccepted()[0]);
                }
                int temp = i;
                AnswersButton.setOnAction(event -> {
                    try {
                        handleAnswersButton(answers);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                addButton(AnswersButton,GridAnswers);
            }
        }

    @FXML
    private void handleBackButton() throws Exception{
        Stage stage;
        Parent root;
        //get reference to the button's stage
        stage=(Stage) GridAnswers.getScene().getWindow();
        double height=stage.getHeight();
        double width=stage.getWidth();
        //load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setHeight(height);
        stage.setWidth(width);
        stage.show();
    }
    private void handleAnswersButton(Answers answers){
        this.GridAnswers.getChildren().clear();
        if(answers.getHint()[0].equals("Text")) {
            this.GridAnswers.add(new Label(answers.getHint()[1]),0,0);
        }
        else{
            ImageView imageView = new ImageView();
            imageView.setFitHeight(300);
            imageView.setFitWidth(300);
            try {
                imageView.setImage(new Image(new FileInputStream(answers.getHint()[1])));
                this.GridAnswers.add(imageView,0,1);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        Button BackQuestionButton = new Button("Back to the question");
        BackQuestionButton.setOnAction(event -> updateAnswers());
        this.GridAnswers.add(BackQuestionButton,0,2);
    }
}
