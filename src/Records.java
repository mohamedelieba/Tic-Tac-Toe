
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Records extends Pane {

    int idx = 0;
    protected final Button backBtn;
    protected final Button rewatchBtn;
    protected final TableView<Game> tableView;
    protected final TableColumn ID;
    protected final TableColumn firstPlayer;
    protected final TableColumn secondPlayer;
    protected final TableColumn winnerPlayer;
    static ArrayList<Integer> recordedMoves;
    DataBase database = new DataBase();
    Timer timer;
    Thread thread;

    public Records() {
        timer = new Timer();
        backBtn = new Button("Back");
        rewatchBtn = new Button("rewatch");
        tableView = new TableView();
        firstPlayer = new TableColumn();
        secondPlayer = new TableColumn();
        winnerPlayer = new TableColumn();
        ID = new TableColumn();
        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);
        backBtn.getStyleClass().add("button1");
        rewatchBtn.getStyleClass().add("button1");

        rewatchBtn.setTranslateX(235);
        rewatchBtn.setTranslateY(607);

        backBtn.setTranslateX(555);
        backBtn.setTranslateY(607);

        backBtn.setPrefSize(90, 90);
        rewatchBtn.setPrefSize(90, 90);
        tableView.setLayoutX(0.0);
        tableView.setLayoutY(100.0);
        tableView.setPrefHeight(500.0);
        tableView.setPrefWidth(850.0);
        tableView.getStyleClass().add("table-view");
        ID.setText("ID");
        ID.setPrefWidth(100.0);
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstPlayer.setPrefWidth(200.0);

        firstPlayer.setText("First");
        firstPlayer.setCellValueFactory(new PropertyValueFactory<>("first"));
        secondPlayer.setPrefWidth(250.0);
        secondPlayer.setText("Second");
        secondPlayer.setCellValueFactory(new PropertyValueFactory<>("second"));
        winnerPlayer.setPrefWidth(250.0);
        winnerPlayer.setText("Winner");
        winnerPlayer.setCellValueFactory(new PropertyValueFactory<>("winner"));
        tableView.getColumns().add(ID);
        tableView.getColumns().add(firstPlayer);
        tableView.getColumns().add(secondPlayer);
        tableView.getColumns().add(winnerPlayer);
        getChildren().add(backBtn);
        getChildren().add(tableView);
        getChildren().add(rewatchBtn);

        backBtn.setOnAction((Action) -> {

            AppManager.viewPane(AppManager.startPane);

        });
        rewatchBtn.setOnAction(e -> {
            Game game = null;
            try {
                game = tableView.getSelectionModel().getSelectedItem();
                recordedMoves = database.retreiveRecord(game.getId());
                AppManager.viewPane(AppManager.playRecords);
                replay();
            } catch (Exception e1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("you may want to choose a record first!1");
                alert.showAndWait();
            }
        });

        try {

            tableView.setItems(database.retreiveGame());
        } catch (SQLException ex) {
            Logger.getLogger(Records.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

   

    public void replay() {
        System.out.println(recordedMoves);
Timeline tl=new Timeline();
  for(int i=0; i < recordedMoves.size(); i++)
     {
         final int k = i;
       
         KeyFrame keyFrame = new KeyFrame(Duration.seconds(k+2), new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event) {
                 try {
                     
                     playRecord();
                 } catch (InterruptedException ex) {
                     Logger.getLogger(Records.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 
                 System.out.println(recordedMoves.get(k));
             }
         });

 tl.getKeyFrames().add(keyFrame);
 }
  tl.play();
    }

    public void playRecord() throws InterruptedException {

        if (idx < recordedMoves.size()) {

            if (idx % 2 == 0) {

                PlayRecords.boardButtons[recordedMoves.get(idx)].setText("X");
                    
            } else {
                
                PlayRecords.boardButtons[recordedMoves.get(idx)].setText("O");

            }

            if (idx < recordedMoves.size()) {
                idx++;
                

            } 
        }
            else 
            {  
        idx = 0;        
             
            }
               

    }
}
