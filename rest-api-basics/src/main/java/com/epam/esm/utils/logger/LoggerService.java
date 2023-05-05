package com.epam.esm.utils.logger;

import com.epam.esm.utils.logger.impl.type.LoggerLevel;

/**
 * LoggerService is the interface that delegates logger methods to implementor
 */
public interface LoggerService {

    /**
     * Creates log entry with requested logger level and message
     *
     * @param loggerLevel requested object
     * @param message requested object
     */
    void post(LoggerLevel loggerLevel, String message);
}