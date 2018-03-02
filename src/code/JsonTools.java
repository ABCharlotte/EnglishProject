package code;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
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

    public void validQuestion(Question question) throws IOException, ParseException {
        FileReader reader = new FileReader(filePath);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonFile = (JSONObject) jsonParser.parse(reader);
        JSONObject quest = (JSONObject) jsonFile.get(""+question.getNum_q()+"");
        quest.put("validated",new Boolean(true));
        reader.close();
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(jsonFile.toJSONString());
            writer.flush();
        }
    }

    public void validAnswer(Question question, int num_a) throws IOException, ParseException {
        FileReader reader = new FileReader(filePath);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonFile = (JSONObject) jsonParser.parse(reader);
        JSONObject quest = (JSONObject) jsonFile.get(""+question.getNum_q()+"");
        JSONArray answers = (JSONArray) quest.get("answers");
        JSONObject a = (JSONObject) answers.get(num_a-1);
        a.put("visibility", new Boolean(true));
        //System.out.println(a.get("visibility"));
        /*answers.set(2,a);
        quest.put("answers",answers);
        jsonFile.put("2",quest);
        */
        reader.close();
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(jsonFile.toJSONString());
            writer.flush();
            //System.out.println("Successfully Copied JSON Object to File...");
            //System.out.println("\nJSON Object: " + jsonFile);
        }/*
        try (FileWriter writer = new FileWriter(filePath)) {
            JSONWriter jsonWriter = new JSONWriter(writer);
            jsonWriter.object("2");
            jsonWriter.object();
            jsonWriter.key("2");
            jsonWriter.value(a);
            jsonWriter.endObject();
            writer.flush();
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: ");
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

//verification if Question must be validated
        boolean toValid=true;
        int i=0;
        while(toValid && i <answers.size()) {
            JSONObject ans= (JSONObject) answers.get(i);
            toValid = ans.get("visibility").equals(true);
            i++;
        }
        if (toValid) {
            validQuestion(question);
        }
    }

    public static void main(String[] args) throws IOException, ParseException {

        System.out.println("panda !");

        JsonTools L = new JsonTools();
        Question[] q= L.loadQuestions();
        //System.out.println(q[0].getTheme());
        Question qAndA = L.loadAnswers(7);
        System.out.println(qAndA.getAnswers()[0].getAccepted()[1]);
        //System.out.println(qAndA.getAnswers()[2].getAccepted()[0]);
        //System.out.println(qAndA.getAnswers()[2].isVisibility());
        //L.validAnswer(qAndA,1);
        //System.out.println(qAndA.getAnswers()[2].isVisibility());
        System.out.println(qAndA.checkAnswers("back"));

    }
}

