package Servlets;
import Dao.SystemDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/client")
public class ClientServlet extends HttpServlet{

    private Connection connection;

    public ClientServlet() {
        connection = Utilities.DBUtil.getConnection(); //get DB connection
    }

    SystemDao dao = new SystemDao(); //get login validator instance

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0); //restrict caching

        response.setContentType("text/html; charset=UTF-8"); //creating dynamic page
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        if (request.getParameter("showBill") != null){
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head><title>Λογαριασμός</title> </head>");
            out.println("<body style=\"text-align: center; font-size: 20px\"");
            createDynPage(response, "Λογαριασμός");
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
                        .prepareStatement("SELECT program_name, charge, data, sms, minutes FROM programs"); //find program info
                ResultSet rs1 = preparedStatement1.executeQuery();
                while (rs1.next()) { //if DB returns data - until data ends
                    String programName = rs1.getString("program_name");
                    int charge = rs1.getInt("charge");
                    int data = rs1.getInt("data");
                    int sms = rs1.getInt("sms");
                    int minutes = rs1.getInt("minutes");

                    String htmlRow = createHTMLRowPrograms(programName, charge, data, sms, minutes); // html table with all the data
                    out.println(htmlRow);
                }
                rs1.close();
            } catch (SQLException e) {
                e.printStackTrace();
                out.println("<p style=\"font-size: 25px\"> Something went wrong </p>"); //debug message
            }

        } else if(request.getParameter("payBill") != null){
            PrintWriter out = response.getWriter();
        } else if(request.getParameter("showHistory") != null){
            PrintWriter out = response.getWriter();
        }
    }

    private String createHTMLRowPrograms(String programName, int charge, int data, int sms, int minutes) //create table for programs
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
        out.println("<a href=\"seller.jsp\">Επιστροφή στην αρχική σελίδα</a>");
        out.println("<br><br>");
        out.println("</body></html>");
    }

}

