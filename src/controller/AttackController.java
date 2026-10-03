package controller;
import javafx.application.Application;
import javafx.stage.Stage;
import main.DictionaryAttackApp;

public class AttackController extends Application{
    @Override 
    public void start(Stage args0){
        DictionaryAttackApp dic = new DictionaryAttackApp();
        dic.dashboard(AttackController.this);
    }
}
