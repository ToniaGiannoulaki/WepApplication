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

    <div class="seller-form">
        <h3>Έγγραφή νέου πωλητή</h3>
        <form action="admin" method="post">
            <div class="sign-in-htm">
                <div class="group">
                    <label for="username" class="label">Όνομα χρήστη</label>
                    <input id="username" type="text" name="username" class="input">
                </div>
                <br>
                <div class="group">
                    <label for="name" class="label">Όνομα</label>
                    <input id="name" type="text" name="name" class="input">
                </div>
                <br>
                <div class="group">
                    <label for="surname" class="label">Επίθετο</label>
                    <input id="surname" type="text" name="surname" class="input">
                </div>
                <br>
                <div class="group">
                    <label for="type" class="label">Τύπος</label>
                    <input id="type" type="text" name="type" class="input">
                </div>
                <br>
                <div class="group">
                    <label for="address" class="label">Διεύθυνση</label>
                    <input id="address" type="text" name="address" class="input">
                </div>
                <br>
                <div class="group">
                    <label for="AFM" class="label">ΑΦΜ</label>
                    <input id="AFM" type="text" name="AFM" class="input">
                </div>
                <br>
                <div class="group">
                    <label for="phoneNumber" class="label">Αριθμός Κινητού</label>
                    <input id="phoneNumber" type="text" name="phoneNumber" class="input">
                </div>
                <br>
                <div class="group">
                    <label for="password" class="label">Κωδικός Πρόσβασης</label>
                    <input id="password" type="password" name="password" class="input" data-type="password">
                </div>
                <br>
                <div class="group">
                    <input type="submit" name="addSeller" class="button" value="Εισαγωγή νέου πωλητή">
                </div>
            </div>
        </form>
    </div>

    <form method="get" action="logout">
        <input type="submit" class="button" value="Αποσύνδεση">
    </form>
    <br>
</div>
</body>



