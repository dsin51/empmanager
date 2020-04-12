package com.demo.empmanager.exceptions;

import lombok.Getter;

@Getter
public class PersonException extends Exception {
    private String field;
    private String message;
    public PersonException(String field, String message) {
        this.field = field;
        this.message = message;
    }
}




