"use strict"

let checkboxAuthors = document.getElementById("checkbox-authors");
let checkboxBooks = document.getElementById("checkbox-books");

let formStatistics = document.getElementById("form-create");
let createButton = document.getElementById("create-statistics-button");

let nameAction = document.getElementById("action-name");

let errorText = document.getElementById("statistics-error");

checkboxAuthors.addEventListener('click', function(e) {
    if (this.checked) {
        checkboxBooks.checked = false;
        nameAction.value = "authors";
        errorText.textContent = "";
    } else {
        nameAction.value = "";
    }
});

checkboxBooks.addEventListener('click', function(e) {
    if (this.checked) {
        checkboxAuthors.checked = false;
        nameAction.value = "books";
        errorText.textContent = "";
    } else {
        nameAction.value = "";
    }
});

createButton.addEventListener('click', function(e) {
    if (nameAction.value == "") {
        e.preventDefault();

        errorText.textContent = "Не выбран тип выбора";
    }
});
