package sample;

public class Question {
    private int num_q;
    private String theme;
    private String nature; //{"text" or "image"}
    private String question; //{text or path of the image}
    private Answers[] answers;

    public Question(int num_q, String theme) {
        this.num_q = num_q;
        this.theme = theme;
    }
    public Question(int num_q, String theme, String nature, String question, Answers[] answers) {
        this.num_q = num_q;
        this.theme = theme;
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

    public String getNature() {
        return nature;
    }

    public String getQuestion() {
        return question;
    }

    public Answers[] getAnswers() {
        return answers;
    }
}
