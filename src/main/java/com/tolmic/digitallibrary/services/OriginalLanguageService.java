package com.tolmic.digitallibrary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tolmic.digitallibrary.entities.OriginalLanguage;
import com.tolmic.digitallibrary.repositories.OriginalLanguageRepository;


@Service
public class OriginalLanguageService {

    @Autowired
    private OriginalLanguageRepository originalLanguageRepository;

    private void saveMain(OriginalLanguage originalLanguage) {
        originalLanguageRepository.save(originalLanguage);
    }

    public OriginalLanguage saveAndGet(String name) {
        OriginalLanguage originalLanguage = new OriginalLanguage();
        originalLanguage.setName(name);
        
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
