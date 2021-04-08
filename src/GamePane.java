import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.util.Duration;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class GamePane extends Pane {

    /**
     * *******************************************
     */

    
    ArrayList<Integer> moves = new ArrayList<>();
    ArrayList<Integer> recordesMoves = new ArrayList<>();
    ToolBar buttonstoolBar = new ToolBar();
    Button backBtn = new Button("back");
    ToggleButton recordBtn = new ToggleButton("Record");
    Button replyBtn = new Button("Replay");
    ToolBar scoreToolBar = new ToolBar();
    Label playerName = new Label();
    Label playerScore = new Label("0");
    Label computerPlayer = new Label("Computer");
    Label computerScore = new Label("0");
    /**
     * ******************************************
     */
    static Button[] boardButtons;
    boolean isGameEnded = false;
    boolean isSingleModeOn = true;
    DataBase database = new DataBase();
    SinglePlayerPane singlePane = new SinglePlayerPane();
    AudioClip audioPlayer;
    PauseTransition pause;
    PauseTransition recordPlay;
    String winnerName;
    Timer timer = new Timer();
    Rectangle rectangle0 = new Rectangle();
    Rectangle rectangle1 = new Rectangle();

    public GamePane() {

        audioPlayer = new AudioClip(getClass().getResource("game.mp3").toExternalForm());
        boardButtons = new Button[9];
        for (int i = 0; i < 9; i++) {

            boardButtons[i] = new Button();
            boardButtons[i].setLayoutY(i / 3 * 180);
            boardButtons[i].setLayoutX(i % 3 * 220);
            boardButtons[i].setMinSize(180, 150);

            boardButtons[i].addEventHandler(ActionEvent.ACTION, e -> {
                showSymbol(e);
                System.out.println(moves);
            });

            
            boardButtons[i].getStyleClass().add("buttonsStyle");
        }
        
        backBtn.getStyleClass().add("button1");
        recordBtn.getStyleClass().add("button1");
        replyBtn.getStyleClass().add("button1");
       
        playerName.getStyleClass().add("label1");
       playerScore.getStyleClass().add("label1");
        computerPlayer.getStyleClass().add("label1");
        computerScore.getStyleClass().add("label1");
        
         playerName.setTranslateX(665);
         playerName.setTranslateY(50);
         playerScore.setTranslateX(695);
        playerScore.setTranslateY(140);
        computerPlayer.setTranslateX(650);
        computerPlayer.setTranslateY(210);
        computerScore.setTranslateX(695);
        computerScore.setTranslateY(280);
        
        
        
        replyBtn.setTranslateX(15);
        replyBtn.setTranslateY(570);
       
        recordBtn.setTranslateX(285);
        recordBtn.setTranslateY(570);
       
        backBtn.setTranslateX(555);
       backBtn.setTranslateY(570);
        
        replyBtn.setPrefSize(125,125);
         recordBtn.setPrefSize(125, 125);
         backBtn.setPrefSize(125,125);
  
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
        
        getChildren().addAll(rectangle0,rectangle1,replyBtn,recordBtn, backBtn,playerName,playerScore,computerPlayer,computerScore);
        getChildren().addAll(boardButtons);
               backBtn.setOnAction((Action) -> {
            playerScore.setText("0");
            computerScore.setText("0");
            AppManager.viewPane(AppManager.startPane);
        });
        replyBtn.setOnAction((Action) -> {
            for (int i = 0; i < 9; i++) {
                boardButtons[i].setText("");
                moves.clear();
            }

        });
    
        pause = new PauseTransition(Duration.millis(500));
        pause.setOnFinished(
                e -> {
                    try {
                        computerMove();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GamePane.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(GamePane.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
        
        
    
    }

    public void showSymbol(ActionEvent e) {
        Button btn = (Button) e.getSource();
        try {

            if (btn.getText().isEmpty()) {
                playSound();
                btn.setText("X");
                moves.add(find(boardButtons, btn));
                
                System.out.println(moves);
                checkWins();
            } else {
                throw new Exception("this square is full");
            }

            pause.play();
            checkWins();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * ********************** generate random move for computer player
     *
     *******************************************
     * @throws java.lang.InterruptedException
     * @throws java.sql.SQLException
     */
    @SuppressWarnings("empty-statement")
    public void computerMove() throws InterruptedException, SQLException {
        int rand = new Random().nextInt(9);
        Set<Integer> randNumbers = new HashSet<>();
        while (!boardButtons[rand].getText().isEmpty() && randNumbers.size() < 9) {
            randNumbers.add(rand);
            rand = new Random().nextInt(9);

        }

        if (randNumbers.size() < 9) {
            boardButtons[rand].setText("O");
            moves.add(rand);
            checkWins();

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
            database.addDraw(playerName);
            
        }

        if ("X".equals(winner)) {
            database.addWin(playerName);
            winnerName = playerName.getText();
  VideoHandler video = new VideoHandler();
          Stage st = new Stage();
          VideoHandler.setWinnerName(playerName.getText());
          
          try {
          video.start(st);
          } catch (Exception e) {
          e.printStackTrace();
          }
            isGameEnded = true;
            playerScore.setText(Integer.valueOf(playerScore.getText()) + 1 + "");
            //database.calcRating(playerName);
            database.retreiveRecord(9);

        } else if ("O".equals(winner)) {
            database.addLoss(playerName);
            winnerName = computerPlayer.getText();
      
            computerScore.setText(Integer.valueOf(computerScore.getText()) + 1 + "");
            isGameEnded = true;
        }
        if (isGameEnded) {
            for (Button boardButton : boardButtons) {
                boardButton.setText("");
            }
            if (recordBtn.isSelected()) {
                database.addGame(playerName, computerPlayer, winnerName);
                
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

    public void playSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        audioPlayer.play();

    }

    public int find(Button[] a, Button target) {
        for (int i = 0; i < a.length; i++) {
            if (target.equals(a[i])) {
                return i;
            }
        }

        return -1;
    }
                   
}
