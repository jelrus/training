package com.epam.esm.exception.dto;

/**
 * ErrorResponseDto is the data class, serves to form error data for response.
 */
public class ErrorResponseDto {

    /**
     * Field for errorMessage value
     */
    private String errorMessage;

    /**
     * Field for errorCode value
     */
    private String errorCode;

    /**
     * Constructs ErrorResponseDto object with errorMessage and errorCode
     *
     * @param errorMessage value for errorMessage
     * @param errorCode value for errorCode
     */
    public ErrorResponseDto(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    /**
     * Gets value from errorMessage field
     *
     * @return {@code String} errorMessage value
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets new value to errorMessage field
     *
     * @param errorMessage value for setting
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Gets value from errorCode field
     *
     * @return {@code String} errorCode value
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets new value to errorCode field
     *
     * @param errorCode value for setting
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}