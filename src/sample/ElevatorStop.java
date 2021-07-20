package sample;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A lift "megállókat" megvalósító osztály.
 */
class ElevatorStop extends Group {

    private int floor;

    /**
     * Konstruktor, létrehozza a megállót.
     * @param x A megálló bal felső sarkának X koordinátája.
     * @param y A megálló bal felső sarkának Y koordinátája.
     * @param floor Hányadik emeleten van a megálló.
     */
    ElevatorStop(double x, double y, int floor) {
        Rectangle stop = new Rectangle(x, y, 5*Elevator.SizeScale, 7.5*Elevator.SizeScale);
        stop.setStroke(Color.DARKSLATEGRAY);
        stop.setOpacity(0.5);
        this.getChildren().add(stop);

        this.floor = floor;
    }

    /**
     * @return Az emelet, ahol a megálló van.
     */
    int getFloor() {
        return floor;
    }
}
