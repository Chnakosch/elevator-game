package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.ResourceBundle;

/**
 * A popup.fxml Controller osztálya.
 */
public class popupController implements Initializable {

    @FXML
    private TextField nameInput;

    @FXML
    private Label hsLabel;

    /**
     * Inicializálja az elért pontszám kiírását.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hsLabel.setText("High Score: " + Game.getScore());
    }

    /**
     * Lekezeli a gombot: Elmenti az új High Score-t a megadott néven.
     */
    public void saveHandler() {
        ArrayList<HighScore> list = new ArrayList<>();
        try {
            ObjectInputStream o = new ObjectInputStream(new FileInputStream("highscores.dat"));
            while(list.add((HighScore)o.readObject()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        list.add(new HighScore(nameInput.getText(), Game.getScore()));

        Collections.sort(list, new HighScoreComparator());
        Collections.reverse(list);
        try {
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("highscores.dat"));
            Iterator<HighScore> it = list.iterator();
            while(it.hasNext()) {
                o.writeObject(it.next());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Main.getWindow().setScene(Main.getMenu());
        Game.getPopup().close();
    }
}
