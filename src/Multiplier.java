
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
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
public class Multiplier extends Pane {

    Rectangle rectangle0 = new Rectangle();
    Rectangle rectangle1 = new Rectangle();
    ArrayList<Integer> moves;
    ArrayList<Integer> recordesMoves = new ArrayList<>();
    ToolBar buttonstoolBar = new ToolBar();
    Button backBtn = new Button("back");
    ToggleButton recordBtn = new ToggleButton("Record");
    Button replyBtn = new Button("Replay");
    ToolBar scoreToolBar = new ToolBar();
    Label firstPlayer = new Label();
    static Label firstPlayerScore = new Label("0");
    Label secondPlayer = new Label();
    static Label secondPlayerScore = new Label("0");
    String winnerName;
    static Button[] boardButtons;
    boolean isGameEnded = false;
    boolean isFirstPlayerTurn = true;
    DataBase database = new DataBase();

    SinglePlayerPane singlePane = new SinglePlayerPane();
    AudioClip audioPlayer;

    public Multiplier() {
        moves = new ArrayList<>();

        audioPlayer = new AudioClip(getClass().getResource("game.mp3").toExternalForm());
        boardButtons = new Button[9];
        for (int i = 0; i < 9; i++) {

            boardButtons[i] = new Button();
            boardButtons[i].setLayoutY(i / 3 * 180);
            boardButtons[i].setLayoutX(i % 3 * 220);
            boardButtons[i].setMinSize(180, 150);

            boardButtons[i].addEventHandler(ActionEvent.ACTION, e -> {
                try {
                    actionPerformed(e);
                } catch (SQLException ex) {
                    Logger.getLogger(Multiplier.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            boardButtons[i].getStyleClass().add("buttonsStyle");
        }

        backBtn.getStyleClass().add("button1");
        recordBtn.getStyleClass().add("button1");
        replyBtn.getStyleClass().add("button1");

        firstPlayer.getStyleClass().add("label1");
        firstPlayerScore.getStyleClass().add("label1");
        secondPlayer.getStyleClass().add("label1");
        secondPlayerScore.getStyleClass().add("label1");

        firstPlayer.setTranslateX(665);
        firstPlayer.setTranslateY(50);
        firstPlayerScore.setTranslateX(695);
        firstPlayerScore.setTranslateY(140);
        secondPlayer.setTranslateX(665);
        secondPlayer.setTranslateY(210);
        secondPlayerScore.setTranslateX(695);
        secondPlayerScore.setTranslateY(280);

        replyBtn.setTranslateX(15);
        replyBtn.setTranslateY(570);

        recordBtn.setTranslateX(285);
        recordBtn.setTranslateY(570);

        backBtn.setTranslateX(555);
        backBtn.setTranslateY(570);

        replyBtn.setPrefSize(125, 125);
        recordBtn.setPrefSize(125, 125);
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

        getChildren().addAll(rectangle0, rectangle1, replyBtn, recordBtn, backBtn, firstPlayer, firstPlayerScore, secondPlayer, secondPlayerScore);

        getChildren().addAll(boardButtons);

        backBtn.setOnAction((Action) -> {
            firstPlayerScore.setText("0");
            secondPlayerScore.setText("0");
            AppManager.viewPane(AppManager.startPane);
        });
        replyBtn.setOnAction((Action) -> {
            for (int i = 0; i < 9; i++) {
                boardButtons[i].setText("");
                moves.clear();
            }

        });
    }

    private void actionPerformed(ActionEvent e) throws SQLException {
        Button btn = (Button) e.getSource();

        if (isFirstPlayerTurn) {

            moves.add(AppManager.gamePane.find(boardButtons, btn));
            Client.ps.println(moves.get(moves.size() - 1) + "." + "X");

        } else {

            moves.add(AppManager.gamePane.find(boardButtons, btn));
            Client.ps.println(moves.get(moves.size() - 1) + "." + "O");

        }
    }

    public void checkWins() throws SQLException {
        String winner = null;
        if (boardButtons[0].getText().equals(boardButtons[1].getText()) && boardButtons[1].getText().equals(boardButtons[2].getText()) && boardButtons[0].getText().equals(boardButtons[2].getText())) {
            winner = boardButtons[0].getText();
        } else if (boardButtons[3].getText().equals(boardButtons[4].getText()) && boardButtons[4].getText().equals(boardButtons[5].getText()) && boardButtons[3].getText().equals(boardButtons[5].getText())) {
            winner = boardButtons[3].getText();

        } else if (boardButtons[6].getText().equals(boardButtons[7].getText()) && boardButtons[7].getText().equals(boardButtons[8].getText()) && boardButtons[6].getText().equals(boardButtons[8].getText())) {
            winner = boardButtons[6].getText();

        } else if (boardButtons[0].getText().equals(boardButtons[3].getText()) && boardButtons[0].getText().equals(boardButtons[6].getText()) && boardButtons[3].getText().equals(boardButtons[6].getText())) {
            winner = boardButtons[0].getText();

        } else if (boardButtons[1].getText().equals(boardButtons[4].getText()) && boardButtons[4].getText().equals(boardButtons[7].getText()) && boardButtons[1].getText().equals(boardButtons[7].getText())) {
            winner = boardButtons[1].getText();

        } else if (boardButtons[2].getText().equals(boardButtons[5].getText()) && boardButtons[5].getText().equals(boardButtons[8].getText()) && boardButtons[2].getText().equals(boardButtons[8].getText())) {
            winner = boardButtons[2].getText();

        } else if (boardButtons[0].getText().equals(boardButtons[4].getText()) && boardButtons[4].getText().equals(boardButtons[8].getText()) && boardButtons[0].getText().equals(boardButtons[8].getText())) {
            winner = boardButtons[0].getText();

        } else if (boardButtons[2].getText().equals(boardButtons[4].getText()) && boardButtons[4].getText().equals(boardButtons[6].getText()) && boardButtons[2].getText().equals(boardButtons[6].getText())) {
            winner = boardButtons[2].getText();

        } else if (boardIsFull()) {
            isGameEnded = true;
            database.addDraw(firstPlayer);
            database.addDraw(secondPlayer);
            clearBoard();
//            Client.ps.println("clearBoard");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("draw");
            alert.showAndWait();
        }

        if ("X".equals(winner)) {
            database.addWin(firstPlayer);
            database.addLoss(secondPlayer);
            winnerName = firstPlayer.getText();
//            Client.ps.println("score" + "." + "firstPlayer");
            clearBoard();
//            isFirstPlayerTurn = true;
            firstPlayerScore.setText(Integer.parseInt(firstPlayerScore.getText()) + 1 + "");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(firstPlayer.getText() + " win");
            alert.showAndWait();
            isGameEnded = true;

        } else if ("O".equals(winner)) {
            database.addWin(secondPlayer);
            database.addLoss(firstPlayer);
            winnerName = secondPlayer.getText();
            clearBoard();
//            isFirstPlayerTurn = true;
//            Client.ps.println("score" + "." + "secondPlayer");
            secondPlayerScore.setText(Integer.parseInt(secondPlayerScore.getText()) + 1 + "");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(secondPlayer.getText() + " Win");
            alert.showAndWait();
            isGameEnded = true;
        }
        if (isGameEnded) {

            if (recordBtn.isSelected()) {
                database.addGame(firstPlayer, secondPlayer, winnerName);

                for (int i = 0; i < moves.size(); i++) {
                    database.insertRecord(moves.get(i));
                }
            }
            moves.clear();
            isGameEnded = false;
        }
        
    }

    public boolean boardIsFull() {
        for (int i = 0; i < 9; i++) {
            if (boardButtons[i].getText().isEmpty()) {
                return false;
            }
        }
        isGameEnded = true;
        return isGameEnded;
    }
    
   void showOthersSymbols(String message) throws SQLException {

        String[] positionAndSymbol = message.split("\\.");
        audioPlayer.play();
        switch (positionAndSymbol[0]) {

            case "0":
                if (boardButtons[0].getText().isEmpty()) {
                    boardButtons[0].setText(positionAndSymbol[1]);

                }
                break;
            case "1":
                if (boardButtons[1].getText().isEmpty()) {
                    boardButtons[1].setText(positionAndSymbol[1]);

                }
                break;
            case "2":
                if (boardButtons[2].getText().isEmpty()) {
                    boardButtons[2].setText(positionAndSymbol[1]);
                }
                break;
            case "3":
                if (boardButtons[3].getText().isEmpty()) {
                    boardButtons[3].setText(positionAndSymbol[1]);
                }
                break;
            case "4":
                if (boardButtons[4].getText().isEmpty()) {
                    boardButtons[4].setText(positionAndSymbol[1]);
                }
                break;
            case "5":
                if (boardButtons[5].getText().isEmpty()) {
                    boardButtons[5].setText(positionAndSymbol[1]);
                }
                break;
            case "6":
                if (boardButtons[6].getText().isEmpty()) {
                    boardButtons[6].setText(positionAndSymbol[1]);
                }
                break;
            case "7":
                if (boardButtons[7].getText().isEmpty()) {
                    boardButtons[7].setText(positionAndSymbol[1]);
                }
                break;
            case "8":
                if (boardButtons[8].getText().isEmpty()) {
                    boardButtons[8].setText(positionAndSymbol[1]);
                }
                break;
        }
        checkWins();

    }

    void clearBoard() {
        for (Button btn : boardButtons) {
            btn.setText("");
        }

    }
}
