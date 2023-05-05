package com.epam.esm.utils.logger.impl;

import com.epam.esm.utils.logger.LoggerService;
import com.epam.esm.utils.logger.impl.type.LoggerLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * LoggerServiceImpl is the service class, implementor of LoggerService
 * Used to write logs
 */
@Service
public class LoggerServiceImpl implements LoggerService {

    /**
     * Constant field to hold info logger
     */
    private static final Logger LOGGER_INFO = LoggerFactory.getLogger(LoggerLevel.INFO.getLevel());

    /**
     * Constant field to hold warn logger
     */
    private static final Logger LOGGER_WARN = LoggerFactory.getLogger(LoggerLevel.WARN.getLevel());

    /**
     * Constant field to hold error logger
     */
    private static final Logger LOGGER_ERROR = LoggerFactory.getLogger(LoggerLevel.ERROR.getLevel());

    /**
     * Creates log entry with requested logger level and message
     *
     * @param loggerLevel requested object
     * @param message requested object
     */
    @Override
    public void post(LoggerLevel loggerLevel, String message) {
        if (loggerLevel == LoggerLevel.INFO) {
            LOGGER_INFO.info(message);
        }

        if (loggerLevel == LoggerLevel.WARN) {
            LOGGER_WARN.warn(message);
        }

        if (loggerLevel == LoggerLevel.ERROR) {
            LOGGER_ERROR.error(message);
        }
    }
}