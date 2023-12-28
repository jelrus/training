package epam.com.esm.exception.types;

/**
 * UnexpectedException is the exception class
 * Objects of this class can be thrown as stub, to hide the real cause of failure
 */
public class UnexpectedException extends RuntimeException {

    /**
     * Constructs UnexpectedException with message
     *
     * @param message value for message
     */
    public UnexpectedException(String message) {
        super(message);
    }
}