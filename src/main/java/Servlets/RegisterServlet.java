package Servlets;

import Dao.SystemDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.nio.charset.StandardCharsets;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    SystemDao dao = new SystemDao(); //get login validator instance

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0); //restrict caching

        String salt=dao.getAlphaNumericString(16);
        String username=request.getParameter("newusername");
        String name=request.getParameter("newname");
        String surname=request.getParameter("newsurname");
        String password=request.getParameter("newpassword1")+salt;

        String usernamevalidation=dao.signupUsernameCheck(username); //check for duplicate username

        if (usernamevalidation.equals("ok")) {
            MessageDigest digest;
            try {
                digest = MessageDigest.getInstance("SHA-1");
                byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                password=dao.bytesToHex(encodedhash);
                System.out.println("pass = "+ password); //set new hashed password
                HttpSession session = request.getSession();
                boolean success = dao.signupAdmin(username, name, surname, password, salt); //add user

                if (success) {
                    synchronized (session) {
                        session.setAttribute("username", username);
                        session.setAttribute("role", "admin");
                        request.setAttribute("username", username);
                        request.setAttribute("role", "admin");
                        response.sendRedirect("admin.jsp");
                    }
                } else
                {
                    System.out.println("Something went wrong");
                    response.sendRedirect("index.jsp");
                }
            } catch (NoSuchAlgorithmException e){
                e.printStackTrace();
            }
        } else {
            request.setAttribute("message", usernamevalidation);
            request.setAttribute("user", username);
            response.sendRedirect("index.jsp");
        }
    }
}
