package org.foden.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseUtils {
    private static final String DB_URL = "jdbc:mysql://" + ConfigLoader.getInstance().getProperty("db_host") + ":" + ConfigLoader.getInstance().getProperty("db_port") + "/" + ConfigLoader.getInstance().getProperty("db_name");
    private static final String DB_USERNAME = ConfigLoader.getInstance().getProperty("db_username");
    private static final String DB_PASSWORD = ConfigLoader.getInstance().getProperty("db_password");

    public static void loadUserCredentials() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user_credentials LIMIT 1");

            if (resultSet.next()) {
                int columnCount = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = resultSet.getMetaData().getColumnName(i);
                    String columnValue = resultSet.getString(i);
                    DataManager.getInstance().setData(columnName, columnValue);
                }
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
