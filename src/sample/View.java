package sample;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import java.sql.SQLException;

public class View {
    Controller control;
    private GridPane StartView;
    //Creates the different elements in the pane
    Button exitBtn = new Button("Exit");
    Button FindStudentAverage = new Button("Find students average");
    Button FindCourseAverage = new Button("Find Course Average");
    Label AverageCourseGradeLbl = new Label("Average grade for course:");
    Label AverageStudentGradeLbl = new Label("Average grade for student:");
    //This TextArea is not really used in the current version of the program, but is kept in for further development purposes
    TextArea Comments = new TextArea();
    ComboBox<String> CoursesComB = new ComboBox<>();
    ComboBox<String> StudentsComB = new ComboBox<>();

    public View(Controller control) throws SQLException {
        this.control = control;
        CreateAndConfigure();
    }

    private void CreateAndConfigure() throws SQLException {
        StartView = new GridPane();
        StartView.setMinSize(300, 200);
        StartView.setPadding(new Insets(10, 10, 10, 10));
        StartView.setVgap(5);
        StartView.setHgap(1);

        StartView.add(AverageCourseGradeLbl, 1, 1);
        StartView.add(StudentsComB, 15, 1);
        StartView.add(AverageStudentGradeLbl, 1, 5);
        StartView.add(CoursesComB, 15, 5);
        StartView.add(FindStudentAverage, 10, 1);
        StartView.add(FindCourseAverage, 10, 5);
        StartView.add(Comments, 1, 7, 15, 7);
        StartView.add(exitBtn, 20, 15);

        ObservableList<String> CoursesList = control.getStudentCourse();
        CoursesComB.setItems(CoursesList);
        CoursesComB.getSelectionModel().selectFirst();

        ObservableList<String> StudentsList = control.getStudents();
        StudentsComB.setItems(StudentsList);
        StudentsComB.getSelectionModel().selectFirst();

    }

    //A parent is needed to set the scene
    public Parent asParent() {
        return StartView;
    }
}
