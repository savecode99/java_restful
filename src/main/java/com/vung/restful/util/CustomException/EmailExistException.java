package com.vung.restful.util.CustomException;

public class EmailExistException extends RuntimeException{
    
    public EmailExistException(String message){
        super(message);
    }
}
