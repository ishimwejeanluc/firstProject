package student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Student
 */
public class Student extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(Student.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Student() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Handling GET request");
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Handling POST request");

        String userId = request.getParameter("userId");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("secondName");

        logger.debug("Received parameters: userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName);

        // Insert data into database
        try {
            logger.trace("Loading PostgreSQL driver");
            Class.forName("org.postgresql.Driver");

            logger.trace("Connecting to database");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/student", "postgres", "Lukatoni123");

            logger.trace("Preparing SQL statement for insertion");
            String sql = "INSERT INTO students (id, firstname, lastname) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(userId));
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);

            logger.debug("Executing update");
            int rowAffected = pstmt.executeUpdate();

            if (rowAffected != 0) {
                logger.info("Student registered successfully");
                response.setContentType("text/html");
                response.getWriter().println("<html><body>");
                response.getWriter().println("<h4>Student registered successfully</h4>");
                response.getWriter().println("</body></html>");
            } else {
                logger.warn("No rows affected during registration");
            }

            pstmt.close();
            conn.close();
        } catch (Exception e) {
            logger.error("Failed to register student", e);
        }

        // Retrieve data from database
        try {
            logger.trace("Connecting to database for retrieval");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/student", "postgres", "Lukatoni123");

            logger.trace("Preparing SQL statement for retrieval");
            String sql = "SELECT * FROM students WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(userId));

            logger.debug("Executing query");
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String first = rs.getString("firstname");
                String second = rs.getString("lastname");
                response.setContentType("text/html");
                response.getWriter().println("<html><body>");
                response.getWriter().println("<h1>User Information</h1>");
                response.getWriter().println("<table border='1'>");
                response.getWriter().println("<tr><th>ID</th><th>First Name</th><th>Last Name</th></tr>");
                response.getWriter().println("<tr><td>" + userId + "</td><td>" + first + "</td><td>" + second + "</td></tr>");
                response.getWriter().println("</table>");
                response.getWriter().println("</body></html>");
                logger.info("User information retrieved successfully");
            } else {
                logger.warn("No user found with id " + userId);
            }

            pstmt.close();
            conn.close();
        } catch (Exception e) {
            logger.error("Failed to retrieve user information", e);
        }
    }
}
