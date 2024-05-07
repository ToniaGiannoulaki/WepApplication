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
    <form method="get" action="seller">
        <div style="text-align:right; size:15px;">
            Welcome, <%= session.getAttribute("username") %>
        </div>
        <p style="font-size: 30px">Λειτουργίες Πωλητή</p>
        <br><br><br>
        <input style="font-size: 20px" type="submit" name="showOffers" value="Προβολή όλων των διαθέσιμων προγραμμάτων/πακέτων τηλεφωνίας."/>
        <br><br>
        <input style="font-size: 20px" type="submit" name="clientToOffer" value="Αντιστοίχηση πελάτη σε πρόγραμμα τηλεφωνίας."/>
        <br><br><br>
    </form>
    <br>
    <div class="client-form">
        <form action="seller" method="post">
            <div class="sign-in-htm">
                <div class="group">
                    <label for="username" class="label">Username</label>
                    <input id="username" type="text" name="username" class="input">
                </div>
                <br>
                <div class="group">
                    <label for="name" class="label">Name</label>
                    <input id="name" type="text" name="name" class="input">
                </div>
                <br>
                <div class="group">
                    <label for="surname" class="label">Surname</label>
                    <input id="surname" type="text" name="surname" class="input">
                </div>
                <br>
                <div class="group">
                    <label for="type" class="label">Type</label>
                    <input id="type" type="text" name="type" class="input">
                </div>
                <br>
                <div class="group">
                    <label for="address" class="label">Address</label>
                    <input id="address" type="text" name="address" class="input">
                </div>
                <br>
                <div class="group">
                    <label for="AFM" class="label">AFM</label>
                    <input id="AFM" type="text" name="AFM" class="input">
                </div>
                <br>
                <div class="group">
                    <label for="phoneNumber" class="label">Phone Number</label>
                    <input id="phoneNumber" type="text" name="phoneNumber" class="input">
                </div>
                <br>
                <div class="group">
                    <label for="password" class="label">Password</label>
                    <input id="password" type="password" name="password" class="input" data-type="password">
                </div>
                <br>
                <div class="group">
                    <input type="submit" name="addClient" class="button" value="Εισαγωγή νέου πελάτη">
                </div>
            </div>
        </form>
    </div>
    <br>
    <form method="get" action="logout">
        <input type="submit" class="button" value="Log Out">
    </form>
</div>
</body>
</html>
