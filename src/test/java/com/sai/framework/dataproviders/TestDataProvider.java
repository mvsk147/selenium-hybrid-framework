package com.sai.framework.dataproviders;

import com.sai.framework.utils.ExcelUtils;
import com.sai.framework.utils.JsonUtils;
import org.testng.annotations.DataProvider;

public class TestDataProvider {


    @DataProvider(name = "loginData")
    public Object[][] loginData(){

        String filePath = "src\\main\\resources\\testdata\\LoginData.xlsx";
        return ExcelUtils.getSheetData(filePath, "logindata");
    }


    @DataProvider(name = "loginJsonData")
    public Object[][] loginJsonData(){

        String filePath = "src/main/resources/testdata/LoginData.json";
        return JsonUtils.getJsonData(filePath);

    }

}
