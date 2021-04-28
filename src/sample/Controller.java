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
    private View view;

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

    public void setView(View view) throws SQLException {
        this.view = view;
        view.exitBtn.setOnAction(e -> Platform.exit());
        EventHandler<ActionEvent> PrintStudentAverage = e -> {
            try {
                //PrintStudentCourses(view.StudentsComB.getValue(), view.Comments);
                PrintsStudentGrades(view.StudentsComB.getValue(), view.Comments);
                //PrintStudentAVG(view.StudentsComB.getValue(), view.Comments);
            } catch (SQLException f) {
                System.out.println(f.getMessage());
            }
        };

        EventHandler<ActionEvent> PrintCourseAverage = e -> {
            try{
                PrintCourseAVG(view.CoursesComB.getValue(), view.Comments);
            } catch (SQLException d){
                System.out.println(d.getMessage());
            }
        };

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

    public void PrintCourseAVG(String CourseID, TextArea text) throws SQLException{
        text.clear();

        model.PreparedStmtSQLQueryCourseAverage();
        model.GetInformation(CourseID);
    }

    public void PrintStudentAVG(String StudentID, TextArea text) throws SQLException {
        text.clear();
        text.appendText("StudentID, average \n");
        model.PreparedStmtSQLQueryStudentAverage();
        model.GetInformation(StudentID);
        //text.appendText(model.GetInformation(StudentID));
    }

    public void PrintStudentCourses(String StudentID, TextArea text) throws SQLException{
        model.PreparedStmtQLQueryStudentCourse();
        model.GetInformation(StudentID);
    }

    public void PrintsStudentGrades(String StudentID, TextArea text) throws SQLException{
        model.PreparedStmtSQLQueryStudentGrade();
        model.GetInformation(StudentID);
    }


}

