package com.tolmic.digitallibrary;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class HelperMethods {

    public boolean moreOrEqual(double a, double b) {
        return a >= b;
    }

    public boolean lessOrEqual(double a, double b) {
        return a <= b;
    }

    public MultipartFile turnFileToMultipartFile(File file) {

        String fileName = file.getName();

        Path path = Paths.get(file.getAbsolutePath() + "/" + fileName);

        byte[] content = null;
        try (FileInputStream fis = new FileInputStream(path.toString())) {
            content = new byte[fis.available()];
            fis.read(content);
        } catch (IOException e) {
            e.getStackTrace();
        }
        
        return new MockMultipartFile("file", fileName, "text/plain", content);
    }

}
