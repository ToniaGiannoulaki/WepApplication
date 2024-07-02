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
            out.println("<html>");
            out.println("<head><title>Λογαριασμός</title> </head>");
            out.println("<body style=\"text-align: center; font-size: 20px;\"");
            createDynPage(response, "Λογαριασμός");
            out.println("<table style=\"text-align: center; margin-left: auto; margin-right: auto\"  border=\"1\">");
            out.println("<tr>");
            out.println("<th>Λογαριασμός</th>");
            out.println("<th>Χρέωση</th>");
            out.println("<th>Τηλέφωνο</th>");
            out.println("<th>Μήνας</th>");
            out.println("<th>Πληρωμένο</th>");
            out.println("</tr>");
            PreparedStatement preparedStatement1 = null; //find program info
            try {
                preparedStatement1 = connection
                        .prepareStatement("SELECT username, phone, program_name, month, charge, paid FROM bills");
                ResultSet rs1 = preparedStatement1.executeQuery();
                while (rs1.next()) { //if DB returns data - until data ends
                    String username = rs1.getString("username");
                    String phone = rs1.getString("phone");
                    String program_name = rs1.getString("program name");
                    String month = rs1.getString("month");
                    String charge = rs1.getString("charge");
                    boolean paid = rs1.getBoolean("paid");

                    String htmlRow = createHTMLRowBills(username, phone, program_name, month, charge, paid); // html table with all the data
                    out.println(htmlRow);
                }
                rs1.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        } else if(request.getParameter("showHistory") != null){
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head><title>Ιστορικό κλήσεων</title> </head>");
            out.println("<body style=\"text-align: center; font-size: 20px;\"");
            createDynPage(response, "Ιστορικό κλήσεων");
            out.println("<table style=\"text-align: center; margin-left: auto; margin-right: auto\"  border=\"1\">");
            out.println("<tr>");
            out.println("<th></th>");
            out.println("<th>Έναρξη κλήσης</th>");
            out.println("<th>Τερματισμός κλήσης</th>");
            out.println("<th>Διάρκεια κλήσης</th>");
            out.println("<th>Ημερομηνία</th>");
            out.println("</tr>");
            PreparedStatement preparedStatement1 = null; //find program info
            try {
                preparedStatement1 = connection
                        .prepareStatement("SELECT startTime, endTime, duration, date FROM calls");
                ResultSet rs1 = preparedStatement1.executeQuery();
                while (rs1.next()) { //if DB returns data - until data ends
                    String startTime = rs1.getString("startTime");
                    String endTime = rs1.getString("endTime");
                    String duration = rs1.getString("duration");
                    String date = rs1.getString("date");

                    String htmlRow = createHTMLRowCalls(startTime,endTime, duration, date); // html table with all the data
                    out.println(htmlRow);
                }
                rs1.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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

    private String createHTMLRowBills(String username, String phone, String program_name, String month, String charge, boolean paid){
        String row = "<tr>";
        row  += "<td>" + username + "</td>";
        row  += "<td>" + phone + "</td>";
        row  += "<td>" + program_name + "</td>";
        row  += "<td>" + month + "</td>";
        row  += "<td>" + charge + "</td>";
        row +="</tr>";
        if (paid) {
            row += "Paid";
        } else {
            row += "<button onclick=\"confirmPayment('" + username + "','" + phone + "','" + month + "')\">Pay</button>";
        }
        row += "</td>";
        row += "</tr>";
        return row;
    }

    private String createHTMLRowCalls(String startTime, String endTime, String duration, String date) //create table for programs
    {
        String row = "<tr>";
        row  += "<td>" + startTime + "</td>";
        row  += "<td>" + endTime + "</td>";
        row  += "<td>" + duration + "</td>";
        row  += "<td>" + date + "</td>";
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
        out.println("<a href=\"client.jsp\">Επιστροφή στην αρχική σελίδα</a>");
        out.println("<br><br>");
        out.println("</body></html>");
    }

}

