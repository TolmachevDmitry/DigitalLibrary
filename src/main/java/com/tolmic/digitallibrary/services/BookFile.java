package com.tolmic.digitallibrary.services;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.tolmic.digitallibrary.entities.BookDivision;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

public class BookFile {

    @Value("${temp.path}")
    private String tempPath;

    public List<String> readText(String path) {

        List<String> pars = new ArrayList<>();

        try(FileInputStream fileInputStream = new FileInputStream(path)) {

            XWPFDocument docxFile = new XWPFDocument(OPCPackage.open(fileInputStream));

            List<XWPFParagraph> paragraphs = docxFile.getParagraphs();
            for (XWPFParagraph p : paragraphs) {
                pars.add(p.getText());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pars;
    }

    private String saveFile() {

        return "";
    }

    public List<XWPFParagraph> getFileParagraphs(File file) {

        List<XWPFParagraph> paragraphs = null;

        try(FileInputStream fileInputStream = new FileInputStream(file)) {

            XWPFDocument docxFile = new XWPFDocument(OPCPackage.open(fileInputStream));

            paragraphs = docxFile.getParagraphs();

            for (XWPFParagraph par : paragraphs) {
                
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return paragraphs;
    }

    public void splitFile(File file) {

    }

    public File createTempFile(MultipartFile file) throws IOException {
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

    public List<BookDivision> saveBookText() {

        // Сделаем миграцию с Python

        return null;
    }

}
