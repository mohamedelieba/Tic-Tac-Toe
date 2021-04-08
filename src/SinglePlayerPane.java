
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class SinglePlayerPane extends Pane {

    Rectangle rectangle0 = new Rectangle();
    Rectangle rectangle1 = new Rectangle();
    DataBase database = new DataBase();
    Label playerNameLabel = new Label("user name");
    TextField userName = new TextField();
    Button start = new Button("Start");
    Button back = new Button("Back");
    Label password = new Label("password ");
    PasswordField passwordField = new PasswordField();

    public SinglePlayerPane() {

        start.getStyleClass().add("button1");
        back.getStyleClass().add("button1");
        playerNameLabel.getStyleClass().add("label");
        password.getStyleClass().add("label");
        userName.getStyleClass().add("field");
        passwordField.getStyleClass().add("field");

        playerNameLabel.setPrefSize(200, 40);
        password.setPrefSize(200, 40);
        userName.setPrefSize(170, 40);
        passwordField.setPrefSize(170, 40);

        start.setPrefSize(125, 125);

        start.setLayoutX(500.0);
        start.setLayoutY(400.0);
        start.setMnemonicParsing(false);
        start.setText("start");

        back.setPrefSize(125, 125);

        back.setLayoutX(50.0);
        back.setLayoutY(400.0);
        back.setMnemonicParsing(false);
        back.setText("back");

        playerNameLabel.setTranslateX(10);
        playerNameLabel.setTranslateY(70);
        userName.setTranslateX(265);
        userName.setTranslateY(80);

        password.setTranslateX(10);
        password.setTranslateY(230);
        passwordField.setTranslateX(265);
        passwordField.setTranslateY(240);

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

        getChildren().addAll(rectangle0, rectangle1, playerNameLabel, userName, start, back, password, passwordField);
        start.setOnAction((Action) -> {
            try {
                AppManager.gamePane.playerName.setText(userName.getText());
                database.login(userName, passwordField);
                userName.setText("");
                passwordField.setText("");

            } catch (SQLException ex) {
                Logger.getLogger(SinglePlayerPane.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        back.setOnAction((Action) -> {
            AppManager.viewPane(AppManager.startPane);
            userName.setText("");
            passwordField.setText("");
        });

    }
}
