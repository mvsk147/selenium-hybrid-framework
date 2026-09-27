package com.sai.framework.config;

import com.sai.framework.exceptions.ConfigurationException;

public class CredentialManager {

    private CredentialManager(){}

    public static String getUsername(){
        String username = System.getenv("TEST_USERNAME");

        if(username == null || username.isBlank()){
            throw new ConfigurationException("TEST_USERNAME environment variable not configured");
        }
        return username;
    }

    public static String getPassword(){
        String password = System.getenv("TEST_PASSWORD");

        if(password == null || password.isBlank()){
            throw new ConfigurationException("TEST_PASSWORD environment variable not configured");
        }
        return password;
    }

}
