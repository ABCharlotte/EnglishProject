package code;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

public class QuestionController extends Controller {
    public Button BackButton;
    public Label LabelQuestion;
    public GridPane GridAnswers;
    public javafx.scene.image.ImageView ImageView;
    public TextField AnswerText;
    public Button OKButton;
    public Label ErrorLabel;
    public BorderPane borderPane;
    private int row;
    private int column;
    private Question activeQuestion;

    public void initialize() {
        this.activeQuestion=Controller.loadedQuestion;
        if(Controller.loadedQuestion.getNature().equals("Text")){
            this.LabelQuestion.setText(Controller.loadedQuestion.getQuestion());
        }
        else{
            this.ImageView.setFitHeight(400);
            this.ImageView.setFitWidth(400);
            this.ImageView.setPreserveRatio(true);
            try {
                FileInputStream inputStream = new FileInputStream(Controller.loadedQuestion.getQuestion());
                Image im = new Image(inputStream);
                this.ImageView.setImage(im);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //System.out.println(question.getQuestion());

        }
        updateAnswers();

    }

    private void updateAnswers() {
            this.row=0;
            this.column=0;
            this.GridAnswers.getChildren().clear();
            JsonTools j = new JsonTools();
            activeQuestion=j.loadAnswers(activeQuestion.getNum_q());
            for (int i=0;i<this.activeQuestion.getAnswers().length;i++){
                Answer answer =this.activeQuestion.getAnswers()[i];
                Button AnswerButton = new Button("      "+answer.getNum_a()+ "\n click for hint");
                if(answer.isVisibility()){
                    AnswerButton.setStyle("-fx-background-color: #68e0ee");
                    AnswerButton.setText(answer.getAccepted()[0]);
                }
                int temp = i;
                AnswerButton.setOnAction(event -> {
                    try {
                        handleAnswerButton(answer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                super.addButton(AnswerButton,GridAnswers);
            }
        }

    @FXML
    public void handleBackButton() throws IOException {
        Stage stage;
        Parent root;
        //get reference to the button's stage
        stage=(Stage) GridAnswers.getScene().getWindow();
        double height=stage.getHeight();
        double width=stage.getWidth();
        //load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource("fxml/main.fxml"));
        //create a new scene with root and setNum the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setHeight(height);
        stage.setWidth(width);
        stage.show();
    }
    private void handleAnswerButton(Answer answer){
        this.GridAnswers.getChildren().clear();
        if(answer.getHint()[0].equals("Text")) {
            this.GridAnswers.add(new Label(answer.getHint()[1]),0,0);
        }
        else{
            ImageView imageView = new ImageView();
            //imageView.setFitHeight(300);
            //imageView.setFitWidth(300);
            try {
                imageView.setImage(new Image(new FileInputStream(answer.getHint()[1])));
                this.GridAnswers.add(imageView,0,1);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        Button BackQuestionButton = new Button("Back to the question");
        BackQuestionButton.setOnAction(event -> updateAnswers());
        this.GridAnswers.add(BackQuestionButton,0,2);
    }

    @FXML
    private void handleOK() throws IOException, ParseException {
        String answerString = this.AnswerText.getText();
        if(this.activeQuestion.checkAnswers(answerString)){
            this.ErrorLabel.setText("YES !");
            this.AnswerText.clear();
        }else {
            this.ErrorLabel.setText("Error");
        }
        this.updateAnswers();
    }
}
