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

    //Connects to our database, based on the input we have given it in the variable 'url'
    public void connect() throws SQLException {
        conn = getConnection(url);
    }

    //Never used, but is used to close the connection to the database, should that ever be needed
    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public void createStatement() throws SQLException {
        this.stmt = conn.createStatement();
    }

    //Returns a students courses, based on an inner join between the three tables.
    // It finds the columns equal to each other, as shown, and then returns the Course.Course from the rows in which the Student.Name is equal to input
    //Prepared statement
    public void PreparedStmtQLQueryStudentCourse() {
        String sql_CourseName = "Select Course from ((Course INNER JOIN Grade ON Course.CourseID = Grade.CourseID) " +
                "INNER JOIN Student ON Student.StudentID = Grade.StudentID) Where Student.Name = ?;";
        try {
            pstmt = conn.prepareStatement(sql_CourseName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    //Returns a students grades, based on an inner join between the Grade table and the Student table
    //Joins the columns in which Grade.StudentID = Student=StudentID, and then returns the Grade.Grade from the rows matching the Student.Name
    // Prepared statement
    public void PreparedStmtSQLQueryStudentGrade() {
        String sql_Grade = "Select Grade from Grade INNER JOIN Student ON Grade.StudentID = Student.StudentID Where Student.Name = ?;";
        try {
            pstmt = conn.prepareStatement(sql_Grade);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Returns a students average grade, based on an inner join between the Grade table and the Student table
    //Same procedure as the last prepared statement
    public void PreparedStmtSQLQueryStudentAverage() {
        String sql_AVGGrade = "Select AVG(Grade) from Grade INNER JOIN Student ON Student.StudentID = Grade.StudentID WHERE Student.Name = ?;";
        try {
            pstmt = conn.prepareStatement(sql_AVGGrade);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Returns a courses average grade, based on an inner join between the Course table and the Grade table
    //Joins the columns in which Course.CourseID = Grade=CourseID, and then returns the (AVG)Grade.Grade from the rows matching the course name
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
    //Cant append this information in the view while it has a void signature - Has to be some sort of list, but cant get it to work properly
    //Prints in the console
    public void GetInformation(String StudentID) {
        try {
            //While using the prepared statements we have, we only need parameter index 1. This in only used for statements with multiple '?'
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
    //Not a prepared statements, since it has a 'full' SQL statement and returns something on its own
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
