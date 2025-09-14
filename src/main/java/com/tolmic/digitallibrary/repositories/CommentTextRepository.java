package com.tolmic.digitallibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tolmic.digitallibrary.entities.CommentText;

public interface CommentTextRepository extends JpaRepository<CommentText, Long> {
    
}
