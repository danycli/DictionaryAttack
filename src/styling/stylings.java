package styling;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class stylings {
    public static Button buttonStyling(String text, int x, int y){
        Button b = new Button(text);
        b.setTranslateX(x);
        b.setTranslateY(y);
        b.setBorder(Border.EMPTY);
        b.setFocusTraversable(false);
        b.setTextFill(Color.WHITE);
        b.setStyle("""
                -fx-background-color: #33363c;
                -fx-background-radius:20;
                """);

        b.setOnMouseExited((MouseEvent t) ->{
            b.setStyle("""
                -fx-background-color: #33363c;
                -fx-background-radius:20;
                """);
            b.setTextFill(Color.WHITE);
        });

        b.setOnMouseEntered((MouseEvent t) -> {
            b.setStyle("""
                    -fx-background-color: #4f5258;
                    -fx-background-radius:20;
                    """);
            b.setTextFill(Color.BLACK);
        });

        return b;
    }

    public static Button primaryButton(String text, int x, int y, int width, int height) {
        Button b = new Button(text);
        b.setTranslateX(x);
        b.setTranslateY(y);
        b.setPrefSize(width, height);
        b.setTextFill(Color.WHITE);
        b.setFocusTraversable(false);
        b.setStyle("""
            -fx-background-color: #34373e; -fx-background-radius: 8; -fx-font-weight: bold; -fx-font-size: 14px; -fx-cursor: hand;
            """);

        b.setOnMouseEntered(e -> 
            b.setStyle("""
                -fx-background-color: #4f5258; -fx-background-radius: 8; -fx-font-weight: bold; -fx-font-size: 14px; -fx-cursor: hand;
                """));

        b.setOnMouseExited(e -> 
            b.setStyle("""
            -fx-background-color: #34373e; -fx-background-radius: 8; -fx-font-weight: bold; -fx-font-size: 14px; -fx-cursor: hand;
            """));
        return b;
    }

    public static Button sidebarButton(String text, int x, int y, boolean active) {
        Button b = new Button(text);
        b.setTranslateX(x);
        b.setTranslateY(y);
        b.setPrefSize(180, 40);
        b.setAlignment(Pos.CENTER_LEFT);
        b.setFocusTraversable(false);
        String baseStyle = """
        -fx-padding: 0 0 0 15; -fx-background-radius: 8; -fx-font-size: 14px; -fx-font-weight: bold; -fx-cursor: hand; 
        """;

        if (active) {
            b.setStyle(baseStyle + "-fx-background-color: #e3dec9; -fx-text-fill: #1a1a1a;");
        } else {
            b.setStyle(baseStyle + "-fx-background-color: transparent; -fx-text-fill: #555555;");
            b.setOnMouseEntered(e -> 
                b.setStyle(baseStyle + "-fx-background-color: #ebeae4; -fx-text-fill: #1a1a1a;"));
            b.setOnMouseExited(e -> 
                b.setStyle(baseStyle + "-fx-background-color: transparent; -fx-text-fill: #555555;"));
        }
        return b;
    }

    public static Button secondaryButton(String text, int x, int y, int width, int height) {
        Button b = new Button(text);
        b.setTranslateX(x);
        b.setTranslateY(y);
        b.setPrefSize(width, height);
        b.setFocusTraversable(false);
        b.setStyle("""
            -fx-background-color: transparent; -fx-border-color: #d1d1d1; -fx-border-radius: 6; -fx-cursor: hand; -fx-font-size: 12px; -fx-text-fill: #333333;
            """);

        b.setOnMouseEntered(e -> 
            b.setStyle("""
                -fx-background-color: #f0f0f0; -fx-border-color: #d1d1d1; -fx-border-radius: 6; -fx-cursor: hand; -fx-font-size: 12px; -fx-text-fill: #333333;
            """));
            
        b.setOnMouseExited(e -> 
            b.setStyle("""
                -fx-background-color: transparent; -fx-border-color: #d1d1d1; -fx-border-radius: 6; -fx-cursor: hand; -fx-font-size: 12px; -fx-text-fill: #333333;
            """));
        return b;
    }

    public static TextField textField(String prompt, String text, int x, int y, int width) {
        TextField tf = new TextField(text);
        tf.setPromptText(prompt);
        tf.setTranslateX(x);
        tf.setTranslateY(y);
        tf.setPrefSize(width, 35);
        tf.setStyle("""
            -fx-background-color: transparent; -fx-border-color: #d1d1d1; -fx-border-radius: 6; -fx-padding: 5 10; -fx-font-size: 13px; -fx-text-fill: #333333;
            """);
        return tf;
    }

    public static Pane cardPane(int x, int y, int width, int height) {
        Pane pane = new Pane();
        pane.setTranslateX(x);
        pane.setTranslateY(y);
        pane.setPrefSize(width, height);
        pane.setStyle("""
            -fx-background-color: #ffffff; -fx-background-radius: 12;
        """);
        return pane;
    }

    public static Pane innerCard(int x, int y, int width, int height) {
        Pane pane = new Pane();
        pane.setTranslateX(x);
        pane.setTranslateY(y);
        pane.setPrefSize(width, height);
        pane.setStyle("""
            -fx-background-color: #f7f6f2; -fx-background-radius: 8;
        """);
        return pane;
    }

    public static Label label(String text, int x, int y, int size, String color, boolean bold) {
        Label lbl = new Label(text);
        lbl.setTranslateX(x);
        lbl.setTranslateY(y);
        lbl.setStyle("-fx-text-fill: " + color + "; -fx-font-size: " + size + "px;" + (bold ? " -fx-font-weight: bold;" : ""));
        return lbl;
    }

    public static TextArea logArea(String text, int x, int y, int width, int height) {
        TextArea ta = new TextArea(text);
        ta.setTranslateX(x);
        ta.setTranslateY(y);
        ta.setPrefSize(width, height);
        ta.setEditable(false);
        ta.setStyle("""
            -fx-control-inner-background: #2b2e33; -fx-text-fill: #a9b7c6; -fx-font-family: 'Consolas'; -fx-font-size: 12px; -fx-background-radius: 8; -fx-border-radius: 8;
            """);
        return ta;
    }
    //For progress bar
    public static ProgressBar progressBar(int x, int y, int width, int height) {

    ProgressBar p = new ProgressBar(0);

    p.setTranslateX(x);
    p.setTranslateY(y);
    p.setPrefSize(width, height);
    p.setStyle("""
        -fx-accent: #1a1a1a;
        -fx-control-inner-background: #e6e6e6;
        -fx-background-radius: 8;
        """);

    return p;
}
}
