package sample;

import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * A liftekkel utazó NPC-ket megvalósító osztály.
 */
public class NPC extends Group implements Comparable<NPC>{


    private static final double StepLength = 25;
    private static final double SizeScale = 10;


    private double stepoffset = StepLength;
    private int destination;
    private boolean fake;

    NPC() { fake = true; }

    /**
     * Létrehozza (megrajzolja) az NPC-t.
     * @param x Az NPC bal alsó sarkának X koordinátája.
     * @param y Az NPC bal alsó sarkának Y koordinátája.
     */
    NPC(double x, double y) {
        fake = false;
        Line leftleg = new Line(x, y, x+SizeScale, y-(2*SizeScale));
        leftleg.setStroke(Color.WHITE);
        Line rightleg = new Line(x+SizeScale, y-(2*SizeScale), x+(2*SizeScale), y);
        rightleg.setStroke(Color.WHITE);
        Line body = new Line(x+SizeScale, y-(2*SizeScale), x+SizeScale, y-(4.5*SizeScale));
        body.setStroke(Color.WHITE);
        Circle head = new Circle(x+SizeScale, y-(5.3*SizeScale), (0.8*SizeScale));
        head.setStroke(Color.WHITE);
        Line leftarm = new Line(x+SizeScale, y-(4.5*SizeScale), x, y-(3*SizeScale));
        leftarm.setStroke(Color.WHITE);
        Line rightarm = new Line(x+SizeScale, y-(4.5*SizeScale), x+(2*SizeScale), y-(3*SizeScale));
        rightarm.setStroke(Color.WHITE);

        ArrayList<Integer> nums = new ArrayList<>();
        for (int i = Game.bottomFloor; i <= Game.topFloor; i++) {
            if (i != 0) {
                nums.add(i);
            }
        }
        destination = nums.get((int)(Math.random() * nums.size()));
        Label label = new Label(Integer.toString(destination));
        label.setTextFill(Color.WHITE);
        if ((destination >= 10) || (destination < 0)) {
            label.setLayoutX(x+(0.3*SizeScale));
        } else {
            label.setLayoutX(x+(0.6*SizeScale));
        }
        label.setLayoutY(y-(8*SizeScale));

        this.getChildren().addAll(leftleg, rightleg, body, head, leftarm, rightarm, label);
    }

    /**
     * Az NPC-t lépteti egyet jobbra. Animációval együtt.
     */
    void stepright() {
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), this);
        transition.setToX(stepoffset);
        transition.play();
        stepoffset += StepLength;
    }

    /**
     * Az NPC-t lépteti egyet balra. Animációval együtt.
     */
    void stepleft() {
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), this);
        transition.setToX(stepoffset-50);
        transition.play();
        stepoffset -= StepLength;
    }

    /**
     * @return Az NPC úticélja (emelet).
     */
    int getDestination() {
        return destination;
    }

    /**
     * @return Igaz, ha fake = true
     * Magyarázat a "fake"-hez:
     * Ha az értéke true, akkor az NPC nem létezik, nincs is kirajzolva, csupán egy listát kitöltő elemként van szerepe.
     */
    boolean isFake() {
        return fake;
    }

    public int compareTo(NPC npc) {
        return destination - npc.getDestination();
    }
}
