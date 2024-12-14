package org.foden.utils;

import java.util.Objects;
import java.util.Properties;

public class DataLoader {

    private final Properties properties;
    private static volatile DataLoader dataLoader;

    private DataLoader(){
        properties = PropertyUtils.propertyLoader("./src/test/resources/data.properties");
    }

    public static DataLoader getInstance(){
        if (Objects.isNull(dataLoader)){
            synchronized (DataLoader.class){
                if (Objects.isNull(dataLoader)){
                    dataLoader = new DataLoader();
                }
            }
        }
        return dataLoader;
    }

    public String getGetPlaylistId(){
        String prop = properties.getProperty("get_playlist_id");
        if (Objects.nonNull(prop)){
            return prop;
        } else {
            throw new RuntimeException("Property get_playlist_id is not specified in the config properties file");
        }
    }

    public String getUpdatePlaylistId(){
        String prop = properties.getProperty("update_playlist_id");
        if (Objects.nonNull(prop)){
            return prop;
        } else {
            throw new RuntimeException("Property update_playlist_id is not specified in the config properties file");
        }
    }

}
