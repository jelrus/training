package epam.com.esm.exception.types;

/**
 * AlreadyExistsException is the exception class
 * Objects of this class can be thrown during create operation when object already exists by significant field
 */
public class AlreadyExistsException extends RuntimeException {

    /**
     * Constructs AlreadyExistsException with message
     *
     * @param message value for message
     */
    public AlreadyExistsException(String message) {
        super(message);
    }
}