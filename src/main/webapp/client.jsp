<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Λειτουργίες Πελάτη</title>

    <%
        //validate login
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setHeader("Expires", "0"); // Proxies.

        if(session.getAttribute("username")==null)
        {
            response.sendRedirect("index.jsp");
        }else{
            if(!session.getAttribute("role").equals("client"))
            {
                session.removeAttribute("username");
                session.invalidate();
                response.sendRedirect("index.jsp");
            }
        }
    %>

</head>
<body style="background-color: #C2DFFF">
<div style="text-align: center;">
    <form method="get" action="client">
        <div style="text-align:right; size:15px;">
            Welcome, <%= session.getAttribute("username") %>
        </div>
        <p style="font-size: 30px">Λειτουργίες Πελάτη</p>
        <br><br><br>
        <input style="font-size: 20px" type="submit" name="showBills" value="Προβολή λογαριασμού"/>
        <br><br>
        <input style="font-size: 20px" type="submit" name="showHistory" value="Προβολή ιστορικού κλήσεων"/>
        <br><br>
    </form>
    <form method="get" action="logout">
        <input type="submit" class="button" value="Αποσύνδεση">
    </form>
</div>
</body>