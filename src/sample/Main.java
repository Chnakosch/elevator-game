package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class Main extends Application {

    private static Stage window;
    private static Scene menu;

    @Override
    public void start(Stage primaryStage) throws Exception{

        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        window.setTitle("A Liftes Játék");
        window.setResizable(false);
        menu = new Scene(root, 400, 400);
        window.setScene(menu);
        window.setY(0);
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    static Stage getWindow() {
        return window;
    }
    static Scene getMenu() { return menu; }
}
