package com.tolmic.digitallibrary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.tolmic.digitallibrary.entities.OriginalLanguage;

public interface OriginalLanguageRepository extends CrudRepository<OriginalLanguage, Long> {
    public OriginalLanguage findByName(String name);
}
