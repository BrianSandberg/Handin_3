package sample;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.SQLException;


public class Main extends Application {
    @Override
    // Since we work with JavaFX, we dont really need the main method, but instead use the start method
    public void start(Stage primaryStage) throws Exception {
        //Change this to your own local path of the database
        String url = "jdbc:sqlite:C:\\Users\\Olli_\\IdeaProjects\\Handin_3\\Handin_3.db";
        Model HDB = new Model(url);
        Controller control = new Controller(HDB);
        View view = new View(control);
        control.setView(view);
        primaryStage.setTitle("Handin3");
        primaryStage.setScene(new Scene(view.asParent(), 600, 475));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

