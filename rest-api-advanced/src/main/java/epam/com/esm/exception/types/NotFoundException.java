package epam.com.esm.exception.types;

/**
 * NotFoundException is the exception class
 * Objects of this class can be thrown during find operation, if entity doesn't exist by significant field
 */
public class NotFoundException extends RuntimeException {

    /**
     * Constructs NotFoundException with message
     *
     * @param message value for message
     */
    public NotFoundException(String message) {
        super(message);
    }
}