package com.tolmic.digitallibrary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tolmic.digitallibrary.entities.Comment;
import com.tolmic.digitallibrary.repositories.CommentRepository;

@SpringBootTest
public class CommentsTests {

    @Autowired
    CommentRepository commentRepository;
    
    @Test
    void commentTest() {
        Iterable<Comment> comments = commentRepository.findAll();

        for (Comment c : comments) {
            System.out.println(c.getDate());
        }

    }
}
