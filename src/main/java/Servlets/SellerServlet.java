package Servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/sellerservlet")
public class SellerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Connection connection;

    public SellerServlet() {
        connection = Utilities.DBUtil.getConnection(); //get DB connection
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0); //restrict caching

        if (request.getParameter("showOffers") != null) //show offers button actions
        {
            response.setContentType("text/html; charset=UTF-8"); //creating dynamic page
            response.setCharacterEncoding("UTF-8");
            request.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head><title>Προγράμματα</title> </head>");
            out.println("<body style=\"text-align: center; font-size: 20px\"");
            createDynPage(response, "Προγράμματα");
            try { //creating table in dyn page
                out.println("<table style=\"text-align: center; margin-left: auto; margin-right: auto\"  border=\"1\">");
                out.println("<tr>");
                out.println("<th>Όνομα Προγράμματος Τηλεφωνίας</th>");
                out.println("<th>Χρέωση</th>");
                out.println("<th>Δεδομένα</th>");
                out.println("<th>Μηνύματα</th>");
                out.println("<th>Λεπτά Ομιλίας</th>");
                out.println("</tr>");

                PreparedStatement preparedStatement1 = connection
                        .prepareStatement("SELECT programName, charge, data, sms, minutes FROM Program"); //find program info
                ResultSet rs1 = preparedStatement1.executeQuery();
                while (rs1.next()) { //if DB returns data - until data ends
                    String programName = rs1.getString("programName");
                    int charge = rs1.getInt("charge");
                    int data = rs1.getInt("data");
                    int sms = rs1.getInt("sms");
                    int minutes = rs1.getInt("minutes");

                    String htmlRow = createHTMLRow(programName, charge, data, sms, minutes); // html table with all the data
                    out.println(htmlRow);
                }
                rs1.close();
            } catch (SQLException e) {
                e.printStackTrace();
                out.println("<p style=\"font-size: 25px\"> Something went wrong </p>"); //debug message
            }
        }
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0); //restrict caching

        try {
            String subject = request.getParameter("subject");
            int registrationNumber = Integer.parseInt(request.getParameter("registrationNumber"));
            int grade = Integer.parseInt(request.getParameter("grade"));
            try {
                PreparedStatement preparedStatement2 = connection
                        .prepareStatement("UPDATE course_has_students SET Grade =? WHERE registrationNumber=? AND courseName=?"); //update the grade
                preparedStatement2.setInt(1, grade);
                preparedStatement2.setInt(2, registrationNumber);
                preparedStatement2.setString(3, subject);
                if (preparedStatement2.executeUpdate() != 1) {
                    System.out.println("Problem with grade update");
                    createDynPage(response, "Ο βαθμός δεν καταχωρήθηκε"); //debug message
                } else {
                    request.setAttribute("subject", subject);
                    doGet(request, response);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                createDynPage(response, "Ο βαθμός δεν καταχωρήθηκε"); //debug message
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
            createDynPage(response, "Παρακαλώ δώστε έγκυρα στοιχεία"); //debug message
        }
    }


    private String createHTMLRow(String programName, int charge, int data, int sms, int minutes) //create table for first button
    {
        String row = "<tr>";
        row  += "<td>" + programName + "</td>";
        row  += "<td>" + charge + "</td>";
        row  += "<td>" + data + "</td>";
        row  += "<td>" + sms + "</td>";
        row  += "<td>" + minutes + "</td>";
        row +="</tr>";
        return row;
    }

    private void createDynPage(HttpServletResponse response, String message) throws IOException { //dynamic page method
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>" + message + "</title></head>");
        out.println("<body style=\"text-align: center\">");
        out.println("<p>" + message + "</p>");
        out.println("<a href=\"professor.jsp\">Επιστροφή στην αρχική σελίδα</a>");
        out.println("<br><br>");
        out.println("</body></html>");
    }
}
