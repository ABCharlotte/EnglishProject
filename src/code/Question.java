package code;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Question {
    private int num_q;
    private String theme;
    private boolean validated;
    private String nature; //{"text" or "image"}
    private String question; //{text or path of the image}
    private Answer[] answers;

    public Question(int num_q, String theme, boolean valid) {
        this.num_q = num_q;
        this.theme = theme;
        this.validated = valid;
    }
    public Question(int num_q, String theme, boolean valid, String nature, String question, Answer[] answers) {
        this.num_q = num_q;
        this.theme = theme;
        this.validated = valid;
        this.nature = nature;
        this.question = question;
        this.answers = answers;
    }

    public int getNum_q() {
        return num_q;
    }

    public String getTheme() {
        return theme;
    }

    public boolean isValidated() {
        return validated;
    }

    public String getNature() {
        return nature;
    }

    public String getQuestion() {
        return question;
    }

    public Answer[] getAnswers() {
        return answers;
    }

    public boolean checkAnswers(String toTest) throws IOException, ParseException {
        for (int i = 0; i < this.answers.length ; i++) {
            String[] accepted = answers[i].getAccepted();
            for (int j = 0; j < accepted.length ; j++) {
                if (accepted[j].equalsIgnoreCase(toTest)){
                    JsonTools J = new JsonTools();
                    J.validAnswer(this,i+1);
                    return true;
                }
            }
        }
        return false;
    }
}
