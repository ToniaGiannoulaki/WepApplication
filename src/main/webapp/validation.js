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

function validateSignUpForm() {
    var var1 = document.forms["SignUp"]["newusername"].value;
    var var2 = document.forms["SignUp"]["newname"].value;
    var var3 = document.forms["SignUp"]["newsurname"].value;
    var var4 = document.forms["SignUp"]["newpassword1"].value;
    var var5 = document.forms["SignUp"]["newpassword2"].value;
    var var6 = document.forms["SignUp"]["role"].value;
    if (var1 === "" || var2 === "" || var3 === "" || var4 === "" || var5 === "" || var6 === "") {
        alert("Παρακαλώ ελέγξτε τα στοιχεία σας");
        return false;
    } else {
        return true;
    }
}
