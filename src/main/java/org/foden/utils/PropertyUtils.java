package org.foden.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {

    public static Properties propertyLoader(String filePath){
        Properties properties = new Properties();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            try {
                properties.load(reader);
            } catch (IOException e){
                throw new RuntimeException("Failed to load properties file " + filePath);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Properties file not found at " + filePath);
        }
        return properties;
    }
}
