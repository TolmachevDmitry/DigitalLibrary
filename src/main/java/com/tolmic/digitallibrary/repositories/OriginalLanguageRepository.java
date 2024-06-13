package com.tolmic.digitallibrary.repositories;

import com.tolmic.digitallibrary.entities.Book;
import com.tolmic.digitallibrary.entities.OriginalLanguage;
import org.springframework.data.repository.CrudRepository;

public interface OriginalLanguageRepository extends CrudRepository<OriginalLanguage, Long> {
    public OriginalLanguage findByName(String name);
}
