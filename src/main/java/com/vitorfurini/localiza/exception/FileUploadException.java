package com.vitorfurini.localiza.exception;

import java.io.Serial;

public class FileUploadException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 3L;

    public FileUploadException(String message){
        super(message);
    }

}
