package com.github.lithualien.currencyexchanger.exceptions.handler;

import com.github.lithualien.currencyexchanger.commands.v1.ExceptionCommand;
import com.github.lithualien.currencyexchanger.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler( { Exception.class } )
    public final ResponseEntity<ExceptionCommand> handleInternalServerErrorExceptions(Exception ex, WebRequest request) {

        ExceptionCommand exceptionCommand = getExceptionCommand(ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getDescription(false));

        return new ResponseEntity<>(exceptionCommand, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler( { ResourceNotFoundException.class } )
    public final ResponseEntity<ExceptionCommand> handleBadRequestExceptions(Exception ex, WebRequest request) {

        ExceptionCommand exceptionCommand = getExceptionCommand(ex.getMessage(), HttpStatus.BAD_REQUEST.value(),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionCommand, HttpStatus.BAD_REQUEST);
    }

    private ExceptionCommand getExceptionCommand(String message, Integer status, String path) {

        ExceptionCommand exceptionCommand = new ExceptionCommand();
        exceptionCommand.setTimestamp(LocalDateTime.now());
        exceptionCommand.setMessage(message);
        exceptionCommand.setStatus(status);
        exceptionCommand.setPath(path);

        return exceptionCommand;
    }

}
