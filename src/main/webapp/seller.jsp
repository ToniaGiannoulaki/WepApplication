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
<<body style="background-color: #C2DFFF" >
<div style="text-align: center;">
    <form method="get" action="seller">
        <div style="text-align:right; size:15px;">
            Welcome, <%= session.getAttribute("username") %>
        </div>
        <p style="font-size: 30px">Λειτουργίες Πωλητή</p>
        <br><br>
        <input style="font-size: 20px" type="submit" name="showOffers" value="Προβολή όλων των διαθέσιμων προγραμμάτων/πακέτων τηλεφωνίας."/>
        <br><br>
        <input style="font-size: 20px" type="submit" name="clientToOffer" value="Αντιστοίχηση πελάτη σε πρόγραμμα τηλεφωνίας."/>
        <br><br>
    </form>
    <br>
    <div class="client-form">
        <h3>Έγγραφή νέου πελάτη</h3>
        <form action="seller" method="post">
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
                    <input type="submit" name="addClient" class="button" value="Εισαγωγή νέου πελάτη">
                </div>
            </div>
        </form>
    </div>
    <br>


    <br><br>
    <div class="form-container">
        <h3>Έκδοση Λογαριασμού πελάτη</h3>
        <form action="seller" method="post">
            <br>
            <div class="group">
                <label for="clientUsername" class="label">Όνομα χρήστη</label>
                <input id="clientUsername" type="text" name="clientUsername" class="input">
            </div>
            <br>
            <div class="group">
                <label for="phoneNum" class="label">Τηλέφωνο</label>
                <input id="phoneNum" type="text" name="phoneNum" class="input">
            </div>
            <br>
            <div class="group">
                <label for="programName" class="label">Όνομα Προγράμματος</label>
                <input id="programName" type="text" name="programName" class="input">
            </div>
            <div class="group">
                <label for="month" class="label">Μήνας</label>
                <input id="month" type="text" name="month" class="input">
            </div>
            <br>
            <div class="group">
                <label for="charge" class="label">Χρέωση</label>
                <input id="charge" type="text" name="charge" class="input">
            </div>
            <br>
            <div class="group">
                <label for="paid" class="label">Πληρωμένο</label>
                <select id="paid" name="paid" class="input">
                    <option value="Yes">Ναι</option>
                    <option value="No">Όχι</option>
                </select>
            </div>
            <br>
            <div class="group">
                <input type="submit" name="submitBill" class="button" value="Έκδοση λογαριασμού">
            </div>
            <br><br>
        </form>
    </div>
    <form method="get" action="logout">
        <input type="submit" class="button" value="Αποσύνδεση">
    </form>
</div>
</body>
</html>
