package com.example.util.exceptions;

import lombok.Getter;

import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;

@Getter
public class ServiceException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}

