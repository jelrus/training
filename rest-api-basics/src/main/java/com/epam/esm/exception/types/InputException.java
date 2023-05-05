package com.epam.esm.exception.types;

/**
 * InputException is the exception class
 * Objects of this class can be thrown during malformed or corrupted input
 */
public class InputException extends RuntimeException{

    /**
     * Constructs InputException with message
     *
     * @param message value for message
     */
    public InputException(String message) {
        super(message);
    }
}