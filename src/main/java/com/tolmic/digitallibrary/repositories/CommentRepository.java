package com.tolmic.digitallibrary.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tolmic.digitallibrary.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByLooked(boolean looked);
}
