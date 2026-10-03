package main;

import controller.AttackController;
import javafx.application.Application;
import javafx.stage.Stage;
import jdk.jfr.SettingControl;

public class DictionaryAttackApp{
    //dashboard
    public void dashboard(AttackController attack){
        Stage stage = new Stage();
        stage.setTitle("Dictionary Attack");
        stage.show();
    }
}
