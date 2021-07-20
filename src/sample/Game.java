package sample;

import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A játék fő részét megvalósító osztály.
 */
public class Game {

    static final int bottomFloor = -1;
    static final int topFloor = 8;

    private Stage window;
    private static Stage popup;
    private LinkedList<NPC> LeftLine = new LinkedList<>();
    private LinkedList<NPC> RightLine = new LinkedList<>();
    private boolean gameover = false;
    private static double randomchance = 0.1;
    private static long score;
    private static Label ScoreLabel;

    /**
     * Konstruktor ami magába foglalja a játék működésének megvalósítását. Az osztály példányosításával már el is kezdődik a játék rögtön.
     * Figyeli az egér és billentyűzet eseményeket, kezeli az NPC-ket, a lifteket. Minden amit csak látni lehet játék közben, az itt történik.
     */
    public Game() {
        score = 0;
        window = Main.getWindow();
        Group root = new Group();
        Scene scene = new Scene(root, 800, 1000, Color.BLACK);
        window.setScene(scene);
        Button backButton = new Button("Back to menu");
        backButton.setStyle("-fx-background-color: #585858; -fx-text-fill: #F8F8F8; -fx-font-size: 20; -fx-background-radius: 15;");
        backButton.setOnAction(e -> window.setScene(Main.getMenu()));

        Elevator leftElevator = new Elevator(250, 825);
        Elevator rightElevator = new Elevator(500, 825);

        ArrayList<ElevatorStop> leftStops = new ArrayList<>();
        ArrayList<ElevatorStop> rightStops = new ArrayList<>();
        for (int i = bottomFloor; i <= topFloor; i++) {
            root.getChildren().addAll(
                    WhiteLine(250, 800-(i*100), 550, 800-(i*100)), // Floor
                    WhiteLine(250, 800-(i*100), 250, 900-(i*100)), // Left wall
                    WhiteLine(550, 800-(i*100), 550, 900-(i*100)) // Right wall
            );
            if (i == 0) {
                root.getChildren().add(WhiteLabel(365, 880-(i*100), "Ground Floor")); // Ground Floor Sign
            } else {
                root.getChildren().add(WhiteLabel(380, 880-(i*100), "Floor " + i)); // Floor Sign
            }
            leftStops.add(new ElevatorStop(250, 825-(i*100), i));
            rightStops.add(new ElevatorStop(500, 825-(i*100), i));
        }

        scene.setOnMouseClicked(e -> {
            switch (e.getButton()) {
                case PRIMARY:
                    if ((leftElevator.contains(e.getX(), e.getY()))) {
                        leftElevator.setSelected(true);
                    } else {
                        leftElevator.setSelected(false);
                    }
                    if ((rightElevator.contains(e.getX(), e.getY()))) {
                        rightElevator.setSelected(true);
                    } else {
                        rightElevator.setSelected(false);
                    }
                    break;
                case SECONDARY:
                    if (leftElevator.isSelected()) {
                        for (ElevatorStop s : leftStops) {
                            if (s.contains(e.getX(), e.getY())) {
                                leftElevator.moveToFloor(s.getFloor());
                            }
                        }
                    } else if (rightElevator.isSelected()) {
                        for (ElevatorStop s : rightStops) {
                            if (s.contains(e.getX(), e.getY())) {
                                rightElevator.moveToFloor(s.getFloor());
                            }
                        }
                    }
                    break;
            }
        });

        scene.setOnKeyPressed(e -> {
            switch  (e.getCode()) {
                case NUMPAD1:
                case DIGIT1:
                    leftElevator.setSelected(true);
                    rightElevator.setSelected(false);
                    break;
                case NUMPAD2:
                case DIGIT2:
                    leftElevator.setSelected(false);
                    rightElevator.setSelected(true);
                    break;
            }
        });

        for (int i = 0; i <= 10; i++) {
            LeftLine.addFirst(new NPC());
            RightLine.addFirst(new NPC());
        }

        PauseTransition tick = new PauseTransition(Duration.millis(500));
        tick.play();
        tick.setOnFinished(e -> {
            tickTurn(root, leftElevator, true, LeftLine, -50);
            if (!gameover) tickTurn(root, rightElevator, false, RightLine, 825);

            if (!gameover) tick.play();
        });

        ScoreLabel = new Label("Score: " + score);
        ScoreLabel.setLayoutX(650);
        ScoreLabel.setLayoutY(10);
        ScoreLabel.setTextFill(Color.WHITE);
        ScoreLabel.setFont(new Font(20));

        root.getChildren().addAll(
                backButton,
                leftElevator,
                rightElevator,
                ScoreLabel,
                WhiteLine(0, 900, 250, 900), // Left side ground
                WhiteLine(550, 900, 800, 900) // Right side ground
        );
        root.getChildren().addAll(leftStops);
        root.getChildren().addAll(rightStops);


    }

