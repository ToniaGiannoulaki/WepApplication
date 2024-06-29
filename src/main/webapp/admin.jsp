<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Λειτουργίες Διαχειριστή</title>

    <%
        //validate login
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setHeader("Expires", "0"); // Proxies.

        if(session.getAttribute("username")==null)
        {
            response.sendRedirect("index.jsp");
        }else{
            if(!session.getAttribute("role").equals("admin"))
            {
                session.removeAttribute("username");
                session.invalidate();
                response.sendRedirect("index.jsp");
            }
        }
    %>

</head>
<body style="background-color: #C2DFFF;">
<div style="text-align: center">
    <form method="get" action="admin">
        <div style="text-align:right; size:15px;">
            Welcome, <%= session.getAttribute("username") %>
        </div>
        <p style="font-size: 30px">Λειτουργίες Πελάτη</p>
        <br><br><br>
        <input style="font-size: 20px" type="submit" name="createSeller" value="Δημιουργία πωλητή"/>
        <br><br>
        <input style="font-size: 20px" type="submit" name="createProgram" value="Δημιουργία προγράμματος"/>
        <br><br>
        <input style="font-size: 20px" type="submit" name="changeProgram" value="Αλλαγή προγράμματος"/>
        <br><br><br>
    </form>
    <form method="get" action="logout">
        <input type="submit" class="button" value="Αποσύνδεση">
    </form>
    <br>
</div>
</body>



