
import javafx.scene.text.*;
import java.lang.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SignUpBase extends Pane {

    protected final Label userName;
    protected final Label password;
    protected final PasswordField passwordField;
    // protected final Label acc;
    protected final TextField textField;

    protected final Button playBtn;
    protected final Button signupBtn;
    DataBase database;
    Rectangle rectangle0 = new Rectangle();
    Rectangle rectangle1 = new Rectangle();

    public SignUpBase() {

//       
        userName = new Label();
        password = new Label();
        passwordField = new PasswordField();
        textField = new TextField();

        playBtn = new Button();
        signupBtn = new Button();
        database = new DataBase();

        playBtn.getStyleClass().add("button1");
        signupBtn.getStyleClass().add("button1");

        userName.getStyleClass().add("label");
        password.getStyleClass().add("label");
        textField.getStyleClass().add("field");
        passwordField.getStyleClass().add("field");

        signupBtn.setPrefSize(125, 125);
        playBtn.setPrefSize(125, 125);

        userName.setPrefSize(200, 40);
        textField.setPrefSize(170, 40);
        password.setPrefSize(200, 40);
        passwordField.setPrefSize(170, 40);

        userName.setLayoutX(130.0);
        userName.setLayoutY(100.0);
        userName.setText("user name");

        password.setLayoutX(130.0);
        password.setLayoutY(230.0);
        password.setText("password");

        textField.setLayoutX(400.0);
        textField.setLayoutY(110.0);

        passwordField.setLayoutX(400.0);
        passwordField.setLayoutY(240.0);

        playBtn.setLayoutX(50.0);
        playBtn.setLayoutY(400.0);
        playBtn.setMnemonicParsing(false);
        playBtn.setText("Play");


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

        /**
         * ****************************************************************************
         */
        playBtn.setOnAction((Action) -> {
            AppManager.viewPane(AppManager.startPane);
        });
        /**
         * **********************************************************************************
         */

        signupBtn.setLayoutX(500.0);
        signupBtn.setLayoutY(400.0);
        signupBtn.setMnemonicParsing(false);
        signupBtn.setText("Sign Up");
        /**
         * ****************************************************************************
         */
        signupBtn.setOnAction((Action) -> {
            database.insertPlayer(textField, passwordField);
            textField.clear();
            passwordField.clear();
        });

        /**
         * **********************************************************************************
         */
        
        getChildren().addAll(rectangle0,rectangle1,userName,password,textField,passwordField,playBtn,signupBtn);

    }
}
