package com.tolmic.digitallibrary.controllers;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tolmic.digitallibrary.services.AuthorService;
import com.tolmic.digitallibrary.services.ImageService;


@Controller
public class ImageController {

    @Autowired
    ImageService imageService;

    @Autowired
    AuthorService authorService;

    @RequestMapping("/images/author-image")
    public ResponseEntity<?> authorImages(@RequestParam("authorId") Long authorId) throws IOException {

        // File file = new File(authorService.findById(authorId).getImageLink());

        // return imageService.getResponseEntity(file);

        return null;

    }

    @GetMapping("/images/use-inter-image/{name}")
    public ResponseEntity<?> useInterImage(@PathVariable String name) throws IOException {
        return imageService.getResponseEntity(new File("./images/use-inter-images" + name));
    }

    // @GetMapping("/favicon.ico")
    // public ResponseEntity<?> favicon() throws IOException {
    //     return imageService.getResponseEntity(new File("./images/use-inter-images/icon.png"));
    // }

}
