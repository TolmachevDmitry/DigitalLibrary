package com.tolmic.digitallibrary.repositories;


import org.springframework.data.repository.CrudRepository;

import com.tolmic.digitallibrary.entities.CommentText;


public interface CommentTextRepository extends CrudRepository<CommentText, Long> {
    
}
