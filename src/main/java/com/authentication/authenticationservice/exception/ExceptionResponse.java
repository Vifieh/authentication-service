package com.authentication.authenticationservice.exception;

import lombok.Data;

@Data
public class ExceptionResponse {

    private String title;
    private String message;
    private String endpoint;
}