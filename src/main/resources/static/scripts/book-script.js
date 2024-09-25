"use strict"

// let request = new XMLHttpRequest();

let commentField = document.querySelector('#comment-field');

let commentText = document.querySelector('#comment-text');
let createBookCommentButton = document.querySelector('#create-book-comment-button');

let stars = Array.from(document.getElementsByClassName('star-icon'));

let starChecks = Array.from(document.getElementsByClassName('star-check'));

let commentDeleteForm = document.querySelector('.comment-delete-form');

let lastCheckNumber = -1;

const sendStarGrade = (numberGrade, bookId, userId) => {

    const xhr = new XMLHttpRequest();

    const url = "/add_grade?userId=" + userId + "&bookId=" + bookId + "&numberGrade=" + numberGrade;

    xhr.open("POST", url)
    xhr.send();
}

stars.map((star, i) => {
    star.addEventListener('mouseover', ev => {
        paintToGolden(i);
    });

    star.addEventListener('mouseout', ev => {
        paintToGolden(lastCheckNumber);
    })

    star.addEventListener('click', ev => {
        lastCheckNumber = i;
    })
})


const paintToGolden = (id) => {
    paintTo(id, "yellow", "grey");
}

const paintTo = (id, color, colorForOther) => {
    stars.map((star, i) => {
        star.style["fill"] = i <= id ? color : colorForOther;
    })
}


if (commentDeleteForm != null) {
    commentDeleteForm.addEventListener('submit', (ev) => {
        ev.preventDefault();
    
        let formData = new FormData(commentDeleteForm);
    
        fetch('/delete_comment', {
            method: 'DELETE',
            body: formData
        }).then(response => response.json())
          .then(data => console.log(data))
          .catch((error) => console.error('Error:', error));
    });
}
