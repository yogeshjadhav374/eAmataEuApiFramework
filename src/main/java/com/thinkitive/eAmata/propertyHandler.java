package com.thinkitive.eAmata;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class propertyHandler {
    public static Properties prop = new Properties();
    public static Properties prop1 = new Properties();
    public static FileInputStream input;
    public static FileInputStream input1;

    public static String getProperty(String key) {

        try {
            input = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties");
            prop.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop.getProperty(key);
    }

    public static String getEndpoint(String key) {

        try {
            input1 = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/endpoint.properties");
            prop1.load(input);
            input1.close();

        } catch (IOException e) {
            e.printStackTrace();

        }
        return getEndpoint(key);
    }
}

