package sample;

import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * A lifteket megvalósító osztály.
 */
public class Elevator extends Group {

    static final double SizeScale = 10;
    private static final int maxSize = 4;

    private ArrayList<NPC> NPCList;
    private int floor = 0;
    private Rectangle elevator;
    private Label destinations;
    private boolean selected = false;
    private boolean moving = false;
    private double x, y;

    /**
     * Konstruktor, létrehozza a liftet
     * @param x A lift bal felső sarkának X koordinátája.
     * @param y A lift bal felső sarkának Y koordinátája.
     */
    Elevator(double x, double y) {
        this.x = x;
        this.y = y;
        elevator = new Rectangle(x, y, 5*SizeScale, 7.5*SizeScale);
        elevator.setStroke(Color.WHITE);
        elevator.setStrokeWidth(2);

        NPCList = new ArrayList<>();


        destinations = new Label();
        destinations.setTextFill(Color.WHITE);
        destinations.setLayoutX(x);
        destinations.setLayoutY(y);

        this.getChildren().addAll(elevator, destinations);
    }

    /**
     * Hozzáad egy NPC-t a lifthez.
     * @param npc Az NPC akit szeretnénk a liftbe "helyezni".
     * @return Igaz, ha sikerült elhelyezni (ha a lift tele van, akkor nem sikerült).
     */
    boolean addNPC(NPC npc) {
        if (NPCList.size() < maxSize) {
            NPCList.add(npc);
            NPCList.sort(NPC::compareTo);
            resetText();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Kiszállítja az összes NPC-t aki arra a szintre szeretett volna eljutni, ahol éppen a lift áll.
     * @return A kiszálló NPC-k listája.
     */
    ArrayList<NPC> removeNPCs() {
        ArrayList<NPC> returnList = new ArrayList<>();
        for (NPC npc : NPCList) {
            if (npc.getDestination() == floor) {
                returnList.add(npc);
                //Game.addScore();
            }
        }
        NPCList.removeAll(returnList);
        resetText();
        return returnList;
    }

    /**
     * Elmozdítja a liftet animációval együtt.
     * @param x Az emelet száma, ahová a lift elmozdul.
     */
    void moveToFloor(int x) {
        if ((!moving) && ((x >= Game.bottomFloor) && (x <= Game.topFloor))) {
            TranslateTransition transition = new TranslateTransition(Duration.millis(500*(Math.abs(x-floor))), this);
            transition.setToY(-(100*x));
            transition.play();
            moving = true;
            transition.setOnFinished(e -> {
                setFloor(x);
                ArrayList<NPC> tmp = removeNPCs();
                for (NPC n : tmp) {
                    Game.addScore();
                }
                moving = false;
            });
        }
    }

    /**
     * @return A szint ahol épp a lift tartózkodik.
     */
    int getFloor() {
        return floor;
    }

    void setFloor(int x) { floor = x; }

    /**
     * Beállítja, hogy a lift épp ki van e választva, vagy nincs. (És ezzel együtt a színét is változtatja)
     * @param b true = Ki van választva
     */
    void setSelected(boolean b) {
        if (b) {
            elevator.setStroke(Color.RED);
        } else {
            elevator.setStroke(Color.WHITE);
        }
        selected = b;
    }

    /**
     * @return Igaz, ha a lift ki van választva.
     */
    boolean isSelected() {
        return selected;
    }

    /**
     * @return Igaz, ha a lift éppen mozog.
     */
    boolean isMoving() {
        return moving;
    }

    /**
     * Beállítja a lifthez tartozó szám Label-eket újra, az aktualitásnak megfelelően.
     */
    private void resetText() {
        boolean first = true;
        if (NPCList.isEmpty()) {
            destinations.setText("");
        } else {
            for (NPC npc : NPCList) {
                if (first) {
                    destinations.setText(Integer.toString(npc.getDestination()));
                    first = false;
                } else {
                    destinations.setText(destinations.getText() + "\n" + npc.getDestination());
                }
            }
        }
    }

    /**
     * A Group osztály metódusának felülírása. Megnézi, hogy a lift tartalmazza e az adott pontot.
     * @param localX A vizsgálandó pont X koordinátája.
     * @param localY A vizsgálandó pont Y koordinátája.
     * @return Igaz, ha tartalmazza a pontot.
     */
    @Override
    public boolean contains(double localX, double localY) {
        Rectangle tmp = new Rectangle(x, y - (floor * 100), 5*SizeScale, 7.5*SizeScale);
        return tmp.contains(localX, localY);
    }
}
