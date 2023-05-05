package com.epam.esm;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * RestApiBasicsApplication is the configuration class, which loads properties from application context
 * based on xml file
 */
@Configuration
public class RestApiBasicsApplication {

    /**
     * Loads properties from application context based on xml file
     *
     * @param args requested arguments
     */
    public static void main(String[] args) {
        new FileSystemXmlApplicationContext("classpath:application-context.xml");
    }
}