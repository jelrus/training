package epam.com.esm.exception.types;

/**
 * IncorrectUrlParameterException is the exception class
 * Objects of this class can be thrown during parsing web request
 */
public class IncorrectUrlParameterException extends RuntimeException{

    /**
     * Constructs IncorrectUrlParameterException with message
     *
     * @param message value for message
     */
    public IncorrectUrlParameterException(String message) {
        super(message);
    }
}