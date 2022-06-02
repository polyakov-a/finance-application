package by.itacademy.mail.controllers.advice;

import by.itacademy.mail.services.api.exceptions.MultipleError;
import by.itacademy.mail.services.api.exceptions.SingleError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(MultipleError.class)
    public ResponseEntity<MultipleError> handleMultipleError(MultipleError errors) {
        return new ResponseEntity<>(
                new MultipleError(errors.getLogref(), errors.getErrors()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<SingleError> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(
                new SingleError("error", e.getMessage()),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<SingleError> handleEntityNotFoundException(EntityNotFoundException e) {
        return new ResponseEntity<>(
                new SingleError("error", e.getMessage()),
                HttpStatus.NOT_FOUND);
    }
}
