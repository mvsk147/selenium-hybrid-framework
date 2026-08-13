package com.sai.framework.utils;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;

public class ExcelUtils {

    public static void main(String[] args) {
        System.out.println(getCellData("src/main/resources/testdata/LoginData.xlsx","logindata",0,0));

    }

    public static int getRowCount(String filePath, String sheetName){

        try(FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = WorkbookFactory.create(fis)){

            Sheet sheet = workbook.getSheet(sheetName);
            return sheet.getPhysicalNumberOfRows();

        } catch (Exception e) {
            throw new RuntimeException("Failed to get rowcount from sheet "+sheetName,e);
        }

    }


    public static int getColumnCount(String filePath, String sheetName){

        try(FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = WorkbookFactory.create(fis)){

            Sheet sheet = workbook.getSheet(sheetName);

            Row row = sheet.getRow(0);
            return row.getPhysicalNumberOfCells();


        } catch (Exception e) {
            throw new RuntimeException("Failed to get column count from sheet "+sheetName,e);
        }

    }

    public static String getCellData(String filePath, String sheetName, int row, int column){

        try(FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = WorkbookFactory.create(fis)){

            Sheet sheet = workbook.getSheet(sheetName);

            if(sheet == null){
                throw new IllegalArgumentException("Sheet "+sheetName+" does not exist in file:"+filePath);
            }

            Row rowData = sheet.getRow(row);

            if(rowData == null){
                throw new IllegalArgumentException("Row "+row+" does not exist in sheet:"+sheetName);
            }
            Cell cell = rowData.getCell(column);

            if(cell == null) {
                return "";
            }

            DataFormatter formatter = new DataFormatter();
            return formatter.formatCellValue(cell);


        } catch (Exception e) {
            throw new RuntimeException("Failed to get cell data from sheet "+sheetName,e);
        }

    }

    public static Object[][] getSheetData(String filePath, String sheetName){

        int rowCount = getRowCount(filePath, sheetName);
        int columnCount = getColumnCount(filePath, sheetName);

        Object[][] excelData = new Object[rowCount-1][columnCount];

        for(int i=1; i< rowCount; i++){

            for (int j=0; j < columnCount; j++){
                excelData[i-1][j] = getCellData(filePath, sheetName, i, j);
            }

        }

        return excelData;

    }

}
