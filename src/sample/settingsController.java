package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

/**
 * A settings.fxml Controller osztálya.
 */
public class settingsController implements Initializable {

    private Stage window;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        window = Main.getWindow();
    }

    /**
     * Lekezeli a vissza gombot: Visszatér a főmenübe.
     */
    public void backHandler() {
        window.setScene(Main.getMenu());
    }

    /**
     * Lekezeli a reset gombot: Reseteli a High Score adatbázist.
     */
    public void resetHandler() {
        ArrayList<HighScore> list = new ArrayList<>();
        try {
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("highscores.dat"));
            Iterator<HighScore> it = list.iterator();
            while(it.hasNext()) {
                o.writeObject(it.next());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
