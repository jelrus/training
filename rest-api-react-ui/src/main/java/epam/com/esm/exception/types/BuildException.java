package epam.com.esm.exception.types;

/**
 * BuildException is the exception class
 * Objects of this class can be thrown during LinkBuilder object operations
 */
public class BuildException extends RuntimeException{

    /**
     * Constructs BuildException with message
     *
     * @param message value for message
     */
    public BuildException(String message) {
        super(message);
    }
}