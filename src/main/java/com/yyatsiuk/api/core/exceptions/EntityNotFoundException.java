package com.yyatsiuk.api.core.exceptions;

public class EntityNotFoundException extends BaseException {

    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String message, Object... args) {
        super(message, args);
    }

}
