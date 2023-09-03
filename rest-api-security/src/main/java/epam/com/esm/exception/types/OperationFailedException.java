package epam.com.esm.exception.types;

/**
 * OperationFailedException is the exception class
 * Objects of this class can be thrown to interrupt operations, if operation stage(s) was finished incorrectly
 */
public class OperationFailedException extends RuntimeException {

    /**
     * Constructs OperationFailedException with message
     *
     * @param message value for message
     */
    public OperationFailedException(String message) {
        super(message);
    }
}