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

@WebServlet("/seller")
public class SellerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Connection connection;

    public SellerServlet() {
        connection = Utilities.DBUtil.getConnection(); //get DB connection
    }

    SystemDao dao = new SystemDao(); //get login validator instance

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0); //restrict caching

        response.setContentType("text/html; charset=UTF-8"); //creating dynamic page
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        if (request.getParameter("showOffers") != null) //show offers button actions
        {
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
        }else if(request.getParameter("clientToOffer") != null) {
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head><title>Πελάτες</title> </head>");
            out.println("<body style=\"text-align: center; font-size: 20px\"");
            createDynPage(response, "Πελάτες");
            try { //creating table in dyn page
                out.println("<table style=\"text-align: center; margin-left: auto; margin-right: auto\"  border=\"1\">");
                out.println("<tr>");
                out.println("<th>Όνομα Πελάτη</th>");
                out.println("<th>ΑΦΜ</th>");
                out.println("<th>Τηλέφωνο</th>");
                out.println("<th>Username</th>");
                out.println("<th>Πρόγραμμα</th>");
                out.println("</tr>");

                PreparedStatement preparedStatement1 = connection
                        .prepareStatement("SELECT username, name, afm, phone_number, program_name FROM users INNER JOIN clients ON users.username = clients.user_username"); //find program info
                ResultSet rs1 = preparedStatement1.executeQuery();
                while (rs1.next()) { //if DB returns data - until data ends
                    String name = rs1.getString("name");
                    String afm = rs1.getString("afm");
                    String phone = rs1.getString("phone_number");
                    String username = rs1.getString("username");
                    String programName = rs1.getString("program_name");

                    String htmlRow = createHTMLRowClients(name, afm, phone, username, programName); // html table with all the data
                    out.println(htmlRow);

                    out.println("<br><br>");
                    out.println("<form name=\"clientToProgram\" action=\"seller\" method=\"post\">");
                    out.println("<div class=\"group\">");
                    out.println("<label for=\"username\" class=\"label\">Username</label>");
                    out.println("<input id=\"username\" type=\"text\" name=\"username\" class=\"input\">");
                    out.println("</div>");
                    out.println("<br><br>");
                    out.println("<div class=\"group\">");
                    out.println("<label for=\"programName\" class=\"label\">Program Name</label>");
                    out.println("<input id=\"programName\" type=\"text\" name=\"programName\" class=\"input\">");
                    out.println("</div>");
                    out.println("<br>");
                    out.println("<div>");
                    out.println("<input style=\"font-size: 20px\" type=\"submit\" name=\"assignProgram\" value=\"Αντιστοίχηση\"/>");
                    out.println("</div>");
                    out.println("<br>");
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

        if(request.getParameter("addClient") != null) //add client
        {
            String username = request.getParameter("username");
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String type = request.getParameter("type");
            String address = request.getParameter("address");
            String afm = request.getParameter("AFM");
            String phone = request.getParameter("phoneNumber");
            String password = request.getParameter("password");
            String usernameValidation=dao.signupUsernameCheck(username); //check for duplicate username

            if (usernameValidation.equals("ok")){
                try {
                    PreparedStatement preparedStatement2 = connection
                            .prepareStatement("INSERT INTO users (username, name, surname, type, password) " +
                                    "VALUES (?, ?, ?, ?, ?)"); //insert user
                    preparedStatement2.setString(1, username);
                    preparedStatement2.setString(2, name);
                    preparedStatement2.setString(3, surname);
                    preparedStatement2.setString(4, type);
                    preparedStatement2.setString(5, password);
                    if (preparedStatement2.executeUpdate() != 1) {
                        System.out.println("Problem with user insert");
                        createDynPage(response, "Ο χρήστης δεν καταχωρήθηκε"); //debug message
                    } else {
                        PreparedStatement preparedStatement3 = connection
                                .prepareStatement("INSERT INTO clients (user_username, address, phone_number, afm) " +
                                        "VALUES (?, ?, ?, ?)"); //insert client
                        preparedStatement3.setString(1, username);
                        preparedStatement3.setString(2, address);
                        preparedStatement3.setString(3, phone);
                        preparedStatement3.setString(4, afm);

                        if (preparedStatement3.executeUpdate() != 1) {
                            PreparedStatement preparedStatement4 = connection
                                    .prepareStatement("DELETE FROM users WHERE username=?"); //delete user
                            preparedStatement4.setString(1, username);
                            preparedStatement3.executeUpdate();
                            System.out.println("Problem with client insert");
                            createDynPage(response, "Ο πελάτης δεν καταχωρήθηκε"); //debug message
                        }else{
                            response.sendRedirect("seller.jsp");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    createDynPage(response, "Ο χρήστης δεν καταχωρήθηκε"); //debug message
                }
            } else{
                request.setAttribute("message", usernameValidation);
                request.setAttribute("user", username);
                response.sendRedirect("seller.jsp");
            }
        }else if(request.getParameter("assignProgram") != null){
            String username = request.getParameter("username");
            String programName = request.getParameter("programName");

            try {
                PreparedStatement preparedStatement1 = connection
                        .prepareStatement("UPDATE clients SET program_name=? WHERE user_username=?"); //update client
                preparedStatement1.setString(1, programName);
                preparedStatement1.setString(2, username);
                if (preparedStatement1.executeUpdate() != 1) {
                    System.out.println("Problem with client update");
                    createDynPage(response, "Η αντιστοίχηση δεν ήταν επιτυχής"); //debug message
                } else {
                    response.sendRedirect("seller.jsp");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                createDynPage(response, "Η αντιστοίχηση δεν ήταν επιτυχής"); //debug message
            }
        }
    }


    private String createHTMLRowClients(String name, String afm, String phoneNumber, String username, String programName) //create table for clients
    {
        String row = "<tr>";
        row  += "<td>" + name + "</td>";
        row  += "<td>" + afm + "</td>";
        row  += "<td>" + phoneNumber + "</td>";
        row  += "<td>" + username + "</td>";
        row  += "<td>" + programName + "</td>";
        row +="</tr>";
        return row;
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