    /**
     * Megnöveli a pontszámlálót 10 ponttal, és kicsivel növeli a nehézséget.
     */
    static void addScore() {
        randomchance += 0.005;
        score += 10;
        ScoreLabel.setText("Score: " + score);
    }

    /**
     * @return Ennyi pontja van éppen a játékosnak.
     */
    static long getScore() {
        return score;
    }

    /**
     * Létrehoz egy fehér vonalat.
     * @param startX A vonal kezdő X koordinátája.
     * @param startY A vonal kezdő Y koordinátája.
     * @param endX A vonal végén lévő X koordináta.
     * @param endY A vonal végén lévő Y koordináta.
     * @return Az elkészült vonal.
     */
    private Line WhiteLine(double startX, double startY, double endX, double endY) {
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(Color.WHITE);
        return line;
    }

    /**
     * Létrehoz egy fehér Label-t.
     * @param x A Label X koordinátája.
     * @param y A Label Y koordinátája.
     * @param text A Label szövege.
     * @return Az elkészült fehér Label.
     */
    private Label WhiteLabel(double x, double y, String text) {
        Label label = new Label(text);
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setTextFill(Color.WHITE);
        return label;
    }

    /**
     * Az NPC-k földszinti mozgását lekezelő metódus.
     * @param root A Group ami tartalmazza a megjelenített alakzatokat. Ehhez lesznek hozzáadva az újonnan bejövő NPC-k.
     * @param elevator A lift amibe az NPC-k beszállnak.
     * @param stepright Igaz, ha az NPC-k balról jobbra közlekednek. Hamis ha jobbról balra.
     * @param line A sorbanálló NPC-ket tartalmazó lista.
     * @param startX Az X koordináta ahol az új NPC-k spawnolnak (Az Y az rögzített a földszint magasságában).
     */
    private void tickTurn(Group root, Elevator elevator, boolean stepright, LinkedList<NPC> line, double startX) {
        if (line.getLast().isFake()) {
            line.removeLast();
            if (Math.random() < randomchance) {
                NPC tmp = new NPC(startX, 900);
                root.getChildren().add(tmp);
                line.addFirst(tmp);
            } else {
                line.addFirst(new NPC());
            }
            for (NPC n : line) {
                if (stepright) {
                    n.stepright();
                } else {
                    n.stepleft();
                }
            }
        } else {
            if ((elevator.getFloor() == 0) && (!elevator.isMoving()) && (elevator.addNPC(line.getLast()))) {
                line.removeLast().setVisible(false);
                if (Math.random() < randomchance) {
                    NPC tmp = new NPC(startX, 900);
                    root.getChildren().add(tmp);
                    line.addFirst(tmp);
                } else {
                    line.addFirst(new NPC());
                }
                for (NPC n : line) {
                    if (stepright) {
                        n.stepright();
                    } else {
                        n.stepleft();
                    }
                }
            } else {
                for (int i = line.size()-2; i >= 1; i--) {
                    if ((line.get(i).isFake()) && (!line.get(i-1).isFake())) {
                        if (stepright) {
                            line.get(i-1).stepright();
                        } else {
                            line.get(i-1).stepleft();
                        }
                        NPC tmp = line.get(i);
                        line.set(i, line.get(i-1));
                        line.set(i-1, tmp);
                    }
                }
                if (line.getFirst().isFake()) {
                    if (Math.random() < randomchance) {
                        NPC tmp = new NPC(startX, 900);
                        root.getChildren().add(tmp);
                        line.set(0, tmp);
                        if (stepright) {
                            line.getFirst().stepright();
                        } else {
                            line.getFirst().stepleft();
                        }
                    }
                } else {
                    gameOver();
                }
            }
        }
    }

    /**
     * A játék végét lekezelő metódus. Létrehozza az ablakot ahova a játékos beírhatja a nevét, és elmentheti a pontszámát.
     * Majd ezt követően visszatér a főmenübe.
     */
    private void gameOver() {
        gameover = true;
        popup = new Stage();
        window.setResizable(false);
        try {
            Parent root = FXMLLoader.load(getClass().getResource("popup.fxml"));
            Scene popup = new Scene(root, 440, 170);
            window.setScene(popup);
            window.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @return A gameOver() metódusban előhívott ablak (Stage).
     */
    static public Stage getPopup() {
        return popup;
    }

}
