package com.example.util.http;

import com.example.util.exceptions.ExchangeApiException;
import com.example.util.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
class GlobalControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ExchangeApiException.class)
    public @ResponseBody
    HttpErrorInfo handleExchangeApiExceptions(ServerHttpRequest request, Exception ex) {

        return createHttpErrorInfo(INTERNAL_SERVER_ERROR, request, ex);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServiceException.class)
    public @ResponseBody HttpErrorInfo handleServiceException(ServerHttpRequest request, Exception ex) {

        return createHttpErrorInfo(INTERNAL_SERVER_ERROR, request, ex);
    }

    private HttpErrorInfo createHttpErrorInfo(HttpStatus httpStatus, ServerHttpRequest request, Exception ex) {
        final String path = request.getPath().pathWithinApplication().value();
        final String message = ex.getMessage();

        LOG.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path, message);
        return new HttpErrorInfo(httpStatus, path, message);
    }
}