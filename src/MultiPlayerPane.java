import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class MultiPlayerPane extends Pane {

    GamePane gamePane = new GamePane();
    DataBase database = new DataBase();
    Label playerXLabel = new Label("user name");
    TextField playerName = new TextField();

    Button start = new Button("Start");
    Button back = new Button("Back");

    Label passwordx = new Label("password");
    PasswordField passwordFieldx = new PasswordField();

    Rectangle rectangle0 = new Rectangle();
    Rectangle rectangle1 = new Rectangle();

    public MultiPlayerPane() {

        start.getStyleClass().add("button1");
        back.getStyleClass().add("button1");

        playerXLabel.getStyleClass().add("label");

        playerName.getStyleClass().add("field");

        passwordx.getStyleClass().add("label");

        passwordFieldx.getStyleClass().add("field");

        passwordx.setPrefSize(200, 40);
        passwordFieldx.setPrefSize(160, 30);

        playerXLabel.setTranslateX(80);
        playerXLabel.setTranslateY(80);
        playerName.setTranslateX(400);
        playerName.setTranslateY(90);

        passwordx.setTranslateX(80);
        passwordx.setTranslateY(150);
        passwordFieldx.setTranslateX(400);
        passwordFieldx.setTranslateY(160);

        playerXLabel.setPrefSize(200, 40);
        playerName.setPrefSize(170, 40);

        passwordFieldx.setPrefSize(170, 40);
        start.setPrefSize(125, 125);
        back.setPrefSize(125, 125);

        start.setLayoutX(500.0);
        start.setLayoutY(400.0);
        start.setMnemonicParsing(false);
        start.setText("start");

        back.setLayoutX(50.0);
        back.setLayoutY(400.0);
        back.setMnemonicParsing(false);
        back.setText("back");

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

        getChildren().addAll(rectangle0, rectangle1, playerXLabel, playerName, start, back, passwordx, passwordFieldx);
        start.setOnAction((Action) -> {
            try {
                AppManager.multiplier.clearBoard();
                Client.ps.println("name" +"."+ playerName.getText());
                AppManager.isSingleModeOn = false;
                database.login(playerName, passwordFieldx);
                playerName.clear();

            } catch (SQLException ex) {
                Logger.getLogger(SinglePlayerPane.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        back.setOnAction((Action) -> {
            AppManager.viewPane(AppManager.startPane);
            playerName.clear();

        });

    }

    public static void setFirstPlayerName(String playerName) {
        String[] playerNameArr = playerName.split("\\.");
        AppManager.multiplier.firstPlayer.setText(playerNameArr[1]);
        
    }
    public static void setSecondPlayerName(String playerName) {
        String[] playerNameArr = playerName.split("\\.");
       // if (AppManager.multiplier.firstPlayer.equals(playerNameArr[1]))
        AppManager.multiplier.secondPlayer.setText(playerNameArr[1]);    
    }
}
