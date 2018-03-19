package code;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
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

    private JsonTools L= new JsonTools();

    public void initialize() {
        updateQuestions();
    }
    private double getScaleFactor() {
        double trueHorizontalLines = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        double scaledHorizontalLines = Screen.getPrimary().getBounds().getHeight();
        double dpiScaleFactor = trueHorizontalLines / scaledHorizontalLines;
        return dpiScaleFactor;
    }

    private void addButton(Button button, GridPane grid) {
        button.setMinSize(100,100);
        button.setMaxSize(100,100);
        button.setWrapText(true);
        grid.add(button, column, row);
        int div = (int) (borderPane.getWidth()/(120*getScaleFactor()));
        //System.out.println(getScaleFactor());
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
        root = fxmlLoader.load(getClass().getResource("fxml/Question.fxml").openStream());
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
        super.switchFXML("fxml/Menu.fxml");
    }
    public void handleAddButton() throws IOException {
        super.switchFXML("fxml/addQuestion.fxml");
    }
}
