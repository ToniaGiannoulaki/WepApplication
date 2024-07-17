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
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Utilities.DBUtil.connection;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

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

        ////////////////////////////////////////////////////////////////
        ////////////////////// ADD NEW SELLER //////////////////////////
        ////////////////////////////////////////////////////////////////

        if(request.getParameter("addSeller") != null)
        {
            String salt=dao.getAlphaNumericString(16);

            String username = request.getParameter("username");
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
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
                        response.sendRedirect("admin.jsp");
                    }
                }
                catch (NoSuchAlgorithmException e)
                {
                    e.printStackTrace();
                }
            } else{
                request.setAttribute("message", usernameValidation);
                request.setAttribute("user", username);
                response.sendRedirect("admin.jsp");
            }
        }

        ////////////////////////////////////////////////////////////////
        ////////////////////// ADD NEW PROGRAM /////////////////////////
        ////////////////////////////////////////////////////////////////

         else if(request.getParameter("addProgram") != null) {

            String program_name = request.getParameter("program_name");
            int charge = Integer.parseInt(request.getParameter("charge"));
            int data = Integer.parseInt(request.getParameter("data"));
            int sms = Integer.parseInt(request.getParameter("sms"));
            int minutes = Integer.parseInt(request.getParameter("minutes"));

            try{
                PreparedStatement preparedStatement1 = connection
                        .prepareStatement(
                                "INSERT INTO programs" +
                                        "(program_name, charge, data, sms, minutes) VALUES (?, ?, ?, ?, ?)");
                preparedStatement1.setString(1, program_name);
                preparedStatement1.setInt(2, charge);
                preparedStatement1.setInt(3, data);
                preparedStatement1.setInt(4, sms);
                preparedStatement1.setInt(5, minutes);

                int rowsInserted = preparedStatement1.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("A new program was inserted successfully!");
                } else {
                    System.out.println("No rows inserted.");
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
            // Redirect to a success page after insertion
            response.sendRedirect("admin.jsp");
        }

        ////////////////////////////////////////////////////////////////
        ////////////////////// CHANGE PROGRAM //////////////////////////
        ////////////////////////////////////////////////////////////////

        else if (request.getParameter("updateProgram") != null) {
            // Handle program update
            String programName = request.getParameter("programName");
            int charge = Integer.parseInt(request.getParameter("charge"));
            int data = Integer.parseInt(request.getParameter("data"));
            int sms = Integer.parseInt(request.getParameter("sms"));
            int minutes = Integer.parseInt(request.getParameter("minutes"));

            try {
                PreparedStatement preparedStatement1 = connection
                        .prepareStatement(
                                "UPDATE programs SET charge = ?, data = ?, sms = ?, minutes = ? WHERE program_name = ?");
                preparedStatement1.setInt(1, charge);
                preparedStatement1.setInt(2, data);
                preparedStatement1.setInt(3, sms);
                preparedStatement1.setInt(4, minutes);
                preparedStatement1.setString(5, programName);

                int rowsUpdated = preparedStatement1.executeUpdate();

                if (rowsUpdated > 0) {
                    response.sendRedirect("admin.jsp");
                } else {
                    response.sendRedirect("admin.jsp");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("admin.jsp");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0); //restrict caching

        response.setContentType("text/html; charset=UTF-8"); //creating dynamic page
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        ////////////////////////////////////////////////////////////////
        ////////////////////// CHANGE PROGRAM //////////////////////////
        ////////////////////////////////////////////////////////////////

        if (request.getParameter("changeProgram") != null){
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head><title>Τροποποίηση προγραμμάτων</title></head>");
            out.println("<body style=\"text-align: center; font-size: 20px\">");

            out.println("<h3>Τροποποίηση προγράμματος</h3>");
            createDynPage(response,"<br>");
            out.println("<form action='admin' method='post'>");  // Form action points to the same servlet
            out.println("<input type='hidden' name='updateProgram' value='true'>");  // Hidden input to identify update action
            out.println("<table style=\"text-align: center; margin-left: auto; margin-right: auto\" border=\"1\">");
            out.println("<tr><td>Όνομα Προγράμματος Τηλεφωνίας:</td><td><input type='text' name='programName' required></td></tr>");
            out.println("<tr><td>Χρέωση:</td><td><input type='number' name='charge'></td></tr>");
            out.println("<tr><td>Δεδομένα:</td><td><input type='number' name='data'></td></tr>");
            out.println("<tr><td>Μηνύματα:</td><td><input type='number' name='sms'></td></tr>");
            out.println("<tr><td>Λεπτά Ομιλίας:</td><td><input type='number' name='minutes'></td></tr>");
            out.println("</table>");
            out.println("<br>");
            out.println("<input type='submit' name='updateProgram' value='Τροποποίηση προγράμματος'>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    ////////////////////////////// CREATE A DYNAMIC PAGE //////////////////////////////////
    private void createDynPage(HttpServletResponse response, String message) throws IOException { //dynamic page method
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>" + message + "</title></head>");
        out.println("<body style=\"text-align: center\">");
        out.println("<p>" + message + "</p>");
        out.println("<a href=\"admin.jsp\">Επιστροφή στην αρχική σελίδα</a>");
        out.println("<br><br>");
        out.println("</body></html>");
    }

}
