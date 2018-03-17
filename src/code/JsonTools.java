package code;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class JsonTools {
    private static String filePath = "src/data/json/everybody.json";
    //private static int nbOfQuest=3;

    static Question[] loadQuestions(){

        Question[] questions = new Question[0];
        try {
            FileReader reader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonQuest = (JSONObject) jsonParser.parse(reader);
            int nbOfQuest=jsonQuest.size();
            questions = new Question[nbOfQuest];
            for (int i = 0; i < nbOfQuest ; i++) {
                JSONObject quest= (JSONObject) jsonQuest.get(""+i+"");
                //System.out.println("theme question "+i+":"+quest.get("theme"));
                String theme = (String) quest.get("theme");
                boolean val = quest.get("validated").equals(true);
                Question question = new Question(i,theme, val);
                questions[i]=question;
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return questions;
    }

    static Question loadAnswers(int num_q){
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
            reader.close();
        } catch (ParseException | IOException e) {
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
            writer.write(prettyprint(jsonFile.toJSONString()));
            writer.flush();
            writer.close();
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
        reader.close();
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(prettyprint(jsonFile.toJSONString()));
            writer.flush();
            writer.close();
        }

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

    public void load_player(String player) throws IOException, ParseException {
        filePath = "src/data/json/"+player.toLowerCase()+".json";
        if (Files.exists(Paths.get(filePath))){
            System.out.println("The file linked to the player " + player + " is loaded.");
        }else{
            json_create(player);
        }
    }

    public void json_create(String player) throws IOException, ParseException {
        filePath = "src/data/json/"+player.toLowerCase()+".json";
        FileReader reader = new FileReader("src/data/json/sauvDB.json");
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonFile = (JSONObject) jsonParser.parse(reader);
        reader.close();
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(prettyprint(jsonFile.toJSONString()));
            writer.flush();
            writer.close();
            System.out.println("The file linked to the player " + player + " is created. ");
        }

    }

    public void json_erase(String player) throws IOException, ParseException {
        if (player.equals("everybody")){
            this.json_create("everybody");
            System.out.println("The default file is reinitialised.");
        }else {
            filePath = "src/data/json/" + player.toLowerCase() + ".json";
            Files.delete(Paths.get(filePath));
            System.out.println("The file linked to the player " + player + " is deleted. ");
        }
        load_player("everybody");

    }
    public String prettyprint(String uglyJSONString){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(uglyJSONString);
        String prettyJsonString = gson.toJson(je);
        return prettyJsonString;
    }

    public String getPlayer() {
        return filePath.substring(14,filePath.length()-5);
    }

    public void addQuestion(Question q) throws IOException, ParseException {
        FileReader reader = new FileReader("src/data/json/sauvDB.json");
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonFile = (JSONObject) jsonParser.parse(reader);
        int num_q = jsonFile.size();
        reader.close();
        q.setNum(num_q);
        JSONObject quest = new JSONObject();
        quest.put("theme", q.getTheme());
        quest.put("validated", q.isValidated());
        quest.put("nature", q.getNature());
        quest.put("question", q.getQuestion());
        JSONArray ansArray = new JSONArray();
        for (int i=0; i< q.getAnswers().length; i++){
            JSONObject ans = new JSONObject();
            ans.put("number",q.getAnswers()[i].getNum_a());
            ans.put("visibility",q.getAnswers()[i].isVisibility());
            JSONArray accepted = new JSONArray();
            accepted.add(0,q.getAnswers()[i].getAccepted()[0]);
            ans.put("answer", accepted);
            JSONObject hint = new JSONObject();
            hint.put("nature",q.getAnswers()[i].getHint()[0]);
            hint.put("is",q.getAnswers()[i].getHint()[1]);
            ans.put("hint", hint);
            ansArray.add(i,ans);
        }
        quest.put("answers",ansArray);
        jsonFile.put(num_q, quest);

        File folder = new File("src/data/json/");
        File[] files = folder.listFiles();
        for (File f : files){
            try (FileWriter writer = new FileWriter(f)) {
                writer.write(prettyprint(jsonFile.toJSONString()));
                writer.flush();
                writer.close();
            }
        }
    }



    public static void main(String[] args) throws IOException, ParseException {

        //System.out.println("panda !");

        JsonTools L = new JsonTools();
        Question[] q= L.loadQuestions();
        //System.out.println(q[0].getTheme());
        Question qAndA = L.loadAnswers(7);
        //System.out.println(qAndA.getAnswers()[0].getAccepted()[1]);
        //System.out.println(qAndA.getAnswers()[2].getAccepted()[0]);
        //System.out.println(qAndA.getAnswers()[2].isVisibility());
        //L.validAnswer(qAndA,1);
        //System.out.println(qAndA.getAnswers()[2].isVisibility());
        System.out.println(qAndA.checkAnswers("back"));
        L.json_create("chat");
        L.load_player("panda");
        //System.out.println(L.getPlayer();

    }


}

