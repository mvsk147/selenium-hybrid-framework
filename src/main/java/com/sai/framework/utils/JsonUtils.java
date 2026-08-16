package com.sai.framework.utils;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Iterator;

public class JsonUtils {

    public static Object[][] getJsonData(String filePath){

        try{

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File(filePath));
            int rowCount = rootNode.size();

            if(rowCount == 0){
                return new Object[0][0];
            }

            int columnCount = rootNode.get(0).size();

            Object[][] jsonData = new Object[rowCount][columnCount];

            for(int i=0; i<rowCount; i++){

                JsonNode testData = rootNode.get(i);

                jsonData[i][0] = testData.get("username").asText();
                jsonData[i][1] = testData.get("password").asText();

            }

            return jsonData;

        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON test data from file: "+filePath,e);
        }

    }

    public static JsonNode readJson(String filePath){

        try{

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File(filePath));
            return rootNode;

        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON file: "+filePath,e);
        }

    }

}
