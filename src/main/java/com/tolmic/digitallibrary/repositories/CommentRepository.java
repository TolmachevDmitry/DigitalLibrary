package com.tolmic.digitallibrary.repositories;


import com.tolmic.digitallibrary.entities.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByLooked(boolean looked);
}
