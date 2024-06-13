package com.tolmic.digitallibrary.services;

import com.tolmic.digitallibrary.entities.Book;
import com.tolmic.digitallibrary.entities.OriginalLanguage;
import com.tolmic.digitallibrary.repositories.OriginalLanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OriginalLanguageService {

    @Autowired
    private OriginalLanguageRepository originalLanguageRepository;

    private void saveMain(OriginalLanguage originalLanguage) {
        originalLanguageRepository.save(originalLanguage);
    }

    public OriginalLanguage saveAndGet(String name) {
        OriginalLanguage originalLanguage = new OriginalLanguage(name);
        saveMain(originalLanguage);

        return originalLanguage;
    }

    public OriginalLanguage findByName(String name) {
        return originalLanguageRepository.findByName(name);
    }

    public Iterable<OriginalLanguage> findAll() {
        return originalLanguageRepository.findAll();
    }


}
