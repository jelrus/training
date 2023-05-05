package com.epam.esm.exception;

import com.epam.esm.exception.dto.ErrorResponseDto;
import com.epam.esm.exception.types.AlreadyExistsException;
import com.epam.esm.exception.types.InputException;
import com.epam.esm.exception.types.NotFoundException;
import com.epam.esm.exception.types.OperationFailedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler class is the special controller to catch exceptions which might occur during code execution
 * Contains handlers to produce response if exception occur
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    /**
     * Catches AlreadyExistsException exception and produces response
     *
     * @param e caught exception
     * @return {@code ResponseEntity<Object>} response, which contains message and HTTP status code
     */
    @ExceptionHandler(value = AlreadyExistsException.class)
    public ResponseEntity<Object> handleAlreadyExistsException(AlreadyExistsException e) {
        String errorMessage = e.getMessage();
        String errorCode = HttpStatus.CONFLICT.value() + "01";
        ErrorResponseDto erDto = new ErrorResponseDto(errorMessage, errorCode);
        return new ResponseEntity<>(erDto, HttpStatus.CONFLICT);
    }

    /**
     * Catches InputException exception and produces response
     *
     * @param e caught exception
     * @return {@code ResponseEntity<Object>} response, which contains message and HTTP status code
     */
    @ExceptionHandler(value = InputException.class)
    public ResponseEntity<Object> handleInputException(InputException e) {
        String errorMessage = e.getMessage();
        String errorCode = HttpStatus.BAD_REQUEST.value() + "01";
        ErrorResponseDto erDto = new ErrorResponseDto(errorMessage, errorCode);
        return new ResponseEntity<>(erDto, HttpStatus.BAD_REQUEST);
    }

    /**
     * Catches NotFoundException exception and produces response
     *
     * @param e caught exception
     * @return {@code ResponseEntity<Object>} response, which contains message and HTTP status code
     */
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        String errorMessage = e.getMessage();
        String errorCode = HttpStatus.NOT_FOUND.value() + "01";
        ErrorResponseDto erDto = new ErrorResponseDto(errorMessage, errorCode);
        return new ResponseEntity<>(erDto, HttpStatus.NOT_FOUND);
    }

    /**
     * Catches OperationFailedException exception and produces response
     *
     * @param e caught exception
     * @return {@code ResponseEntity<Object>} response, which contains message and HTTP status code
     */
    @ExceptionHandler(value = OperationFailedException.class)
    public ResponseEntity<Object> handleOperationFailedException(OperationFailedException e) {
        String errorMessage = e.getMessage();
        String errorCode = HttpStatus.CONFLICT.value() + "02";
        ErrorResponseDto erDto = new ErrorResponseDto(errorMessage, errorCode);
        return new ResponseEntity<>(erDto, HttpStatus.CONFLICT);
    }
}