    
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
static public Client client;
    @Override
    public void start(Stage stage) {
        client = new Client();
        Pane root = new Pane();
        root.getStyleClass().addAll("pane");
        root.getChildren().add(AppManager.signupbase);
        root.getChildren().add(AppManager.playerRecords);
        root.getChildren().add(AppManager.startPane);
        root.getChildren().add(AppManager.singlePlayerPane);
        root.getChildren().add(AppManager.multiPlayerPane);
        root.getChildren().add(AppManager.settingsPane);
        root.getChildren().add(AppManager.gamePane);
        root.getChildren().add(AppManager.multiplier);
        root.getChildren().add(AppManager.records);
        root.getChildren().add(AppManager.playRecords);
        AppManager.viewPane(AppManager.startPane);
        Scene scene = new Scene(root, 850, 700);
        scene.getStylesheets().add("style.css");
        stage.setTitle("Tic Tac Toe");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
