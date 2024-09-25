package com.tolmic.digitallibrary;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import com.tolmic.digitallibrary.file_working.FileReader;
import com.tolmic.digitallibrary.file_working.FileRecorder;
import com.tolmic.digitallibrary.services.BookDivisionService;


@SpringBootTest
public class FileWorkerTests {

    HelperMethods helperMethods = new HelperMethods();

    @Autowired
    FileReader fileReader;

    @Autowired
    FileRecorder fileRecorder;

    @Autowired
    BookDivisionService bookDivisionService;

    @Value("${temp.path}")
    private String tempPath;

    @Test
    void readFile() {

        String path = "D:\\ВГУ\\Проектирование_Баз_Данных\\Книги\\Александр"+ 
                      "Беляев\\Ариэль\\Ариэль_Глава-1-По кругам ада.docx";
        fileReader.readText(path);

        String link = bookDivisionService.findById(Long.valueOf(10)).getFileLink();

        assertNotEquals(link, null);

        fileReader.readText(link);
    }

    @Test
    void recordFile() {

        String pathToTestFile = "./resources/docx_files/Дубровский Пушкин.docx";
        File file = new File(pathToTestFile);

        assertEquals(file.getName(), "Дубровский Пушкин.docx");
        
        MultipartFile mF = helperMethods.turnFileToMultipartFile(file);

        try {
            fileRecorder.createTempFile(mF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
