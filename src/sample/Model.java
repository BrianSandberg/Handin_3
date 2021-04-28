package sample;

import javax.xml.transform.Result;
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
    public void PreparedStmtQLQueryStudentCourse(){
        ArrayList<String> CourseName = new ArrayList<>();
        String sql_CourseName = "Select Course from Course " +
                "INNER JOIN Student ON Student.StudentID = Course.StudentID Where Student.Name = ?;";
        try {
            pstmt = conn.prepareStatement(sql_CourseName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    //Returns a students grades, based on the StudentID - Prepared statement
    public void PreparedStmtSQLQueryStudentGrade(){
        String sql_Grade = "Select Grade from Grade INNER JOIN Student ON Grade.StudentID = Student.StudentID Where Student.Name = ?;";
        try {
            pstmt = conn.prepareStatement(sql_Grade);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void PreparedStmtSQLQueryStudentAverage(){
        String sql_AVGGrade = "Select AVG(Grade) from Grade INNER JOIN Student ON Student.StudentID = Grade.StudentID WHERE Student.Name = ?;";
        try {
            pstmt = conn.prepareStatement(sql_AVGGrade);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void PreparedStmtSQLQueryCourseAverage(){
        //Joins Grade and Course - Finds the average value of grades in the course we input
        String sql_AVGCourse = "Select AVG(Grade) from Grade INNER JOIN Course ON Course.CourseID = Grade.CourseID Where Course.Course = ?;";
        try {
            pstmt = conn.prepareStatement(sql_AVGCourse);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //The method executing all prepared statements - Takes String as argument, to make it more versatile
    public void GetInformation(String StudentID){
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


    //Returns a students courses, based on the StudentID
    public ArrayList<String> SQLQueryStudentAVG(String StudentID) throws SQLException{
        ArrayList<String> average = new ArrayList<>();
        String sql_Grade = "Select AVG(Grade) from Grade where StudentID = ?;";
        try {
            rs = stmt.executeQuery(sql_Grade);
            while (rs != null && rs.next()) {
                String name = rs.getString(1);
                average.add(name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        rs = null;
        return average;
    }

    //Returns a students courses, based on the StudentID
    public ArrayList<String> SQLQueryStudentCourse() throws SQLException{
        ArrayList<String> CourseName = new ArrayList<>();
        String sql_CourseName = "Select Course from Course;";
                //"INNER JOIN Grade ON Grade.CourseID = Course.CourseID Where Grade.StudentID= ?;";
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

    public ArrayList<String> SQLQueryStudentNames() throws SQLException{
        ArrayList<String> StudentName = new ArrayList<>();
        String sql_CourseName = "Select Name from Student;";
        //"INNER JOIN Grade ON Grade.CourseID = Course.CourseI D Where Grade.StudentID= ?;";
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

    public void printNames(ArrayList<String> Names) {
        for (int i = 0; i < Names.size(); i++) {
            System.out.println(Names.get(i));
        }
    }
}
