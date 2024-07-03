package student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Student
 */
public class Student extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String userId = request.getParameter("userId");
	        String firstName = request.getParameter("firstName");
	        String lastName=request.getParameter("secondName");

	        try {
	           
	            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/student", "postgres", "Lukatoni123");
	            String sql = "INSERT INTO students (id, firstname, lastname) VALUES (?, ?, ?)";
	            PreparedStatement pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, Integer.parseInt(userId));
	            pstmt.setString(2, firstName);
	            pstmt.setString(3, lastName);
	            int rowAffected = pstmt.executeUpdate();

	            if (rowAffected != 0){
	            	 response.setContentType("text/html");
	                 response.getWriter().println("<html><body>");
	                 response.getWriter().println("<h4>student registered </h4>");
	                 response.getWriter().println("</body></html>");

	            	
	            	}

	            
	            pstmt.close();
	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        try {

	            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/student", "postgres", "Lukatoni123");
	            String sql = "SELECT * FROM students ";
	        	 PreparedStatement pstmt = conn.prepareStatement(sql);
	             
	             ResultSet rs = pstmt.executeQuery();
	         

	             if (rs.next()) {
	                 String first = rs.getString("firstname");
	                 String second=rs.getString("lastname");
	                 response.setContentType("text/html");
	                 response.getWriter().println("<html><body>");
	                 response.getWriter().println("<h1>User Information</h1>");
	                 response.getWriter().println("<table border='1'>");
	                 response.getWriter().println("<tr><th>ID</th><th>firstName</th><th>lastName</th></tr>");
	                 response.getWriter().println("<tr><td>" + userId + "</td><td>" + first + "</td><td>"+lastName+"</td></tr>");
	                 response.getWriter().println("</table>");
	                 response.getWriter().println("</body></html>");
	             }
	        	
	        	
	        }catch(Exception e) {
	        	e.printStackTrace();
	        }
	        
	       

	}

}
