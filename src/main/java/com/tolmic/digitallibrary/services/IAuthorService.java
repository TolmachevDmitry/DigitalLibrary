package com.tolmic.digitallibrary.services;

import com.tolmic.digitallibrary.entities.Author;
import com.tolmic.digitallibrary.services.implementations.IMainService;

public interface IAuthorService extends IMainService<Author> {

    public void deleteById(Long id);

}
