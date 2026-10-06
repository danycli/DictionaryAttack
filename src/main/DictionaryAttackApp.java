package main;

import controller.AttackController;
import java.io.File;
import styling.stylings;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DictionaryAttackApp{
    private TextField passField = null;
    private TextArea logArea = null;
    private Label pctLbl = null;
    private Label candVal = null;
    private Label c1Val = null;
    private Label c2Val = null;
    private Label c4Val = null;
    private Label c3Val = null;
    private ProgressBar progressBar = null;
    private TextField wlField = null;
    private Label wordsCount = null;
    private Label sizeCount = null;
    private Label fmtCount = null;
    private boolean termination = false;
    private Stage stage;
    private Pane root;
    private Scene scene;
    private Button stopAttack;
    private Button startAttackBtn;
    private Button resumeAttack;
    private Button terminateAttack;
    //dashboard
    public void dashboard(AttackController attack){
        stage = new Stage();
        root = new Pane();
        scene = new Scene(root, 1100, 750);
        
        // Background color of the entire app
        root.setStyle("-fx-background-color: #f5f4f1;");
        
        //SIDEBAR 
        Label titleLabel = stylings.label("🔒 Dictionary Attack Tool", 20, 20, 16, "#1a1a1a", true);
        Button attackNav = stylings.sidebarButton("🏠 Attack", 15, 60, true);
        
        root.getChildren().addAll(titleLabel, attackNav);
        
        // RGET LOGIN PANE
        Pane targetPane = stylings.cardPane(220, 20, 480, 200);
        Label targetTitle = stylings.label("🎯 Target Login", 20, 20, 16, "#1a1a1a", true);
        Label targetSub = stylings.label("Configure the target login endpoint and credentials.", 20, 45, 12, "#7a7a7a", false);
        
        Label urlLbl = stylings.label("Login URL", 20, 75, 12, "#1a1a1a", false);
        //https://slnsnexsphdsnknskjyn.supabase.co/auth/v1/token?grant_type=password
        TextField urlField = stylings.textField("http://127.0.0.1:3000/login", "http://127.0.0.1:3000/login", 20, 95, 440);
        
        Label userLbl = stylings.label("Username", 20, 140, 12, "#1a1a1a", false);
        TextField userField = stylings.textField("admin", "admin", 20, 160, 210);
        
        Label passLbl = stylings.label("Password", 250, 140, 12, "#1a1a1a", false);
        passField = stylings.textField("Will be guessed", "", 250, 160, 210);
        passField.setPromptText("Will be guessed");
        passField.setEditable(false);
        
        targetPane.getChildren().addAll(targetTitle, targetSub, urlLbl, urlField, userLbl, userField, passLbl, passField);
        
        //WORDLIST PANE 
        Pane wordlistPane = stylings.cardPane(220, 240, 480, 250);
        Label wlTitle = stylings.label("📄 Wordlist", 20, 20, 16, "#1a1a1a", true);
        Label wlFileLbl = stylings.label("Wordlist File", 20, 55, 12, "#1a1a1a", false);
        wlField = stylings.textField("C:\\VS Code\\DictionaryAttack\\wordlists\\words.txt", "C:\\VS Code\\DictionaryAttack\\wordlists\\words.txt", 20, 75, 340);
        Button browseBtn = stylings.secondaryButton("📁 Browse", 370, 75, 90, 35);
        
        // Stats area
        Pane statsBox = stylings.innerCard(20, 130, 440, 50);
        Label statIcon = stylings.label("📄", 15, 15, 18, "#1a1a1a", false);
        wordsCount = stylings.label("----", 60, 10, 14, "#1a1a1a", true);
        Label wordsLbl = stylings.label("Total Words", 60, 27, 11, "#7a7a7a", false);
        
        Rectangle sep1 = new Rectangle(1, 30, Color.web("#d1d1d1"));
        sep1.setTranslateX(160); sep1.setTranslateY(10);
        
        sizeCount = stylings.label("---", 180, 10, 14, "#1a1a1a", true);
        Label sizeLbl = stylings.label("File Size", 180, 27, 11, "#7a7a7a", false);
        
        Rectangle sep2 = new Rectangle(1, 30, Color.web("#d1d1d1"));
        sep2.setTranslateX(280); sep2.setTranslateY(10);
        
        fmtCount = stylings.label("---", 300, 10, 14, "#1a1a1a", true);
        Label fmtLbl = stylings.label("Format", 300, 27, 11, "#7a7a7a", false);

        statsBox.getChildren().addAll(statIcon, wordsCount, wordsLbl, sep1, sizeCount, sizeLbl, sep2, fmtCount, fmtLbl);
        
        stopAttack = stylings.primaryButton("❚❚ Pause", 20, 195, 140, 40);
        startAttackBtn = stylings.primaryButton("▶ Start Attack", 166, 195, 140, 40);
        resumeAttack = stylings.primaryButton("⏹ Resume", 312, 195, 140, 40);
        wordlistPane.getChildren().addAll(wlTitle, wlFileLbl, wlField, browseBtn, statsBox, startAttackBtn, stopAttack, resumeAttack);
        
        stopAttack.setDisable(true);
        resumeAttack.setDisable(true);

        //ATTACK LOG PANE
        Pane logPane = stylings.cardPane(220, 510, 480, 220);
        Label logTitle = stylings.label("⌨ Attack Log", 20, 20, 16, "#1a1a1a", true);
        Button clearBtn = stylings.secondaryButton("🗑 Clear", 390, 15, 70, 30);
        logArea = stylings.logArea("Attack not started yet", 20, 55, 440, 145);
        
        logPane.getChildren().addAll(logTitle, clearBtn, logArea);
        
        //ATTACK PROGRESS PANE
        Pane progressPane = stylings.cardPane(720, 20, 360, 370);
        Label progTitle = stylings.label("📊 Attack Progress", 20, 20, 16, "#1a1a1a", true);
        
        pctLbl = stylings.label("--%", 20, 65, 24, "#1a1a1a", true);
        
        progressBar = stylings.progressBar(20, 105, 300, 15);
        progressBar.setPrefWidth(350);
        progressBar.setPrefHeight(15);
        
        Pane candidateBox = stylings.innerCard(20, 135, 320, 60);
        Label candIcon = stylings.label("📄", 15, 20, 16, "#1a1a1a", false);
        Label candLbl = stylings.label("Current Candidate", 45, 15, 11, "#1a1a1a", false);
        candVal = stylings.label("---", 45, 30, 14, "#1a1a1a", true);
        candidateBox.getChildren().addAll(candIcon, candLbl, candVal);
        
        Button c1 = stylings.secondaryButton("", 20, 210, 70, 80); c1.setStyle(c1.getStyle() + "-fx-background-color: transparent; -fx-border-color: #f0f0f0;");
        Label c1Icon = stylings.label("#", 50, 220, 16, "#1a1a1a", true);
        c1Val = stylings.label("0", 42, 245, 14, "#1a1a1a", true);
        Label c1Lbl = stylings.label("Attempts", 30, 265, 11, "#7a7a7a", false);
        
        Button c2 = stylings.secondaryButton("", 100, 210, 80, 80); c2.setStyle(c2.getStyle() + "-fx-background-color: transparent; -fx-border-color: #f0f0f0;");
        Label c2Icon = stylings.label("🕒", 130, 220, 16, "#1a1a1a", false);
        c2Val = stylings.label("00:00:00", 115, 245, 14, "#1a1a1a", true);
        Label c2Lbl = stylings.label("Elapsed Time", 108, 265, 11, "#7a7a7a", false);
        
        Button c3 = stylings.secondaryButton("", 190, 210, 85, 80); c3.setStyle(c3.getStyle() + "-fx-background-color: transparent; -fx-border-color: #f0f0f0;");
        Label c3Icon = stylings.label("🚀", 225, 220, 16, "#1a1a1a", false);
        c3Val = stylings.label("---", 220, 245, 14, "#1a1a1a", true);
        Label c3Lbl = stylings.label("Attempts/ms", 198, 265, 11, "#7a7a7a", false);
        
        Button c4 = stylings.secondaryButton("", 285, 210, 75, 80); c4.setStyle(c4.getStyle() + "-fx-background-color: transparent; -fx-border-color: #f0f0f0;");
        Label c4Icon = stylings.label("📄", 315, 220, 16, "#1a1a1a", false);
        c4Val = stylings.label("---", 310, 245, 14, "#1a1a1a", true);
        Label c4Lbl = stylings.label("Current Line", 290, 265, 11, "#7a7a7a", false);
        
        terminateAttack = stylings.primaryButton("✗ Terminate", 110, 310, 140, 40); 
        terminateAttack.setDisable(true);

        progressPane.getChildren().addAll(progTitle, pctLbl, progressBar, candidateBox,terminateAttack,
                c1, c1Icon, c1Val, c1Lbl,
                c2, c2Icon, c2Val, c2Lbl,
                c3, c3Icon, c3Val, c3Lbl,
                c4, c4Icon, c4Val, c4Lbl);
                

        // Add panes to root
        root.getChildren().addAll(targetPane, wordlistPane, logPane, progressPane);
        
        // Window Control (Close Button since undecorated)
        Button quit = stylings.buttonStyling("X", 1060, 10);
        quit.setPrefSize(30, 30);
        quit.setStyle("-fx-background-color: transparent; -fx-text-fill: #1a1a1a; -fx-font-weight: bold; -fx-cursor: hand;");
        quit.setOnMouseEntered(e -> quit.setStyle("-fx-background-color: #ff4c4c; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 15;"));
        quit.setOnMouseExited(e -> quit.setStyle("-fx-background-color: transparent; -fx-text-fill: #1a1a1a; -fx-font-weight: bold; -fx-cursor: hand;"));
        root.getChildren().add(quit);

        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("🔒Dictionary Attack");
        stage.show();

        //Functionality for buttons
        quit.setOnAction( e ->{
            stage.close();
        });
        
        // Window Dragging functionality
        final double[] xOffset = new double[1];
        final double[] yOffset = new double[1];
        root.setOnMousePressed(event -> {
            xOffset[0] = event.getSceneX();
            yOffset[0] = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset[0]);
            stage.setY(event.getScreenY() - yOffset[0]);
        });

        //Storing the passwords in an Arraylistfor UI info
        updateUI();

        //Buttons Actions
        clearBtn.setOnAction(e ->{
            logArea.clear();
        });

        startAttackBtn.setOnAction(e -> {
            if(termination){
                attack.setobt(1);
                updateAttackUI("---", "00:00:00", "--", 0, "---");
            }
            termination = false;
            stopAttack.setDisable(false);
            startAttackBtn.setDisable(true);
            terminateAttack.setDisable(false);
            updateUI();
            long startTime = System.currentTimeMillis();
            logArea.clear();
            attack.start(wlField.getText(), userField.getText(), urlField.getText(), this, startTime, termination);
        });

        stopAttack.setOnAction(e ->{
            attack.pauseAttack();
            resumeAttack.setDisable(false);
            stopAttack.setDisable(true);
            startAttackBtn.setDisable(true);
        });

        resumeAttack.setOnAction(e ->{
            attack.resumeAttack();
            stopAttack.setDisable(false);
            startAttackBtn.setDisable(true);
            resumeAttack.setDisable(true);
        });

        terminateAttack.setOnAction(e ->{
            stopAttack.setDisable(true);
            resumeAttack.setDisable(true);
            startAttackBtn.setDisable(false);
            attack.resumeAttack();
            setTerm(true);
        });
    }
    //Now as we use Task to make the threads separated so we have to modify the javaFX components using runlater

    public void updateAttackUI(
        String attempt,
        String elapsed,
        String password,
        double percentage, String currentL) {

    Platform.runLater(() -> {

        c1Val.setText(attempt);
        c2Val.setText(elapsed);
        candVal.setText(password);
        pctLbl.setText(percentage+"%");
        progressBar.setProgress(percentage / 100.0);
        c4Val.setText(currentL);
    });
}

    //setter for attempts per second
    public void setc3Val(String text){
        Platform.runLater(() ->{
            c3Val.setText(text);
        });
    }
    //setter for password field
    public void setpassField(String pass){
        Platform.runLater(() ->{
            passField.setText(pass);
        });
    }
    //appending log area
    public void appendLogArea(String text){
        Platform.runLater(() ->{
            logArea.appendText(text);
        });
    }
    //setter for LogArea to set it after thousand entries
    public void setLogArea(String txt){
        Platform.runLater(() ->{
            logArea.setText(txt);
        });
    }
    //getter for termination boolean
    public boolean getTerm(){
        return this.termination;
    }
    //setter for termination boolean
    public void setTerm(boolean b){
        Platform.runLater(() ->{
            termination = b;
        });
    }

    //Updating the UI
    public void updateUI(){
        if(wlField.getText() != null){
            AttackController pass = new AttackController();
            // ArrayList<String> passwords = pass.getPasswords(wlField.getText());
            int count = pass.getPasswords(wlField.getText());
            //setting word count
            if(count >= 1000000){
                double c = ((double)count)/1000000.0;
                c = Math.round(c * 100.0)/100.0;
                wordsCount.setText(c+"M");
            }else if(count >= 1000){
                double c = ((double)count)/1000.0;
                c = Math.round(c * 100.0)/100.0;
                wordsCount.setText(c+"K");
            }else{
                wordsCount.setText(""+count);
            }

            //setting file size
            File file = new File(wlField.getText());
            double size = file.length();
            size = Math.round(size * 1000.0)/1000.0;
            //Converting in to KB from bytes
            if(size >= 1048576.0){
                size /= 1048576.0;
                size = Math.round(size * 1000.0)/1000.0;
                sizeCount.setText(""+size+" MB");
                
            }else if(size >= 1024.0){
                size /= 1024.0;
                size = Math.round(size * 1000.0)/1000.0;
                sizeCount.setText(""+size+" KB");
            }else{
                sizeCount.setText(""+size+" bytes");
            }

            //Getting format
            String[] sp = wlField.getText().split("");
            String format = null;
            for(String n : sp){
                if (format != null || n.equals(".")) {
                    if (format == null) {
                        format = "";
                    }
                    format += n;
                }
            }
            fmtCount.setText(format);
        }
    }
    //setter for disabling/enabling the buttons
    public void setButtons(boolean term, boolean resume, boolean start, boolean pause){
        terminateAttack.setDisable(term);
        resumeAttack.setDisable(resume);
        startAttackBtn.setDisable(start);
        stopAttack.setDisable(pause);
    }
}
