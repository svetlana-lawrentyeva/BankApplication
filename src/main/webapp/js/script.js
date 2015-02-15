function isNumeric(input){
    var result=false;;
    var RE = /^-{0,1}\d*\.{0,1}\d+$/;
    if (RE.test(input) && (input >= 0)){
        result=true;
    }
    return result;
}


function checkForm() {
    return checkName();
}

function checkName() {
    var name=$("#name").val();
    var result=true;
    var nameArr = name.split(" ");
    for(var i=0;i<nameArr.length;i++) {
        var n = nameArr[i];
        if (name.length<2) {
            $("#nameError").html("пожалуйста, укажите имя");
            return false;
        }
    }
    return result;
}

function checkPhone(){
    var result=true;
    var phone = ("#phone").val();
    var RE = /\d{3}-\d{7}/;
    if (!RE.test(phone)){
        $("#phoneError").html("пожалуйста, укажите телефон");
        result=false;
    }
    return result;
}

function checkCity() {
    var city=$("#city").val();
    var result=true;
    if (city.length<2) {
        $("#cityError").html("пожалуйста, укажите город");
        result = false;
    }
    return result;
}

function checkEmail() {
    var email=$("#email").val();
    var result=true;
    if(email == ""){
        $("#emailError").html("введите email");
        result=false;
    } else if (email.match(".+@.+\.[a-zA-Z]{2,3}") == null) {
        $("#emailError").html("неверный формат email");
        result = false;
    }
    return result;
}

function checkOverdraft() {
    var overdraft=$("#overdraft").val();
    var result=true;
    if(!isNumeric(overdraft)){
        $("#overdraftError").html("введите число");
        result = false;
    }
    if (overdraft<0) {
        $("#overdraftError").html("введите сумму");
        result = false;
    }
    return result;
}

function checkBalance() {
    var balance=$("#balance").val();
    var result=true;
    if(!isNumeric(balance)){
        $("#balanceError").html("введите число");
        result = false;
    }
    if (balance<0) {
        $("#balanceError").html("введите сумму");
        result = false;
    }
    return result;
}

function checkClientForm() {
    if(checkName() & checkCity() & checkEmail() & checkOverdraft() & checkBalance() & checkPhone()){
        return true;
    } else {
        return false;
    }
}

function checkFindClientForm() {
    if(checkName() & checkCity()){
        return true;
    } else {
        return false;
    }
}