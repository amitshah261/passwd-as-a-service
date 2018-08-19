package com.example.braincorp.error;

import org.springframework.http.HttpStatus;

public class ApiError {

    private HttpStatus status;
    private String message;

    ApiError(HttpStatus status) {
        this.status = status;
    }

    ApiError(HttpStatus status, String message, Throwable ex) {
        this.status = status;
        this.message = message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public HttpStatus getStatus(){
        return status;
    }
    public void setStatus(HttpStatus status){
        this.status = status;
    }
}

