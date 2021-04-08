
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;

/**
 *
 * @author Aya
 */
public class VideoHandler extends Application{
    
    private String Dir = System.getProperty("user.dir");
    
    
    public static String winnerName=new String();

    /**
     *
     * @param st
     */
    public static void setWinnerName(String st){
            winnerName=st;      
    }

    /**
     *
     * @return
     */
    public static String getWinnerName(){
           return winnerName;      
    }
    
    
    
    @Override
    public void start(Stage stage) throws Exception {
    
        //goes to user Directory
        //File f = new File("winner.mp4");
        Media media = new Media(getClass().getResource("winner.mp4").toExternalForm());
        //Converts media to string URL  //exception
       // Media media = new Media(f.toURI().toURL().toString());
       //Media media = new Media(getClass().getResource("winner.mp4").toExternalForm());
        javafx.scene.media.MediaPlayer player = new   javafx.scene.media.MediaPlayer(media);
        MediaView viewer = new MediaView(player);
        
        
        Label winner = new Label(VideoHandler.getWinnerName());
        winner.setFont(new Font("Serif", 40));
        winner.setTextFill(Color.web("#FFD700"));
        winner.setTranslateY(50);

        //change width and height to fit video
        DoubleProperty width = viewer.fitWidthProperty();
        DoubleProperty height = viewer.fitHeightProperty();
        
        
        viewer.setPreserveRatio(true);

        StackPane root = new StackPane();
        root.getChildren().add(viewer);
        root.getChildren().add(winner);

        //set the Scene
        Scene scenes = new Scene(root, 500, 285, Color.BLACK);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scenes);
        stage.setTitle("WINNER!");
        stage.show();
        player.play();
        player.setStopTime(Duration.millis(9000.0));
        width.bind(Bindings.selectDouble(viewer.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(viewer.sceneProperty(), "height"));
        player.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                player.stop();
                stage.close();
            }
           
        });
         
    }
    
 
    
    
}