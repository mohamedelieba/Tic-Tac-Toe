
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Rectangle;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author elieba
 */
public class PlayRecords extends Pane {

    static Button[] boardButtons;
    boolean isGameEnded = false;
    boolean isFirstPlayerTurn = true;
    DataBase database = new DataBase();
    SinglePlayerPane singlePane = new SinglePlayerPane();
    AudioClip audioPlayer;
    Button backBtn;
    Rectangle rectangle0 = new Rectangle();
    Rectangle rectangle1 = new Rectangle();

    /**
     * *********************************
     */
    public PlayRecords() {

        backBtn = new Button("Back");
        backBtn.getStyleClass().add("button1");
        backBtn.setTranslateX(350);
        backBtn.setTranslateY(560);
        backBtn.setPrefSize(125, 125);
        rectangle0.setX(650.0f);
        rectangle0.setY(0.0f);
        rectangle0.setWidth(210.0f);
        rectangle0.setHeight(700.0f);

        rectangle1.setX(0.0f);
        rectangle1.setY(560.0f);
        rectangle1.setWidth(850.0f);
        rectangle1.setHeight(210.0f);

        rectangle0.getStyleClass().add("rect");
        rectangle1.getStyleClass().add("rect");

        getChildren().addAll(rectangle0, rectangle1);
        audioPlayer = new AudioClip(getClass().getResource("game.mp3").toExternalForm());
        boardButtons = new Button[9];
        for (int i = 0; i < 9; i++) {

            boardButtons[i] = new Button();
            boardButtons[i].setLayoutY(i / 3 * 180);
            boardButtons[i].setLayoutX(i % 3 * 220);
            boardButtons[i].setMinSize(180, 150);

            boardButtons[i].getStyleClass().add("buttonsStyle");
        }
        getChildren().addAll(boardButtons);
        getChildren().add(backBtn);

        
        backBtn.setOnAction(e -> {
            
                Records.recordedMoves.clear();
            clearBoard();
            AppManager.viewPane(AppManager.records);

        });
        
    }
    public void clearBoard() {
        for (int i = 0; i < PlayRecords.boardButtons.length; i++) {
            boardButtons[i].setText("");
        }
    }

}
