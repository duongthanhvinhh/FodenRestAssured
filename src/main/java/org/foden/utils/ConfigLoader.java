package org.foden.utils;

import java.util.Objects;
import java.util.Properties;

public class ConfigLoader {

    private final Properties properties;
    private static volatile ConfigLoader configLoader;

    private ConfigLoader(){
        properties = PropertyUtils.propertyLoader("./src/test/resources/config.properties");
    }

    public static ConfigLoader getInstance(){
        if (Objects.isNull(configLoader)){
            synchronized (ConfigLoader.class){
                if (Objects.isNull(configLoader)){
                    configLoader = new ConfigLoader();
                }
            }
        }
        return configLoader;
    }

    public String getProperty(String propertyName){
        String prop = properties.getProperty(propertyName);
        if (Objects.nonNull(prop)){
            return prop;
        } else {
            throw new RuntimeException("Property " + propertyName + " is not specified in the config properties file !!!");
        }
    }

}
