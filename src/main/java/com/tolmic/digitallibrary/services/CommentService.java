package com.tolmic.digitallibrary.services;

import com.tolmic.digitallibrary.entities.Comment;
import com.tolmic.digitallibrary.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public List<Comment> findByLooked(boolean isLooked) {
        return commentRepository.findByLooked(isLooked);
    }

}
