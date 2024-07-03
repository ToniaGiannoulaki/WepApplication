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

import static Utilities.DBUtil.connection;

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

        if(request.getParameter("showBills") != null){
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head><title>Λογαριασμός</title> </head>");
            out.println("<body style=\"text-align: center; font-size: 20px;\"");
            createDynPage(response, "Λογαριασμός");
            out.println("<table style=\"text-align: center; margin-left: auto; margin-right: auto\"  border=\"1\">");
            out.println("<tr>");
            out.println("<th>Id</th>");
            out.println("<th>Όνομα χρήστη</th>");
            out.println("<th>Τηλέφωνο</th>");
            out.println("<th>Πρόγραμμα</th>");
            out.println("<th>Μήνας</th>");
            out.println("<th>Χρέωση</th>");
            out.println("<th>Πληρωμή</th>");
            out.println("</tr>");
            try {
                String specificUsername = request.getParameter("username");
                if (specificUsername == null || specificUsername.isEmpty()) {
                    specificUsername = (String) request.getSession().getAttribute("username");
                }
                System.out.println("Retrieved username: " + specificUsername);

                if (specificUsername == null || specificUsername.isEmpty()) {
                    out.println("<p style=\"font-size: 25px\"> Username is missing </p>");
                } else {
                    PreparedStatement preparedStatement1 = connection.prepareStatement(
                            "SELECT id, username, phone, program_name, month, charge, paid FROM bills WHERE username = ?"
                    );
                    preparedStatement1.setString(1, specificUsername); // Set the username parameter

                    ResultSet rs1 = preparedStatement1.executeQuery();
                    while (rs1.next()) { // if DB returns data - until data ends
                        int id = rs1.getInt("id");
                        String username = rs1.getString("username");
                        String phone = rs1.getString("phone");
                        String program_name = rs1.getString("program_name");
                        String month = rs1.getString("month");
                        int charge = rs1.getInt("charge");
                        boolean paid = rs1.getBoolean("paid");

                        System.out.println("Retrieved row - ID: " + id + ", Username: " + username);

                        String htmlBill = createHTMLRowPayBills(id, username, phone, program_name, month, charge, paid);
                        out.println(htmlBill);
                    }
                    rs1.close();
                    preparedStatement1.close();
                }
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

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0); //restrict caching

        if (request.getParameter("billId") != null){ //pay bill
            int billId = Integer.parseInt(request.getParameter("billId"));

            try{
                PreparedStatement preparedStatement1 = connection
                        .prepareStatement("UPDATE bills SET paid = true WHERE id = ?");
                preparedStatement1.setInt(1, billId);

                int rowsUpdated = preparedStatement1.executeUpdate();
                if (rowsUpdated > 0) {
                    request.setAttribute("showBills", "showBills");
                    doGet(request, response);
                } else {
                    createDynPage(response, "Ο λογαριασμός δεν πληρώθηκε");
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private String createHTMLRowPayBills(int id, String username, String phone, String program_name, String month, int charge, boolean paid){
        // Initialize the row variable
        String row = "<tr>" +
                "<td>" + id + "</td>" +
                "<td>" + username + "</td>" +
                "<td>" + phone + "</td>" +
                "<td>" + program_name + "</td>" +
                "<td>" + month + "</td>" +
                "<td>" + charge + "</td>" +
                "<td>";

        // Add appropriate content based on the paid status
        if (paid) {
            row += "Πληρωμένο";
        } else {
            row += "<form action='client' method='post'>" +
                    "<input type='hidden' name='billId' value='" + id + "'>" +
                    "<input type='submit' class='button' value='Πληρωμή'>" +
                    "</form>";
        }
        // Close the last cell and the row
        row += "</td></tr>";
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

    private void Bills (String resp, String req){

    }
}

