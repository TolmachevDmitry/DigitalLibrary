package com.tolmic.digitallibrary.file_working;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.tolmic.digitallibrary.file_working.exceptions.NotSingleDirectoryException;
import com.tolmic.digitallibrary.os.scripts.python.SplitRecordFile;


@Component
public class FileRecorder {

    @Autowired
    SplitRecordFile splitRecordFile;

    @Value("${temp.path}")
    private String tempPath;

    @Value("${python.start}")
    private String pythonStart;

    @Value("${python.script.split}")
    private String pythonSplit;

    @Value("${books.directory.path}")
    private String booksDirectory;

    private void checkDirExist(String path) {
        File dir = new File(path);

        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public File createTempFile(MultipartFile file) throws IOException {

        checkDirExist(tempPath);

        String uuidFile = UUID.randomUUID().toString();
        String fullFilePath = tempPath + uuidFile + ".docx";

        try (FileOutputStream fos = new FileOutputStream(fullFilePath)) {
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new File(fullFilePath);
    }

    public List<String> splitAndRecordBook(String tempFileName, String outPath) throws IOException {
        return splitRecordFile.splitAndRecordFile(pythonStart, pythonSplit, tempPath, tempFileName, outPath);
    }

    private String createOutDirectory(String bookName, String authorFullName) {
        String path = booksDirectory + (authorFullName != null ? 
                                                authorFullName : "no author");

        checkDirExist(path);

        path += "/" + bookName + "/";

        checkDirExist(path);

        return path;
    }

    private void deleteFile(File file) {
        file.delete();
    }

    public List<String> recordBook(MultipartFile file, String bookName, String authorFullName) throws IOException {
        File tempFile = createTempFile(file);

        String pathToRecord = createOutDirectory(bookName, authorFullName);

        List<String> divisionsInformations = splitAndRecordBook(tempFile.getName(), pathToRecord);

        deleteFile(tempFile);

        return divisionsInformations;
    }

    private void checkDirectoryEquality(String path1, String path2) throws NotSingleDirectoryException {
        if (!path1.equals(path2)) {
            new NotSingleDirectoryException(path1, path2);
        }
    }

    private void moveFile(String originalLocation, String targetLocation) {
        Path initialPath = Paths.get(originalLocation);
        Path targetPath = Paths.get(targetLocation);

        try {
            Files.move(initialPath, targetPath);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private String getFilePathWithouName(File file) {
        String path = file.getAbsolutePath();

        int ind = path.lastIndexOf(File.separator);
        if (ind != -1) {
            path = path.substring(0, ind + 1);
        }

        return path;
    }

    public List<String> changeBookDirectory(List<String> divisionLinks, String authorsString, String bookName) {
        List<String> actualLinks = new ArrayList<>();

        String authorDirectory = booksDirectory + authorsString + "/";
        checkDirExist(authorDirectory);

        String actualbookDirectory = authorDirectory + bookName + "/";
        checkDirExist(actualbookDirectory);
        
        String referenceDirectory = "";
        for (String path : divisionLinks) {
            File file = new File(path);

            String currDirectory = getFilePathWithouName(file);
            String fileName = file.getName();

            try {
                if (!referenceDirectory.equals("")) {
                    checkDirectoryEquality(referenceDirectory, currDirectory);
                } else {
                    referenceDirectory = currDirectory;
                }
            } catch (NotSingleDirectoryException e) {
                e.printStackTrace();
            }

            String targetPath = actualbookDirectory + fileName;

            moveFile(file.getAbsolutePath(), targetPath);

            actualLinks.add(targetPath);
        }

        deleteFile(new File(referenceDirectory));

        return actualLinks;
    }

}
