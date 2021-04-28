package sample;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class Model {
    Connection conn = null;
    String url;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;

    Model(String url) {
        this.url = url;
    }

    public void connect() throws SQLException {
        conn = getConnection(url);
    }

    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public void createStatement() throws SQLException {
        this.stmt = conn.createStatement();
    }

    //Returns a students courses, based on the StudentID - Prepared statement
    public void PreparedStmtQLQueryStudentCourse() {
        String sql_CourseName = "Select Course from ((Course INNER JOIN Grade ON Course.CourseID = Grade.CourseID) " +
                "INNER JOIN Student ON Student.StudentID = Grade.StudentID) Where Student.Name = ?;";
        try {
            pstmt = conn.prepareStatement(sql_CourseName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    //Returns a students grades, based on the StudentID - Prepared statement
    public void PreparedStmtSQLQueryStudentGrade() {
        String sql_Grade = "Select Grade from Grade INNER JOIN Student ON Grade.StudentID = Student.StudentID Where Student.Name = ?;";
        try {
            pstmt = conn.prepareStatement(sql_Grade);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void PreparedStmtSQLQueryStudentAverage() {
        String sql_AVGGrade = "Select AVG(Grade) from Grade INNER JOIN Student ON Student.StudentID = Grade.StudentID WHERE Student.Name = ?;";
        try {
            pstmt = conn.prepareStatement(sql_AVGGrade);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void PreparedStmtSQLQueryCourseAverage() {
        //Joins Grade and Course - Finds the average value of grades in the course we input
        String sql_AVGCourse = "Select AVG(Grade) from Grade INNER JOIN Course ON Course.CourseID = Grade.CourseID Where Course.Course = ?;";
        try {
            pstmt = conn.prepareStatement(sql_AVGCourse);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //The method executing all prepared statements - Takes String as argument, to make it more versatile

    //Cant append this information while its a void signature - Has to be some sort of list, but cant get it to work
    public void GetInformation(String StudentID) {
        try {
            pstmt.setString(1, StudentID);
            rs = pstmt.executeQuery();
            if (rs == null) {
                System.out.println("No Records Found");
            }
            while (rs != null && rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        pstmt = null;
        rs = null;

    }

    //Returns a list of courses - Used for the combobox in view
    public ArrayList<String> SQLQueryStudentCourse() throws SQLException {
        ArrayList<String> CourseName = new ArrayList<>();
        String sql_CourseName = "Select Course from Course;";
        try {
            rs = stmt.executeQuery(sql_CourseName);
            while (rs != null && rs.next()) {
                String name = rs.getString(1);
                CourseName.add(name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        rs = null;
        return CourseName;
    }

    //Returns a list of the names of students - Only used for the combobox in view
    public ArrayList<String> SQLQueryStudentNames() throws SQLException {
        ArrayList<String> StudentName = new ArrayList<>();
        String sql_CourseName = "Select Name from Student;";
        try {
            rs = stmt.executeQuery(sql_CourseName);
            while (rs != null && rs.next()) {
                String name = rs.getString(1);
                StudentName.add(name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        rs = null;
        return StudentName;
    }

}
