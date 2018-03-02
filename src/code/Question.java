package code;

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
}
