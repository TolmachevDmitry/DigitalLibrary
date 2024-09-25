package com.tolmic.digitallibrary.os.scripts.python;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SplitRecordFile {
    
    public List<String> splitAndRecordFile(String pythonStart, String pythonSplit, 
                        String tempPath, String tempFileName, String outPath) throws IOException {

        List<String> filesLinks = new ArrayList<>();

        ProcessBuilder processBuilder = new ProcessBuilder(pythonStart, pythonSplit, 
                                    tempPath, tempFileName, outPath);

        Process process = processBuilder.start();

        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String name;
        while((name = br.readLine()) != null) {
            filesLinks.add(name);
        }

        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        br.close();

        return filesLinks;
    }
}
