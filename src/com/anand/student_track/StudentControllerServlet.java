package com.anand.student_track;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private StudentDbUtil studentDbUtil;

    @Resource(name = "jdbc/student_tracer")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            studentDbUtil = new StudentDbUtil(dataSource);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String command = request.getParameter("command");
            if (command == null) {
                command = "LIST";
            }

            switch (command) {
                case "LIST":
                    listStudents(request, response);
                    break;
                case "ADD":
                    addStudents(request, response);
                    break;
                case "LOAD":
                    loadStudent(request, response);
                    break;
                case "UPDATE":
                    updateStudent(request, response);
                    break;
                case "DELETE":
                	deleteStudent(request,response);
                	break;
                default:
                    listStudents(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Forward to an error page or display an error message
            throw new ServletException(e);
        }
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	String studentId=request.getParameter("studentId");
    	studentDbUtil.updateStudent(studentId);
    	listStudents(request,response);
		
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Read student info from form data
        int id = Integer.parseInt(request.getParameter("studentId"));
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        int pin = Integer.parseInt(request.getParameter("pin"));

        // Create a new student object
        Student theStudent = new Student(id, firstName, lastName, email, pin);

        // Perform update on database
        studentDbUtil.updateStudent(theStudent);

        // Send them back to the "list students" page
        response.sendRedirect("StudentControllerServlet?command=LIST");
    }

    private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Read student ID from request parameter
            String studentId = request.getParameter("studentId");
            
            // Retrieve student details from the database
            Student student = studentDbUtil.getStudent(studentId);
            
            // Set the student object as a request attribute
            request.setAttribute("THE_STUDENT", student);
            
            // Forward to the update form JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions or errors appropriately
        }
    }


    private void addStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Read student info from form data
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        int pin = Integer.parseInt(request.getParameter("pin"));

        // Create a new student object
        Student theStudent = new Student(firstName, lastName, email, pin);

        // Add the student to the database
        studentDbUtil.addStudent(theStudent);

        // Send back to main page (the student list)
        listStudents(request, response);
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Get students from db util
        List<Student> students = studentDbUtil.getStudents();

        // Add students to the request
        request.setAttribute("STUDENT_LIST", students);

        // Send to JSP page (view)
        RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
        dispatcher.forward(request, response);
    }
}
