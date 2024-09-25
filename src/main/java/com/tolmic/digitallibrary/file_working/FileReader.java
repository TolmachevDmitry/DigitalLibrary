package com.tolmic.digitallibrary.file_working;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Component;


@Component
public class FileReader {

    public List<String> readText(String path) {

        List<String> pars = new ArrayList<>(20);

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
}
