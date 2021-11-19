package org.tretton63.feikit.interfaces.rest.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.tretton63.feikit.interfaces.rest.exceptions.NotFoundException;

@RestControllerAdvice
public class ResponseAdviceController {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void NotFound() {
    }
}
