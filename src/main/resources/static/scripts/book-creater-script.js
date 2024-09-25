"use strict"

let name            = document.querySelector("#name");
let yearCreation1       = document.querySelector("#yearCreation1");
let yearCreation2       = document.querySelector("#yearCreation2");
let originalLanguage    = document.querySelector("#originalLanguage");
let genre               = document.querySelector("#genre");

let requiredFields = [ bookName, yearCreation1, originalLanguage, genre ];

let numberFields = [ yearCreation1, yearCreation2 ];

requiredFields.forEach((field) => {
    field.onblur = () => {
        if (field.value == "") {
            field.style["border-block-color"] = "red";
        }
    }

    field.onfocus = () => {
        field.style["border-block-color"] = "";
    }
})

numberFields.forEach((field) => {
    field.oninput = (ev) => {
        let regex = /[^0-9\.]/g;

        if (regex.test(ev.data)) {
            let t = field.value.slice(0, -1);
            field.value = t;
        }
    }
})