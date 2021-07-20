package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith( JfxTestRunner.class )
public class ElevatorTest {

    Elevator elevator;

    @Before
    public void setUp() {
        elevator = new Elevator(0, 0);
        Scene scene = new Scene(elevator, 800, 1000);
    }

    @Test
    public void notMovingElevator() {
        ArrayList<NPC> input = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            input.add(new NPC(0, 0));
            elevator.addNPC(input.get(i));
        }
        ArrayList<NPC> output = elevator.removeNPCs();
        Assert.assertEquals(0, output.size(), 0);
    }

    @Test
    public void movingElevator() {
        NPC tmpNPC;
        for (int i = 0; i < 4; i++) {
            tmpNPC = new NPC(0, 0);
            elevator.addNPC(tmpNPC);
            elevator.setFloor(tmpNPC.getDestination());
            while (elevator.isMoving()) {}
            Assert.assertTrue(elevator.removeNPCs().contains(tmpNPC));
        }
    }

}