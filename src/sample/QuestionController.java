package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

public class QuestionController {
    public Button BackButton;
    public Label LabelQuestion;
    public GridPane GridAnswers;
    public javafx.scene.image.ImageView ImageView;

    private void initialize(Question question) throws MalformedURLException {
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
            //System.out.println(question.getQuestion());

        }

    }
    private void updateAnswers() {
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
}
