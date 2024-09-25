package com.tolmic.digitallibrary.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tolmic.digitallibrary.entities.Book;
import com.tolmic.digitallibrary.entities.Comment;
import com.tolmic.digitallibrary.entities.CommentText;
import com.tolmic.digitallibrary.entities.User;
import com.tolmic.digitallibrary.repositories.CommentRepository;
import com.tolmic.digitallibrary.repositories.CommentTextRepository;


@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentTextRepository commentTextRepository;


    private void save(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Comment> findByLooked(boolean isLooked) {
        return commentRepository.findByLooked(isLooked);
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id).stream().toList().get(0);
    }

    public Comment createComment(Book book, User user, String text) {

        CommentText commentText = new CommentText(text);
        commentTextRepository.save(commentText);

        Comment comment = new Comment(commentText);

        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
        
        comment.setDate(java.sql.Date.valueOf(formatForDateNow.format(new Date())));
        comment.setBook(book);
        comment.setUser(user);

        save(comment);

        return comment;
    }

    public void addAnserToComment(Comment comment, Comment answer) {
        comment.addAnswer(answer);
        save(comment);
    }

    public void deleteComment(Long id) {
        Comment comment = findById(id);
        CommentText commentText = comment.getCommentText();

        commentRepository.delete(comment);
        commentTextRepository.delete(commentText);
    }

}
