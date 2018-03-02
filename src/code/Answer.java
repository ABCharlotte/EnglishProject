package code;

public class Answer {
    private int num_a;
    private boolean visibility;
    private String[] accepted;
    private String[] hint;

    /*public void Answer(){
        accepted= new String[10];
        hint = new String[2];
    }*/

    public Answer(int newNum_a, boolean newVisibility, String[] newAccepted, String[] newHint){
        num_a =newNum_a;
        visibility=newVisibility;
        accepted=newAccepted;
        hint=newHint;
    }

    public int getNum_a() {
        return num_a;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public String[] getAccepted() {
        return accepted;
    }

    public String[] getHint() {
        return hint;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
}
