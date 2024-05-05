function validateSignInForm() {
    var var1 = document.forms["SignIn"]["user"].value;
    var var2 = document.forms["SignIn"]["logpass"].value;
    if(var1 === "" || var2 === ""){
        alert("Παρακαλώ ελέγξτε τα στοιχεία σας");
        return false;
    }
    else
    {
        return true;
    }
}
