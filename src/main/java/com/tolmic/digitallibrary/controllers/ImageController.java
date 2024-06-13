package com.tolmic.digitallibrary.controllers;

import com.tolmic.digitallibrary.services.AuthorService;
import com.tolmic.digitallibrary.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.*;


@Controller
public class ImageController {

    @Autowired
    ImageService imageService;

    @Autowired
    AuthorService authorService;

    @GetMapping("/images/author-images/{name}")
    public ResponseEntity<?> authorImages(@PathVariable Long authorId) throws IOException {

        File file = new File(authorService.findById(authorId).getImageLink());

        return imageService.getResponseEntity(file);
    }

    @GetMapping("/images/use-inter-image/{name}")
    public ResponseEntity<?> useInterImage(@PathVariable String name) throws IOException {
        return imageService.getResponseEntity(new File("./images/use-inter-images" + name));
    }

    @GetMapping("/favicon.ico")
    public ResponseEntity<?> favicon() throws IOException {
        return imageService.getResponseEntity(new File("./images/use-inter-images/icon.png"));
    }

}
