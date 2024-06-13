package com.tolmic.digitallibrary.services;


import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Service
public class ImageService {

    public ResponseEntity<?> getResponseEntity(File imageFile) throws IOException {

        String fileName = imageFile.getName();

        int dotIndex = fileName.lastIndexOf('.');
        String extension = (dotIndex == -1) ? "" : fileName.substring(dotIndex);

        BufferedImage bImage = ImageIO.read(imageFile);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, extension, bos);
        byte[] bytes = bos.toByteArray();

        return ResponseEntity.ok()
                .header("fileName", fileName)
                .contentType(MediaType.valueOf(extension))
                .contentLength(imageFile.length())
                .body(new InputStreamResource(new ByteArrayInputStream(bytes)));
    }
}
