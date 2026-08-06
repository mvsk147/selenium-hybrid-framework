package com.sai.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties properties = new Properties();

    private ConfigReader(){}

    static{
        try (InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")){
            if(inputStream == null) {
                throw new RuntimeException("config.properties file not found in the classpath");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties.", e);
        }

    }

    public static String getBrowser(){
        return properties.getProperty("browser");
    }

    public static String getUrl(){
        return properties.getProperty("url");
    }

    public static long getPageLoadTimeout(){
        return Long.parseLong(properties.getProperty("pageLoadTimeout"));
    }

    public static long getExplicitWait(){
        return Long.parseLong(properties.getProperty("explicitWait"));
    }

    public static String getUsername(){
        return properties.getProperty("username");
    }

    public static String getPassword(){
        return properties.getProperty("password");
    }

}
