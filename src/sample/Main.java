package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
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
        String url = "jdbc:sqlite:C:\\Users\\Olli_\\IdeaProjects\\SD_Handin3\\Handin3.db";
        Model model = new Model(url);
        launch(args);
        try {
            model.connect();
            model.createStatement();
            /*ArrayList<String> courses = model.SQLQueryStudentCourse();
            //ArrayList<String> grades = model.SQLQueryStudentGrade();
            model.PreparedStmtQLQueryStudentCourse();
            model.GetInformation("1");
            model.PreparedStmtSQLQueryStudentAverage();
            model.GetInformation("1");
            model.PreparedStmtSQLQueryCourseAverage();
            model.GetInformation("1");
            model.PreparedStmtSQLQueryStudentGrade();
            model.GetInformation("1");*/
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                model.close();
            } catch (SQLException d) {
                System.out.println(d.getMessage());
            }
        }
    }
}

