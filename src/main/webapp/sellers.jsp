<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Λειτουργίες Πωλητή</title>

    <%
        //validate login
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setHeader("Expires", "0"); // Proxies.

        if(session.getAttribute("username")==null)
        {
            response.sendRedirect("index.jsp");
        }else{
            if(!session.getAttribute("role").equals("seller"))
            {
                session.removeAttribute("username");
                session.invalidate();
                response.sendRedirect("index.jsp");
            }
        }
    %>

</head>
<body >
<div style="text-align: center">
    <form method="get" action="sellerservlet">
        <div style="text-align:right; size:15px;">
            Welcome, <%= session.getAttribute("username") %>
        </div>
        <p style="font-size: 30px">Λειτουργίες Πωλητή</p>
        <br><br><br>
        <input style="font-size: 20px" type="submit" name="showOffers" value="Προβολή όλων των διαθέσιμων προγραμμάτων/πακέτων τηλεφωνίας."/>
        <br><br>
        <input style="font-size: 20px" type="submit" name="addClient" value="Εισαγωγή νέου πελάτη."/>
        <br><br>
        <input style="font-size: 20px" type="submit" name="clientToOffer" value="Αντιστοίχηση πελάτη σε πρόγραμμα τηλεφωνίας."/>
        <br><br><br>
    </form>
    <br>
    <form method="get" action="logout">
        <input type="submit" class="button" value="Log Out">
    </form>
</div>
</body>
</html>
