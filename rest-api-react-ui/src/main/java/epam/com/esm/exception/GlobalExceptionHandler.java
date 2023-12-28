package epam.com.esm.exception;

import epam.com.esm.exception.dto.ErrorResponseDto;
import epam.com.esm.exception.types.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * GlobalExceptionHandler class is the special controller for catching exceptions which might occur
 * during code execution
 * <p>
 * Contains handlers to produce response if significant exception occur
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    /**
     * Catches InputException exception and produces response
     *
     * @param e caught exception
     * @return {@code ResponseEntity<Object>} response, contains message and HTTP status code
     */
    @ExceptionHandler(value = InputException.class)
    public ResponseEntity<Object> handleInputException(InputException e) {
        String errorMessage = e.getMessage();
        String errorCode = HttpStatus.BAD_REQUEST.value() + "01";
        ErrorResponseDto erDto = new ErrorResponseDto(errorMessage, errorCode);
        return new ResponseEntity<>(erDto, HttpStatus.BAD_REQUEST);
    }

    /**
     * Catches IncorrectUrlParameterException exception and produces response
     *
     * @param e caught exception
     * @return {@code ResponseEntity<Object>} response, contains message and HTTP status code
     */
    @ExceptionHandler(value = IncorrectUrlParameterException.class)
    public ResponseEntity<Object> handleIncorrectPageNumberException(IncorrectUrlParameterException e) {
        String errorMessage = e.getMessage();
        String errorCode = HttpStatus.BAD_REQUEST.value() + "02";
        ErrorResponseDto erDto = new ErrorResponseDto(errorMessage, errorCode);
        return new ResponseEntity<>(erDto, HttpStatus.BAD_REQUEST);
    }

    /**
     * Catches EmptyOrderException exception and produces response
     *
     * @param e caught exception
     * @return {@code ResponseEntity<Object>} response, contains message and HTTP status code
     */
    @ExceptionHandler(value = EmptyOrderException.class)
    public ResponseEntity<Object> handleEmptyOrderException(EmptyOrderException e) {
        String errorMessage = e.getMessage();
        String errorCode = HttpStatus.BAD_REQUEST.value() + "03";
        ErrorResponseDto erDto = new ErrorResponseDto(errorMessage, errorCode);
        return new ResponseEntity<>(erDto, HttpStatus.BAD_REQUEST);
    }

    /**
     * Catches UnexpectedException exception and produces response
     *
     * @param e caught exception
     * @return {@code ResponseEntity<Object>} response, contains message and HTTP status code
     */
    @ExceptionHandler(value = UnexpectedException.class)
    public ResponseEntity<Object> handleUnexpectedException(UnexpectedException e) {
        String errorMessage = e.getMessage();
        String errorCode = HttpStatus.BAD_REQUEST.value() + "04";
        ErrorResponseDto erDto = new ErrorResponseDto(errorMessage, errorCode);
        return new ResponseEntity<>(erDto, HttpStatus.BAD_REQUEST);
    }

    /**
     * Catches BuildException exception and produces response
     *
     * @param e caught exception
     * @return {@code ResponseEntity<Object>} response, contains message and HTTP status code
     */
    @ExceptionHandler(value = BuildException.class)
    public ResponseEntity<Object> handleUnexpectedException(BuildException e) {
        String errorMessage = e.getMessage();
        String errorCode = HttpStatus.BAD_REQUEST.value() + "05";
        ErrorResponseDto erDto = new ErrorResponseDto(errorMessage, errorCode);
        return new ResponseEntity<>(erDto, HttpStatus.BAD_REQUEST);
    }

    /**
     * Catches HttpMessageNotReadableException exception and produces response
     *
     * @return {@code ResponseEntity<Object>} response, contains message and HTTP status code
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleMissingJsonBody() {
        String errorMessage = "JSON body is empty";
        String errorCode = HttpStatus.BAD_REQUEST.value() + "06";
        ErrorResponseDto erDto = new ErrorResponseDto(errorMessage, errorCode);
        return new ResponseEntity<>(erDto, HttpStatus.BAD_REQUEST);
    }

    /**
     * Catches MethodArgumentTypeMismatchException exception and produces response
     *
     * @return {@code ResponseEntity<Object>} response, contains message and HTTP status code
     */
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleArgumentTypeMismatch() {
        String errorMessage = "Incorrect argument types";
        String errorCode = HttpStatus.BAD_REQUEST.value() + "07";
        ErrorResponseDto erDto = new ErrorResponseDto(errorMessage, errorCode);
        return new ResponseEntity<>(erDto, HttpStatus.BAD_REQUEST);
    }

    /**
     * Catches HttpRequestMethodNotSupportedException exception and produces response
     *
     * @return {@code ResponseEntity<Object>} response, contains message and HTTP status code
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleMethodNotSupported() {
        String errorMessage = "Wrong method type";
        String errorCode = HttpStatus.METHOD_NOT_ALLOWED.value() + "01";
        ErrorResponseDto erDto = new ErrorResponseDto(errorMessage, errorCode);
        return new ResponseEntity<>(erDto, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Catches NotFoundException exception and produces response
     *
     * @param e caught exception
     * @return {@code ResponseEntity<Object>} response, contains message and HTTP status code
     */
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        String errorMessage = e.getMessage();
        String errorCode = HttpStatus.NOT_FOUND.value() + "01";
        ErrorResponseDto erDto = new ErrorResponseDto(errorMessage, errorCode);
        return new ResponseEntity<>(erDto, HttpStatus.NOT_FOUND);
    }

    /**
     * Catches AlreadyExistsException exception and produces response
     *
     * @param e caught exception
     * @return {@code ResponseEntity<Object>} response, contains message and HTTP status code
     */
    @ExceptionHandler(value = AlreadyExistsException.class)
    public ResponseEntity<Object> handleAlreadyExistsException(AlreadyExistsException e) {
        String errorMessage = e.getMessage();
        String errorCode = HttpStatus.CONFLICT.value() + "01";
        ErrorResponseDto erDto = new ErrorResponseDto(errorMessage, errorCode);
        return new ResponseEntity<>(erDto, HttpStatus.CONFLICT);
    }

    /**
     * Catches OperationFailedException exception and produces response
     *
     * @param e caught exception
     * @return {@code ResponseEntity<Object>} response, contains message and HTTP status code
     */
    @ExceptionHandler(value = OperationFailedException.class)
    public ResponseEntity<Object> handleOperationFailedException(OperationFailedException e) {
        String errorMessage = e.getMessage();
        String errorCode = HttpStatus.CONFLICT.value() + "02";
        ErrorResponseDto erDto = new ErrorResponseDto(errorMessage, errorCode);
        return new ResponseEntity<>(erDto, HttpStatus.CONFLICT);
    }

    /**
     * Catches AccessDeniedException exception and produces response
     *
     * @return {@code ResponseEntity<Object>} response, contains message and HTTP status code
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleAccessDeniedException() {
        String errorMessage = "Access denied or token corrupted";
        String errorCode = HttpStatus.UNAUTHORIZED.value() + "01";
        ErrorResponseDto erDto = new ErrorResponseDto(errorMessage, errorCode);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erDto);
    }
}