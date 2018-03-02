package code;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonTools {
    private static final String filePath = "src/data/questions.json";
    //private static int nbOfQuest=3;

    public  static Question[] loadQuestions(){
        Question[] questions = new Question[0];
        try {
            FileReader reader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonQuest = (JSONObject) jsonParser.parse(reader);
            int nbOfQuest=jsonQuest.size();
            questions = new Question[nbOfQuest];
            for (int i = 0; i < nbOfQuest ; i++) {
                JSONObject quest= (JSONObject) jsonQuest.get(""+i+"");
                //System.out.println(i);
                String theme = (String) quest.get("theme");
                //System.out.println("theme question "+i+":"+quest.get("theme"));
                boolean val = quest.get("validated").equals(true);
                Question question = new Question(i,theme, val);
                questions[i]=question;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public static Question loadAnswers(int num_q){
        Question question = new Question(num_q,"error",false);
        try {
            FileReader reader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonQuest = (JSONObject) jsonParser.parse(reader);
            JSONObject quest = (JSONObject) jsonQuest.get(""+num_q+"");
            String theme = (String) quest.get("theme");
            boolean val = quest.get("validated").equals(true);
            String nature = (String) quest.get("nature");
            String text = (String) quest.get("question");
            JSONArray ansArray = (JSONArray) quest.get("answers");
            Answer[] answers = new Answer[ansArray.size()];
            for (int i = 0; i < ansArray.size() ; i++) {
                JSONObject ans= (JSONObject) ansArray.get(i);
                int num_a = (int) ((long)ans.get("number"));
                boolean vis = ans.get("visibility").equals(true);
                JSONArray ansArr = (JSONArray) ans.get("answer");
                                String[] accepted = new String[ansArr.size()];
                for (int j = 0; j < ansArr.size(); j++) {
                    accepted[j]= (String) ansArr.get(j);
                }
                JSONObject jsonHint = (JSONObject) ans.get("hint");
                String[] hint = new String[2];
                hint[0]= (String) jsonHint.get("nature");
                hint[1]= (String) jsonHint.get("is");
                answers[i]= new Answer(num_a,vis,accepted,hint);
            }
            question = new Question(num_q,theme,val,nature,text,answers);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return question;
    }

    public void validQuestion(Question question){}

    public void validAnswer(Answer answer){

    }

    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("panda !");

        JsonTools L = new JsonTools();
        Question[] q= L.loadQuestions();
        System.out.println(q[0].getTheme());
        Question qAndA = L.loadAnswers(0);
        System.out.println(qAndA.getAnswers()[1].getAccepted()[0]);
    }
}

