package com.sai.framework.dataproviders;

import com.sai.framework.utils.ExcelUtils;
import org.testng.annotations.DataProvider;

public class TestDataProvider {


    @DataProvider(name = "loginData")
    public Object[][] loginData(){

        String filePath = "src\\main\\resources\\testdata\\LoginData.xlsx";
        return ExcelUtils.getSheetData(filePath, "logindata");
    }

}
