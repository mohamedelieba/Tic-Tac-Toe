
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class PlayerRecords extends Pane {
    
    protected final Button backBtn;
    protected final TableView<Player> tableView;
    protected final TableColumn name;
    protected final TableColumn win;
    protected final TableColumn lose;
    protected final TableColumn draw;
    ArrayList<Integer> recordedMoves;
    DataBase database = new DataBase();
    Timer timer;

    public PlayerRecords() {

        /**
         * *******************************************************
         */
        backBtn = new Button("Back");

        tableView = new TableView();
        win = new TableColumn();
        lose = new TableColumn();
        draw = new TableColumn();
        name = new TableColumn();
        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);
        tableView.getStyleClass().add("table-view");
        tableView.setLayoutX(0.0);
        tableView.setLayoutY(100.0);
        tableView.setPrefHeight(500.0);
        tableView.setPrefWidth(850.0);
        name.setText("Name");
        name.setPrefWidth(300.0);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        win.setPrefWidth(150.0);
        backBtn.getStyleClass().add("button1");
        backBtn.setTranslateX(350);
        backBtn.setTranslateY(607);

        backBtn.setPrefSize(90, 90);

        win.setText("Win");
        win.setCellValueFactory(new PropertyValueFactory<>("win"));
        lose.setPrefWidth(150.0);
        lose.setText("Lose");
        lose.setCellValueFactory(new PropertyValueFactory<>("lose"));
        draw.setPrefWidth(150.0);
        draw.setText("Draw");
        draw.setCellValueFactory(new PropertyValueFactory<>("draw"));
        tableView.getColumns().add(name);
        tableView.getColumns().add(win);
        tableView.getColumns().add(lose);
        tableView.getColumns().add(draw);
        getChildren().add(backBtn);
        getChildren().add(tableView);

        backBtn.setOnAction((Action) -> {

            AppManager.viewPane(AppManager.startPane);
        });

        try {

            tableView.setItems(database.retreivePlayer());
        } catch (SQLException ex) {
            Logger.getLogger(Records.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
