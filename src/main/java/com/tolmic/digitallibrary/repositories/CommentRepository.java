package com.tolmic.digitallibrary.repositories;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tolmic.digitallibrary.entities.Comment;


public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByLooked(boolean looked);
}
