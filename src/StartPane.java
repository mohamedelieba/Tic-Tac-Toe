
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class StartPane extends Pane {

    Rectangle rectangle0 = new Rectangle();
    Rectangle rectangle1 = new Rectangle();

    Button signUp = new Button("Sign Up");
    Button singlePlayer = new Button("Single");
    Button multiPlayer = new Button("Multi");

    Button about = new Button("About");
    Button exit = new Button("Exit");
    Button records = new Button("Records");
    Button playerRecords = new Button("Players");
    Alert alert = new Alert(AlertType.INFORMATION);
    Text text = new Text();
    Label playerNameLabell = new Label("Player Name");
    Text wel = new Text();
    Text to = new Text();
    Text tic = new Text();
    Text tac = new Text();
    Text toe = new Text();

    public StartPane() {

        wel.setText("welcome");
        to.setText("To");
        tic.setText("Tic");
        tac.setText("Tac");
        toe.setText("Toe");

        wel.setX(228);
        wel.setY(90);

        to.setX(310);
        to.setY(290);

        tic.setX(90);
        tic.setY(470);

        tac.setX(310);
        tac.setY(470);

        toe.setX(510);
        toe.setY(470);

        wel.getStyleClass().add("text1");
        to.getStyleClass().add("text1");
        tic.getStyleClass().add("text1");
        tac.getStyleClass().add("text1");
        toe.getStyleClass().add("text1");

        signUp.getStyleClass().add("button1");
        singlePlayer.getStyleClass().add("button1");
        multiPlayer.getStyleClass().add("button1");

        about.getStyleClass().add("button1");
        exit.getStyleClass().add("button1");
        records.getStyleClass().add("button1");
        playerRecords.getStyleClass().add("button1");

        singlePlayer.setPrefSize(125, 125);
        multiPlayer.setPrefSize(125, 125);

        about.setPrefSize(125, 125);
        exit.setPrefSize(125, 125);
        signUp.setPrefSize(125, 125);
        records.setPrefSize(125, 125);
        playerRecords.setPrefSize(125, 125);

        singlePlayer.setTranslateX(680);
        singlePlayer.setTranslateY(50);
        multiPlayer.setTranslateX(680);
        multiPlayer.setTranslateY(185);
        signUp.setTranslateX(680);
        signUp.setTranslateY(320);

        records.setTranslateX(15);
        records.setTranslateY(570);
        playerRecords.setTranslateX(150);
        playerRecords.setTranslateY(570);

        about.setTranslateX(285);
        about.setTranslateY(570);
        exit.setTranslateX(420);
        exit.setTranslateY(570);

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

        getChildren().addAll(rectangle0, rectangle1, signUp, singlePlayer, multiPlayer, about, exit, records, playerRecords, wel, to, tic, tac, toe);

        signUp.setOnAction((Action) -> {
            AppManager.viewPane(AppManager.signupbase);
        });

        singlePlayer.setOnAction((Action) -> {
            AppManager.viewPane(AppManager.singlePlayerPane);
        });
        multiPlayer.setOnAction((Action) -> {
            AppManager.viewPane(AppManager.multiPlayerPane);
        });
        playerRecords.setOnAction((Action) -> {
            AppManager.viewPane(AppManager.playerRecords);
        });
        about.setOnAction((Action) -> {
            String str
                    = "Prepared by Mohammed Eliba , Anton Elqes, Maha Shiha, Hager Meselhy, Aya Magdy\n\n"
                    + "If you have any comments, ideas.. just let us know\n\n";
            alert.setTitle("About Tic Tac Toe");
            alert.setHeaderText("About Tic Tac Toe");
            alert.setContentText(str);
            alert.showAndWait();
        });

        exit.setOnAction((Action) -> {
            System.exit(0);
        });
        records.setOnAction((Action) -> {
            AppManager.viewPane(AppManager.records);
        });

    }
}
