package sample;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * A menu.fxml Controller osztálya.
 */
public class menuController implements Initializable {

    private Stage window;
    private Scene highscores, settings;

    /**
     * Inicializálja az almenüket.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        window = Main.getWindow();

        try {
            Parent st = FXMLLoader.load(getClass().getResource("settings.fxml"));
            settings = new Scene(st,400,400);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Lekezeli a menügombot: Elindítja a játékot.
     */
    public void startgameHandler() {
        new Game();
    }

    /**
     * Lekezeli a menügombot: Megnyitja a High Scores-t.
     */
    public void highscoresHandler() {
        try {
            Parent hs = FXMLLoader.load(getClass().getResource("highscores.fxml"));
            highscores = new Scene(hs, 400, 400);
            window.setScene(highscores);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Lekezeli a menügombot: Megnyitja a beállításokat.
     */
    public void settingsHandler() {
        window.setScene(settings);
    }

    /**
     * Lekezeli a menügombot: Kilép a játékból.
     */
    public void quitHandler() {
        window.close();
    }
}
