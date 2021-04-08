
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Settings extends Pane {

    Label level = new Label("level");
    ComboBox<String> levels = new ComboBox<>();

    Button back = new Button("Back");

    public Settings() {

        back.setId("back");
        level.setId("label");

        level.setPrefSize(150, 30);
//        levels.setPrefSize(150, 30);
        levels.getItems().addAll("Beginner", "Intermediate", "Expert");
        back.setPrefSize(240, 40);
        level.setTranslateX(100);
        level.setTranslateY(130);
        levels.setTranslateX(340);
        levels.setTranslateY(130);
        back.setTranslateX(170);
        back.setTranslateY(310);
        getChildren().addAll(level, levels, back);
        back.setOnAction((Action) -> {
            AppManager.viewPane(AppManager.startPane);
        });
    }
}
