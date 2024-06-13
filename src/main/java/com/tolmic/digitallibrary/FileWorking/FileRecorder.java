package com.tolmic.digitallibrary.FileWorking;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

public class FileRecorder {

    @Value("${temp.path}")
    private String tempPath;

    @Value("${python.start}")
    private String pythonStart;

    @Value("${python.script.split}")
    private String pythonSplit;

    @Value("${out.path}")
    private String outPath;

    private File createTempFile(MultipartFile file) throws IOException {

        File tempDir = new File(tempPath);
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }

        String uuidFile = UUID.randomUUID().toString();
        String filename = uuidFile + "." + file.getOriginalFilename();

        File tempFile = new File(tempDir + "/" + filename);

        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(file.getBytes());
        fos.close();

        return tempFile;
    }

    private List<String> splitFile(File tempFile, String originalName) throws IOException {
        List<String> filePathes = new ArrayList<>();

        Process process = new ProcessBuilder(pythonStart, pythonSplit, tempPath, originalName, outPath)
                .inheritIO().start();

        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String name = br.readLine();
        while(name != null) {
            filePathes.add(name);

            name = br.readLine();
        }

        return filePathes;
    }

    public List<String> recordBook(MultipartFile file) throws IOException {

        String originalName = file.getOriginalFilename();

        File tempFile = createTempFile(file);

        List<String> filePathes = splitFile(tempFile, originalName);

        tempFile.delete();

        return filePathes;
    }

}
