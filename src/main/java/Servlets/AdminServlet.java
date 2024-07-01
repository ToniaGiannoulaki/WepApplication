package Servlets;

import Dao.SystemDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Connection connection;

    public AdminServlet() {
        connection = Utilities.DBUtil.getConnection(); //get DB connection
    }
    SystemDao dao = new SystemDao(); //get login validator instance

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0); //restrict caching

        if(request.getParameter("addSeller") != null) //add client
        {
            String salt=dao.getAlphaNumericString(16);

            String username = request.getParameter("username");
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String address = request.getParameter("address");
            String afm = request.getParameter("AFM");
            String password=request.getParameter("password")+salt;
            String usernameValidation=dao.signupUsernameCheck(username); //check for duplicate username

            if (usernameValidation.equals("ok")){
                MessageDigest digest;
                try {
                    digest = MessageDigest.getInstance("SHA-1");
                    byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                    password = dao.bytesToHex(encodedHash);
                    System.out.println("pass = " + password); //set new hashed password
                    boolean success = dao.signupSeller(username, name, surname, password, salt); //add client

                    if (!success) {
                        System.out.println("Problem with user insert");
                        createDynPage(response, "Ο πωλητής δεν καταχωρήθηκε"); //debug message
                    } else {
                        response.sendRedirect("seller.jsp");
                    }
                }
                catch (NoSuchAlgorithmException e)
                {
                    e.printStackTrace();
                }
            } else{
                request.setAttribute("message", usernameValidation);
                request.setAttribute("user", username);
                response.sendRedirect("seller.jsp");
            }
        }
    }

    private void createDynPage(HttpServletResponse response, String message) throws IOException { //dynamic page method
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>" + message + "</title></head>");
        out.println("<body style=\"text-align: center\">");
        out.println("<p>" + message + "</p>");
        out.println("<a href=\"seller.jsp\">Επιστροφή στην αρχική σελίδα</a>");
        out.println("<br><br>");
        out.println("</body></html>");
    }
}
