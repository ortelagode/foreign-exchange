package com.example.util.exceptions;

import lombok.Getter;
import org.springframework.web.client.HttpClientErrorException;
@Getter
public class ExchangeApiException extends RuntimeException {
    private Integer code = null;

    public ExchangeApiException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ExchangeApiException(String message) {
        super(message);
    }

    public ExchangeApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExchangeApiException(Throwable cause) {
        super(cause);
    }
}
