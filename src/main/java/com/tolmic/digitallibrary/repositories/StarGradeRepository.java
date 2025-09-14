package com.tolmic.digitallibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tolmic.digitallibrary.entities.StarGrade;
import com.tolmic.digitallibrary.entities.embeddable.StarGradePK;

public interface StarGradeRepository extends JpaRepository<StarGrade, StarGradePK> {
    
}
