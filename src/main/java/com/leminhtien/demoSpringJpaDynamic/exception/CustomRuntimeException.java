package com.leminhtien.demoSpringJpaDynamic.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomRuntimeException extends RuntimeException{
    private HttpStatus status;
    public CustomRuntimeException(String message,HttpStatus status){
        super(message);
        this.status = status;
    }

    public CustomRuntimeException(String message){
        this(message,HttpStatus.BAD_REQUEST);
    }

}
