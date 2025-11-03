import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AttendanceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String studentId = request.getParameter("studentId");
        String date = request.getParameter("date");
        String status = request.getParameter("status");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/nimbusdb", "root", "password");

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO Attendance(StudentID, Date, Status) VALUES (?, ?, ?)");
            ps.setInt(1, Integer.parseInt(studentId));
            ps.setString(2, date);
            ps.setString(3, status);

            int i = ps.executeUpdate();
            if (i > 0)
                out.println("<h3>Attendance marked successfully!</h3>");
            else
                out.println("<h3>Failed to mark attendance.</h3>");

            con.close();
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }

        out.close();
    }
}
