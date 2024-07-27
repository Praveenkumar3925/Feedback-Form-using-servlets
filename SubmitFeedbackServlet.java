import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/submitFeedback")
public class SubmitFeedbackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/praveen";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Praveen@123";
    private static final String QUERY = "INSERT INTO feedback1 (name, rating, comments) VALUES (?, ?, ?)";
    
    private Connection con = null;
    private PreparedStatement statement = null;
    
    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully");
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connection Established");
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            statement = con.prepareStatement(QUERY);
            String name = request.getParameter("name");
            int rating = Integer.parseInt(request.getParameter("rating"));
            String comments = request.getParameter("comments");

            statement.setString(1, name);
            statement.setInt(2, rating);
            statement.setString(3, comments);
            int rowsInserted=statement.executeUpdate();
            if (rowsInserted > 0) {
                response.sendRedirect("Success.html");
            } else {
                response.sendRedirect("feedback.html?error=true");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("feedback.html?error=true");
        }
    }

    }

    

