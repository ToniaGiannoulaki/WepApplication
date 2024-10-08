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
        <p style="font-size: 30px">Λειτουργίες Διαχειριστή</p>
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
    <br><br>
    <div class="seller-form">
        <h3>Δημιουργία Προγράμματος</h3>
        <form action="admin" method="post">
            <div class="sign-in-htm">
                <div class="group">
                    <label for="program_name" class="label">Πρόγραμμα</label>
                    <input id="program_name" type="text" name="program_name" class="input">
                </div>
                <br>
                <div class="group">
                    <label for="charge" class="label">Χρέωση</label>
                    <input id="charge" type="text" name="charge" class="input">
                </div>
                <br>
                <div class="group">
                    <label for="data" class="label">Δεδομένα</label>
                    <input id="data" type="text" name="data" class="input">
                </div>
                <br>
                <div class="group">
                    <label for="sms" class="label">Μηνύματα</label>
                    <input id="sms" type="text" name="sms" class="input">
                </div>
                <br>
                <div class="group">
                    <label for="minutes" class="label">Λεπτά Ομιλίας</label>
                    <input id="minutes" type="text" name="minutes" class="input">
                </div>
                <br>
                <div class="group">
                    <input type="submit" name="addProgram" class="button" value="Δημιουργία νέου προγράμματος">
                </div>
            </div>
        </form>
    </div>
    <br>
    <form method="get" action="logout">
        <input type="submit" class="button" value="Αποσύνδεση">
    </form>
    <br>
</div>
</body>



