package com.mycompany.myapp.extended.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ActivityNotFoundException extends RuntimeException {
    public ActivityNotFoundException(String message){super(message);}
}
