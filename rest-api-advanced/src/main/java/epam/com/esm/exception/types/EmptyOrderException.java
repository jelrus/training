package epam.com.esm.exception.types;

/**
 * EmptyOrderException is the exception class
 * Objects of this class can be thrown during create order operation if no gift certificates objects in order are found
 */
public class EmptyOrderException extends RuntimeException{

    /**
     * Constructs EmptyOrderException with message
     *
     * @param message value for message
     */
    public EmptyOrderException(String message) {
        super(message);
    }
}