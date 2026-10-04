package main;

import controller.AttackController;
import styling.stylings;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DictionaryAttackApp{
    //dashboard
    public void dashboard(AttackController attack){
        Stage stage = new Stage();
        Pane root = new Pane();
        Scene scene = new Scene(root, 1100, 750);
        
        // Background color of the entire app
        root.setStyle("-fx-background-color: #f5f4f1;");
        
        //SIDEBAR 
        Label titleLabel = stylings.label("🔒 Dictionary Attack Tool", 20, 20, 16, "#1a1a1a", true);
        Button attackNav = stylings.sidebarButton("🏠 Attack", 15, 60, true);
        Button settingsNav = stylings.sidebarButton("⚙ Settings", 15, 110, false);
        Button aboutNav = stylings.sidebarButton("ℹ About", 15, 160, false);
        
        root.getChildren().addAll(titleLabel, attackNav, settingsNav, aboutNav);
        
        // RGET LOGIN PANE
        Pane targetPane = stylings.cardPane(220, 20, 480, 200);
        Label targetTitle = stylings.label("🎯 Target Login", 20, 20, 16, "#1a1a1a", true);
        Label targetSub = stylings.label("Configure the target login endpoint and credentials.", 20, 45, 12, "#7a7a7a", false);
        
        Label urlLbl = stylings.label("Login URL", 20, 75, 12, "#1a1a1a", false);
        TextField urlField = stylings.textField("http://localhost:8080/login", "http://localhost:8080/login", 20, 95, 440);
        
        Label userLbl = stylings.label("Username", 20, 140, 12, "#1a1a1a", false);
        TextField userField = stylings.textField("admin", "admin", 20, 160, 210);
        
        Label passLbl = stylings.label("Password (Leave empty)", 250, 140, 12, "#1a1a1a", false);
        TextField passField = stylings.textField("Not required (will be guessed)", "", 250, 160, 210);
        passField.setPromptText("Not required (will be guessed)");
        
        targetPane.getChildren().addAll(targetTitle, targetSub, urlLbl, urlField, userLbl, userField, passLbl, passField);
        
        //WORDLIST PANE 
        Pane wordlistPane = stylings.cardPane(220, 240, 480, 250);
        Label wlTitle = stylings.label("📄 Wordlist", 20, 20, 16, "#1a1a1a", true);
        Label wlFileLbl = stylings.label("Wordlist File", 20, 55, 12, "#1a1a1a", false);
        TextField wlField = stylings.textField("C:\\Wordlists\\rockyou-small.txt", "C:\\Wordlists\\rockyou-small.txt", 20, 75, 340);
        Button browseBtn = stylings.secondaryButton("📁 Browse", 370, 75, 90, 35);
        
        // Stats area
        Pane statsBox = stylings.innerCard(20, 130, 440, 50);
        Label statIcon = stylings.label("📄", 15, 15, 18, "#1a1a1a", false);
        Label wordsCount = stylings.label("----", 60, 10, 14, "#1a1a1a", true);
        Label wordsLbl = stylings.label("Total Words", 60, 27, 11, "#7a7a7a", false);
        
        Rectangle sep1 = new Rectangle(1, 30, Color.web("#d1d1d1"));
        sep1.setTranslateX(160); sep1.setTranslateY(10);
        
        Label sizeCount = stylings.label("---", 180, 10, 14, "#1a1a1a", true);
        Label sizeLbl = stylings.label("File Size", 180, 27, 11, "#7a7a7a", false);
        
        Rectangle sep2 = new Rectangle(1, 30, Color.web("#d1d1d1"));
        sep2.setTranslateX(280); sep2.setTranslateY(10);
        
        Label fmtCount = stylings.label("---", 300, 10, 14, "#1a1a1a", true);
        Label fmtLbl = stylings.label("Format", 300, 27, 11, "#7a7a7a", false);
        
        statsBox.getChildren().addAll(statIcon, wordsCount, wordsLbl, sep1, sizeCount, sizeLbl, sep2, fmtCount, fmtLbl);
        
        Button startAttackBtn = stylings.primaryButton("▶ Start Attack", 20, 195, 440, 40);
        //starting the attack
        
        wordlistPane.getChildren().addAll(wlTitle, wlFileLbl, wlField, browseBtn, statsBox, startAttackBtn);

        startAttackBtn.setOnAction(e -> {
            System.out.println("ActionFired");
            AttackController at = new AttackController();
            at.start(wlField.getText());
        });
        
        
        //ATTACK LOG PANE
        Pane logPane = stylings.cardPane(220, 510, 480, 220);
        Label logTitle = stylings.label("⌨ Attack Log", 20, 20, 16, "#1a1a1a", true);
        Button clearBtn = stylings.secondaryButton("🗑 Clear", 390, 15, 70, 30);
        TextArea logArea = stylings.logArea("Nothing Happened yet", 20, 55, 440, 145);
        
        logPane.getChildren().addAll(logTitle, clearBtn, logArea);
        
        //ATTACK PROGRESS PANE
        Pane progressPane = stylings.cardPane(720, 20, 360, 310);
        Label progTitle = stylings.label("📊 Attack Progress", 20, 20, 16, "#1a1a1a", true);
        Button readyBadge = stylings.secondaryButton("● Ready", 270, 15, 70, 25);
        readyBadge.setStyle(readyBadge.getStyle() + "-fx-background-color: #f0f0f0; -fx-border-width: 0;");
        
        Label pctLbl = stylings.label("--%", 20, 65, 24, "#1a1a1a", true);
        Label progressTxt = stylings.label("0 / ---", 280, 75, 12, "#7a7a7a", false);
        
        Rectangle progBg = new Rectangle(320, 12, Color.web("#e0e0e0"));
        progBg.setTranslateX(20); progBg.setTranslateY(105);
        progBg.setArcWidth(12); progBg.setArcHeight(12);
        
        Pane candidateBox = stylings.innerCard(20, 135, 320, 60);
        Label candIcon = stylings.label("📄", 15, 20, 16, "#1a1a1a", false);
        Label candLbl = stylings.label("Current Candidate", 45, 15, 11, "#1a1a1a", false);
        Label candVal = stylings.label("---", 45, 30, 14, "#1a1a1a", true);
        candidateBox.getChildren().addAll(candIcon, candLbl, candVal);
        
        Button c1 = stylings.secondaryButton("", 20, 210, 70, 80); c1.setStyle(c1.getStyle() + "-fx-background-color: transparent; -fx-border-color: #f0f0f0;");
        Label c1Icon = stylings.label("#", 50, 220, 16, "#1a1a1a", true);
        Label c1Val = stylings.label("0", 50, 245, 14, "#1a1a1a", true);
        Label c1Lbl = stylings.label("Attempts", 30, 265, 11, "#7a7a7a", false);
        
        Button c2 = stylings.secondaryButton("", 100, 210, 80, 80); c2.setStyle(c2.getStyle() + "-fx-background-color: transparent; -fx-border-color: #f0f0f0;");
        Label c2Icon = stylings.label("🕒", 130, 220, 16, "#1a1a1a", false);
        Label c2Val = stylings.label("00:00:00", 115, 245, 14, "#1a1a1a", true);
        Label c2Lbl = stylings.label("Elapsed Time", 108, 265, 11, "#7a7a7a", false);
        
        Button c3 = stylings.secondaryButton("", 190, 210, 85, 80); c3.setStyle(c3.getStyle() + "-fx-background-color: transparent; -fx-border-color: #f0f0f0;");
        Label c3Icon = stylings.label("🚀", 225, 220, 16, "#1a1a1a", false);
        Label c3Val = stylings.label("---", 228, 245, 14, "#1a1a1a", true);
        Label c3Lbl = stylings.label("Attempts/sec", 198, 265, 11, "#7a7a7a", false);
        
        Button c4 = stylings.secondaryButton("", 285, 210, 75, 80); c4.setStyle(c4.getStyle() + "-fx-background-color: transparent; -fx-border-color: #f0f0f0;");
        Label c4Icon = stylings.label("📄", 315, 220, 16, "#1a1a1a", false);
        Label c4Val = stylings.label("---", 318, 245, 14, "#1a1a1a", true);
        Label c4Lbl = stylings.label("Current Line", 290, 265, 11, "#7a7a7a", false);
        
        progressPane.getChildren().addAll(progTitle, readyBadge, pctLbl, progressTxt, progBg, candidateBox,
                c1, c1Icon, c1Val, c1Lbl,
                c2, c2Icon, c2Val, c2Lbl,
                c3, c3Icon, c3Val, c3Lbl,
                c4, c4Icon, c4Val, c4Lbl);
                
        //RESULT PANE
        Pane resultPane = stylings.cardPane(720, 350, 360, 380);
        Label resTitle = stylings.label("🏆 Result", 20, 20, 16, "#1a1a1a", true);
        
        Pane emptyBox = stylings.innerCard(20, 60, 320, 80);
        Label emptyHyphen = stylings.label("-", 175, 75, 18, "#1a1a1a", true);
        Label emptyTxt = stylings.label("Attack not started yet.", 110, 105, 12, "#7a7a7a", false);
        emptyBox.getChildren().addAll(emptyHyphen, emptyTxt);
        
        Label r1 = stylings.label("Found Password", 20, 170, 12, "#1a1a1a", false); Label r1v = stylings.label("-", 330, 170, 12, "#1a1a1a", true);
        Rectangle s1 = new Rectangle(320, 1, Color.web("#f0f0f0")); s1.setTranslateX(20); s1.setTranslateY(195);
        
        Label r2 = stylings.label("Line Number", 20, 210, 12, "#1a1a1a", false); Label r2v = stylings.label("-", 330, 210, 12, "#1a1a1a", true);
        Rectangle s2 = new Rectangle(320, 1, Color.web("#f0f0f0")); s2.setTranslateX(20); s2.setTranslateY(235);
        
        Label r3 = stylings.label("Total Attempts", 20, 250, 12, "#1a1a1a", false); Label r3v = stylings.label("-", 330, 250, 12, "#1a1a1a", true);
        Rectangle s3 = new Rectangle(320, 1, Color.web("#f0f0f0")); s3.setTranslateX(20); s3.setTranslateY(275);
        
        Label r4 = stylings.label("Time Taken", 20, 290, 12, "#1a1a1a", false); Label r4v = stylings.label("-", 330, 290, 12, "#1a1a1a", true);
        Rectangle s4 = new Rectangle(320, 1, Color.web("#f0f0f0")); s4.setTranslateX(20); s4.setTranslateY(315);
        
        Label r5 = stylings.label("Attempts / Second", 20, 330, 12, "#1a1a1a", false); Label r5v = stylings.label("-", 330, 330, 12, "#1a1a1a", true);
        
        resultPane.getChildren().addAll(resTitle, emptyBox, 
                r1, r1v, s1,
                r2, r2v, s2,
                r3, r3v, s3,
                r4, r4v, s4,
                r5, r5v);

        // Add panes to root
        root.getChildren().addAll(targetPane, wordlistPane, logPane, progressPane, resultPane);
        
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
    }
}
