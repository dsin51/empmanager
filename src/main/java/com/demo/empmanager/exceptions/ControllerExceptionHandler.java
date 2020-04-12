package com.demo.empmanager.exceptions;

import com.demo.empmanager.error.ErrorResponse;
import com.mongodb.MongoWriteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(PersonException.class)
    public ResponseEntity<ErrorResponse> handleException(PersonException pe) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(pe.getField(), pe.getMessage()));
    }

    @ExceptionHandler(MongoWriteException.class)
    public ResponseEntity<ErrorResponse> handleException(MongoWriteException me) {
        log.error(me.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("email", "email.id.not.unique"));
        // Refactor to only handle "E11000 duplicate key error" for email uniqueness.
    }
}

