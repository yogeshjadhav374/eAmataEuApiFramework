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
            input = new FileInputStream("C:/Users/LNV-24/Desktop/eAmataEuApiFramework/src/test/resources/config.properties");
            prop.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop.getProperty(key);
    }
}

