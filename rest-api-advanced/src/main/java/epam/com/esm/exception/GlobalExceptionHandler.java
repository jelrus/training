package epam.com.esm.exception;

import epam.com.esm.exception.dto.ErrorResponseDto;
import epam.com.esm.exception.types.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}