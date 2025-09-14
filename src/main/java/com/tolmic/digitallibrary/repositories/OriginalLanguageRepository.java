package com.tolmic.digitallibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tolmic.digitallibrary.entities.OriginalLanguage;

public interface OriginalLanguageRepository extends JpaRepository<OriginalLanguage, Long> {
    public OriginalLanguage findByName(String name);
}
