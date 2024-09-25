package com.tolmic.digitallibrary.file_working;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.tolmic.digitallibrary.file_working.exceptions.NotCorrespondDivisionFormat;


@Component
public class HandleStringParams {

    private String[] numberParams = new String[] {"chapterNumber", "partNumber",
                                                        "valueNumber"};

    private void checkConvertibleToNum(String passibleNumber) {
        try {
            Integer.valueOf(passibleNumber);
        } catch (NumberFormatException e) {
            new NotCorrespondDivisionFormat("After flags '-c', '-p' or '-v'" + 
                                " must be string, that can be converted to integer.");
        }
    }

    private void recordParam(Map<String, String> mapParam, String key, String value) {

        if (Arrays.asList(numberParams).contains(value)) {
            checkConvertibleToNum(value);
        }
        
        String preValue = mapParam.get(key);
        mapParam.put(key, (preValue == null ? "" : preValue + " ") + value);
    }

    public void parseParam(String[] params, Map<String, String> mapParam) {
        Set<String> passedFlags = new HashSet<>();

        String currentParam = "";

        for (String str : params) {

            if (passedFlags.contains(params)) {
                new NotCorrespondDivisionFormat("Reuse parameter: " + params);
            }

            switch (str) {
                case "-f":
                    currentParam = "fileLink";
                    passedFlags.add(str);
                    break;
                case "-c":
                    currentParam = "chapterNumber";
                    passedFlags.add(str);
                    break;
                case "-p":
                    currentParam = "partNumber";
                    passedFlags.add(str);
                    break;
                case "-v":
                    currentParam = "valueNumber";
                    passedFlags.add(str);
                    break;
                case "-cn":
                    currentParam = "chapterName";
                    passedFlags.add(str);
                    break;
                case "-pn":
                    currentParam = "partName";
                    passedFlags.add(str);
                    break;
                default:
                    recordParam(mapParam, currentParam, str);
            }
        }

        if (mapParam.get("fileLink") == null || mapParam.get("chapterNumber") == null) {
            new NotCorrespondDivisionFormat("Don't exists one of the" + 
                                    " required parameters: fileLink or" + 
                                    " numberChapter or both at once");
        }
    }

    public List<Map<String, String>> splitOutStringsToParamMap(List<String> paramStringList) {
        List<Map<String, String>> parametrMaps = new ArrayList<>();

        for (String param : paramStringList) {
            Map<String, String> map = new HashMap<String, String>();

            parseParam(param.split(" "), map);

            parametrMaps.add(map);
        }

        return parametrMaps;
    }

}
