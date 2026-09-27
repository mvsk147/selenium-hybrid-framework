package com.sai.framework.config;

import com.sai.framework.exceptions.ConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties properties = new Properties();

    private ConfigReader(){}

    static{
        loadProperties();
    }

    private static void loadProperties(){
        String environment = System.getProperty("env");
        String fileName;

        if(environment == null || environment.isBlank()){
            fileName = "config.properties";
        } else {
            fileName = "config-"+environment+".properties";
        }

        try (InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(fileName)){
            if(inputStream == null) {
                throw new ConfigurationException("config file not found "+fileName);
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new ConfigurationException("Failed to load config file" + fileName, e);
        }


    }

    public static String getEnvironment(){
        String environment = System.getProperty("env");
        if(environment == null || environment.isBlank()){
            return "default";
        }
        return environment;
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

    public static long getImplicitWait(){
        return Long.parseLong(properties.getProperty("implicitWait"));
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

    public static boolean isHeadless(){
        return Boolean.parseBoolean(properties.getProperty("headless"));
    }

}
