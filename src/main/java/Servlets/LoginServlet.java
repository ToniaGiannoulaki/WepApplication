package Servlets;
import Dao.SystemDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    SystemDao dao = new SystemDao(); //get login validator instance

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        String username = request.getParameter("user");
        String password = request.getParameter("logpass"); //get login form parameters
        String unamevalidation = dao.loginUnameCheck(username);

        if (!unamevalidation.equals(username)) {
            request.setAttribute("message", unamevalidation);
            request.setAttribute("username", username);
            response.sendRedirect("index.jsp");
        } else
        {
            System.out.println("PASS CHECK");
            String passwordvalidation=dao.loginPasswordCheck(username, password);

            if (passwordvalidation.equals("You logged in!"))
            {
                System.out.println("You logged in!");
                String role=dao.getRole(username);
                System.out.println("ROLE ===== "+role);
                HttpSession session = request.getSession(true);
                synchronized(session)
                {
                    session.setAttribute("username", username);
                    session.setAttribute("role", role);

                    if (role.equals("seller"))
                    {
                        System.out.println("INSIDE role SELLER !");
                        request.setAttribute("username", username);
                        request.setAttribute("role", role);
                        response.sendRedirect("seller.jsp");
                    }
                    else if (role.equals("client"))
                    {
                        System.out.println("INSIDE role CLIENT !");
                        request.setAttribute("username", username);
                        request.setAttribute("role", role);
                        response.sendRedirect("client.jsp");
                    }
                    else if (role.equals("admin"))
                    {
                        System.out.println("INSIDE role ADMIN !");
                        request.setAttribute("username", username);
                        request.setAttribute("role", role);
                        response.sendRedirect("admin.jsp");
                    }
                }
            }
            else
            {
                request.setAttribute("message", passwordvalidation);
                response.sendRedirect("index.jsp");
            }
        }
    }
}

