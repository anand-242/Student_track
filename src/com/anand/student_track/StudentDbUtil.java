package com.anand.student_track;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil {
    private DataSource dataSource;

    public StudentDbUtil(DataSource theDataSource) {
        dataSource = theDataSource;
    }

    public List<Student> getStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM student_tracer";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String email = rs.getString("email");
                int pin = rs.getInt("pin");
                Student s = new Student(id, first_name, last_name, email, pin);
                students.add(s);
            }
        } finally {
            close(conn, stmt, rs);
        }

        return students;
    }

    public void addStudent(Student s) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            String sql = "INSERT INTO student_tracer (first_name, last_name, email, pin) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, s.getFirst_name());
            stmt.setString(2, s.getLast_name());
            stmt.setString(3, s.getEmail());
            stmt.setInt(4, s.getPin());

            stmt.executeUpdate();
        } finally {
            close(conn, stmt, null);
        }
    }

    private void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Student getStudent(String sid) throws Exception {
        Student st = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int stid;
        try {
            stid = Integer.parseInt(sid);
            conn = dataSource.getConnection();
            String sql = "select * from student_tracer where id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, stid);
            rs = stmt.executeQuery();
            if (rs.next()) {
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String email = rs.getString("email");
                int pin = rs.getInt("pin");
                st = new Student(stid, first_name, last_name, email, pin);
                return st;
            } else {
                throw new Exception("Could not find student id: " + sid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, stmt, rs);
        }
     return null;  
    }

    public void updateStudent(Student theStudent) throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            // get db connection
            myConn = dataSource.getConnection();

            // create SQL update statement
            String sql = "update student_tracer set first_name=?, last_name=?, email=?, pin=? where id=?";

            // prepare statement
            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setString(1, theStudent.getFirst_name());
            myStmt.setString(2, theStudent.getLast_name());
            myStmt.setString(3, theStudent.getEmail());
            myStmt.setInt(4, theStudent.getPin());
            myStmt.setInt(5, theStudent.getId());

            // Log the SQL statement and parameters
            System.out.println("Executing SQL: " + sql);
            System.out.println("Parameters: " + theStudent.getFirst_name() + ", " + theStudent.getLast_name() + ", " + theStudent.getEmail() + ", " + theStudent.getPin() + ", " + theStudent.getId());

            // execute SQL statement
            int rowsAffected = myStmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new Exception("Updating student failed, no rows affected.");
            }
        } finally {
            // clean up JDBC objects
            close(myConn, myStmt, null);
        }
    }

	public void updateStudent(String studentId) throws Exception {
		 Connection myConn = null;
	        PreparedStatement myStmt = null;
		try {
			int id=Integer.parseInt(studentId);
			myConn=dataSource.getConnection();
			String sql="delete from student_tracer where id=?";
			myStmt=myConn.prepareStatement(sql);
			myStmt.setInt(1, id);
			myStmt.execute();
		}
		finally {
			close(myConn,myStmt,null);
		}
	}
}
