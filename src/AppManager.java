import javafx.scene.layout.Pane;
public class AppManager {
    
    static SignUpBase signupbase = new SignUpBase();
    static StartPane startPane = new StartPane();
    static SinglePlayerPane singlePlayerPane = new SinglePlayerPane();
    static MultiPlayerPane multiPlayerPane = new MultiPlayerPane();
    static Settings settingsPane = new Settings();
    static GamePane gamePane = new GamePane();
    static Multiplier multiplier = new Multiplier();
    static Records records = new Records();
    static PlayRecords playRecords = new PlayRecords();
    static PlayerRecords playerRecords = new PlayerRecords();
    static boolean isSingleModeOn = true;
    public static void viewPane(Pane pane) {
        startPane.setVisible(false);
        playerRecords.setVisible(false);
        playRecords.setVisible(false);
        records.setVisible(false);
        singlePlayerPane.setVisible(false);
        multiPlayerPane.setVisible(false);
        settingsPane.setVisible(false);
        gamePane.setVisible(false);
        signupbase.setVisible(false);
        multiplier.setVisible(false);
        pane.setVisible(true);
    }
}
