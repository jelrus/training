package com.epam.esm;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.FileSystemXmlApplicationContext;

@Configuration
public class Main {

    public static void main(String[] args) {
        new FileSystemXmlApplicationContext("classpath:application-context.xml");
    }
}