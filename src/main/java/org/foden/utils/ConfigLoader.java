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

    public String getUserId(){
        String prop = properties.getProperty("user_id");
        if (Objects.nonNull(prop)){
            return prop;
        } else {
            throw new RuntimeException("Property user_id is not specified in the config properties file");
        }
    }

    public String getClientId(){
        String prop = properties.getProperty("client_id");
        if (Objects.nonNull(prop)){
            return prop;
        } else {
            throw new RuntimeException("Property client_id is not specified in the config properties file");
        }
    }

    public String getClientSecret(){
        String prop = properties.getProperty("client_secret");
        if (Objects.nonNull(prop)){
            return prop;
        } else {
            throw new RuntimeException("Property client_secret is not specified in the config properties file");
        }
    }

    public String getGrantType(){
        String prop = properties.getProperty("grant_type");
        if (Objects.nonNull(prop)){
            return prop;
        } else {
            throw new RuntimeException("Property grant_type is not specified in the config properties file");
        }
    }

    public String getRefreshToken(){
        String prop = properties.getProperty("refresh_token");
        if (Objects.nonNull(prop)){
            return prop;
        } else {
            throw new RuntimeException("Property refresh_token is not specified in the config properties file");
        }
    }
}
