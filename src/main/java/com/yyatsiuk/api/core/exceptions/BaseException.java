package com.yyatsiuk.api.core.exceptions;

import java.text.MessageFormat;

public class BaseException extends RuntimeException {

    public BaseException() {
    }

    public BaseException(String message, Object... args) {
        super(getFormattedString(message, args));
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause, String message, Object... args) {
        super(getFormattedString(message, args), cause);
    }

    private static String getFormattedString(String message, Object... args) {
        return MessageFormat.format(message, args);
    }

}
