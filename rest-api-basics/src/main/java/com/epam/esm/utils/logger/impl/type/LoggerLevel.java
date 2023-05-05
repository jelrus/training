package com.epam.esm.utils.logger.impl.type;

/**
 * LoggerLevel enum class which holds values for logger levels
 */
public enum LoggerLevel {

    /**
     * Constant field for info level
     */
    INFO("info"),

    /**
     * Constant field for warn level
     */
    WARN("warn"),

    /**
     * Constant field for error level
     */
    ERROR("error");

    /**
     * Field to hold level value
     */
    private final String level;

    /**
     * Constructs LoggerLevel object with level value
     *
     * @param level value for level
     */
    LoggerLevel(String level) {
        this.level = level;
    }

    /**
     * Gets level
     *
     * @return {@code String} value of level
     */
    public String getLevel() {
        return level;
    }
}