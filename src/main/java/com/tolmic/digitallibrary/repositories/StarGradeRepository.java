package com.tolmic.digitallibrary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.tolmic.digitallibrary.entities.StarGrade;
import com.tolmic.digitallibrary.entities.embeddable.StarGradePK;

public interface StarGradeRepository extends CrudRepository<StarGrade, StarGradePK> {
    
}
