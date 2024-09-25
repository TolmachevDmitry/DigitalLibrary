package com.tolmic.digitallibrary.file_working.exceptions;

public class NotSingleDirectoryException extends Exception {

    public NotSingleDirectoryException(String fileName1, String fileName2) {
        super(String.format("File '%s' must be in the same directory as file '%s' or vice versa", 
                        fileName1, fileName2));
    }

}