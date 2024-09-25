"use strict"

let name = document.querySelector("#name");
let surname = document.querySelector("#surname");
let birthday = document.querySelector("#birthday");
let city = document.querySelector("#city");
let login = document.querySelector("#login");

let password = document.querySelector("#password");
let passwordRepeat = document.querySelector("#password-repeat");

let buttonToRegistration = document.querySelector(".regis");
let error = document.querySelector("#error");

let arr = [name, surname, login, password, passwordRepeat, birthday, city];
for (let i = 0; i < arr.length; i++) {
    arr[i].onblur = function() {
        focus(this, false);
    }
    arr[i].onfocus = function() {
        focus(this, true);
    }
}

if (error.textContent != "") {
    error.style.color = "red";
}

buttonToRegistration.addEventListener('click', function(e) {

    if (!requiredCompleted()) {
        showNote(error, "Не все обязательные поля заполнены", "red");
        error.style.color = "red";
        e.preventDefault();

        return;
    }

    if (password.value != passwordRepeat.value) {
        showNote(error, "Вновь введённый пароль не соответствует первоначальному", "red");
        e.preventDefault();
    }

});

let focus = (obj, isFocus) => {

    if (obj.value != "" || isFocus) {
        obj.style.border = "";
        hideNote(error);
    } else {
        obj.style.border = "1px solid #FF0000";
    }
}

let requiredCompleted = () => {
    return name.value != "" && surname.value != "" && birthday.value != ""&& city.value != ""
    && login.value != "" && password.value != "" && passwordRepeat.value != "";
}

let showNote = (obj, text, color) => {
    obj.textContent = text;
    obj.style.color = color;
}

let hideNote = (obj) => {
    obj.textContent = "";
}