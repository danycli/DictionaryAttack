package controller;
import java.io.File;
import java.io.FileNotFoundException;
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
    public void start(String path){
        //Arraylist for storing passwords
        System.out.println("Path of the file");
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

        for(String pass : passwords){
            System.out.println(pass);
        }
    }
}
