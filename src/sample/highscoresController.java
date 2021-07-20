package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * A highscores.fxml Controller osztálya.
 */
public class highscoresController implements Initializable {

    private Stage window;

    @FXML
    private StackPane pane;

    private TableView<HighScore> table;
    private TableColumn<HighScore, String> nameColumn;
    private TableColumn<HighScore, Long> scoreColumn;

    /**
     * Inicializálja a High Scores táblázatot.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        window = Main.getWindow();

        table = new TableView<>();
        nameColumn = new TableColumn<>("Name");
        scoreColumn = new TableColumn<>("Score");

        pane.getChildren().add(table);

        ArrayList<HighScore> list = new ArrayList<>();
        try {
            ObjectInputStream o = new ObjectInputStream(new FileInputStream("highscores.dat"));
            while(list.add((HighScore)o.readObject()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        ObservableList<HighScore> olist = FXCollections.observableArrayList(list);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        table.setItems(olist);
        table.getColumns().addAll(nameColumn, scoreColumn);
    }

    /**
     * Lekezeli a vissza gombot: Visszatér a főmenübe.
     */
    public void backHandler() {
        window.setScene(Main.getMenu());
    }

}
