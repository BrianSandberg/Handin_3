package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import java.sql.SQLException;
import java.util.ArrayList;

public class Controller {
    Model model;
    View view;

    public Controller(Model model) {
        this.model = model;
        try {
            model.connect();
            model.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    //The method that handles the actions of the different elements in the 'view'
    public void setView(View view) {
        this.view = view;
        //We use lambda expressions for this bit of code, to make it shorter and easier to use
        //Here the lambda expressions are used as eventlisteners for the buttons
        view.exitBtn.setOnAction(e -> Platform.exit());
        EventHandler<ActionEvent> PrintStudentAverage = e -> {
            try {
                PrintStudentAVG(view.StudentsComB.getValue(), view.Comments);
            } catch (SQLException f) {
                System.out.println(f.getMessage());
            }
        };

        EventHandler<ActionEvent> PrintCourseAverage = e -> {
            try {
                PrintCourseAVG(view.CoursesComB.getValue(), view.Comments);
            } catch (SQLException d) {
                System.out.println(d.getMessage());
            }
        };
        //Sets the actions up with the buttons in the view class
        view.FindStudentAverage.setOnAction(PrintStudentAverage);
        view.FindCourseAverage.setOnAction((PrintCourseAverage));

    }

    public ObservableList<String> getStudentCourse() throws SQLException {
        ArrayList<String> StudentCourses = model.SQLQueryStudentCourse();
        ObservableList<String> CourseNames = FXCollections.observableList(StudentCourses);
        return CourseNames;
    }

    public ObservableList<String> getStudents() throws SQLException {
        ArrayList<String> Students = model.SQLQueryStudentNames();
        ObservableList<String> StudentNames = FXCollections.observableList(Students);
        return StudentNames;
    }

    //Since model.GetInformation doesnt return anything, and .appenText() requires a String, we have no appended anything
    public void PrintCourseAVG(String CourseID, TextArea text) throws SQLException {
        text.clear();

        System.out.println("\n The average grade for the course " + CourseID);
        model.PreparedStmtSQLQueryCourseAverage();
        model.GetInformation(CourseID);
    }

    public void PrintStudentAVG(String StudentID, TextArea text) throws SQLException {
        text.clear();

        System.out.println("Student " + StudentID + " Courses");
        model.PreparedStmtQLQueryStudentCourse();
        model.GetInformation(StudentID);
        System.out.println("\n Student " + StudentID + " Grades in these courses");
        model.PreparedStmtSQLQueryStudentGrade();
        model.GetInformation(StudentID);
        System.out.println("\n Student " + StudentID + " Average grade:");
        model.PreparedStmtSQLQueryStudentAverage();
        model.GetInformation(StudentID);

        //text.appendText(model.GetInformation(StudentID));
    }
}

