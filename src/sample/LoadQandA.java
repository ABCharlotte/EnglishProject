package sample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LoadQandA {
    private static final String filePath = "src/data/questions.json";
    private static int nbOfQuest=2;

    public  static Question[] loadQuestions(){
        Question[] questions = new Question[nbOfQuest];
        try {
            FileReader reader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonQuest = (JSONObject) jsonParser.parse(reader);
            for (int i = 1; i <= nbOfQuest ; i++) {
                JSONObject quest= (JSONObject) jsonQuest.get(""+i+"");
                String theme = (String) quest.get("theme");
                boolean val = quest.get("validated").equals("True");
                Question question = new Question(i,theme, val);
                questions[i-1]=question;
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
            boolean val = quest.get("validated").equals("True");
            String nature = (String) quest.get("nature");
            String text = (String) quest.get("question");
            JSONArray ansArray = (JSONArray) quest.get("answers");
            Answers[] answers = new Answers[ansArray.size()];
            for (int i = 0; i < ansArray.size() ; i++) {
                JSONObject ans= (JSONObject) ansArray.get(i);
                int num_a = (int) ((long)ans.get("number"));
                boolean vis = ans.get("visibility").equals("True");
                JSONArray ansArr = (JSONArray) ans.get("answer");
                                String[] accepted = new String[ansArr.size()];
                for (int j = 0; j < ansArr.size(); j++) {
                    accepted[j]= (String) ansArr.get(j);
                }
                JSONObject jsonHint = (JSONObject) ans.get("hint");
                String[] hint = new String[2];
                hint[0]= (String) jsonHint.get("nature");
                hint[1]= (String) jsonHint.get("is");
                answers[i]= new Answers(num_a,vis,accepted,hint);
            }
            question = new Question(num_q,theme,val,nature,text,answers);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return question;
    }

    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("panda !");

        LoadQandA L = new LoadQandA();
        Question[] q= L.loadQuestions();
        System.out.println(q[0].getTheme());
        Question qAndA = L.loadAnswers(2);
        System.out.println(qAndA.getQuestion());
        /*

        //Gson g = new Gson();
        try {
            //System.out.println("panda in the loop !");

            // read the json file
            FileReader reader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();

            //JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            //String firstName = (String) jsonObject.get("firstName");
            //System.out.println("The first name is: " + firstName);

            JSONObject jsonQuest = (JSONObject) jsonParser.parse(reader);
            JSONObject quest0= (JSONObject) jsonQuest.get("1");
            String a = (String) quest0.get("theme");
            System.out.println(a);
            JSONArray ansArray = (JSONArray) quest0.get("answers");
            for (int i = 0; i < ansArray.size() ; i++) {
                JSONObject ans= (JSONObject) ansArray.get(i);
                boolean aa = ans.get("visibility").equals("True");
                System.out.println(aa);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
}

