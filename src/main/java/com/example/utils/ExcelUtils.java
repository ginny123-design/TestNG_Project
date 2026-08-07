package com.example.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {

    private String filePath;
    private Workbook workbook;

    public ExcelUtils(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads all data from a specified sheet (excluding header row) and returns Object[][] for TestNG DataProvider
     */
    public Object[][] getSheetData(String sheetName) {
        Object[][] data = null;
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet " + sheetName + " does not exist in file: " + filePath);
            }

            int rowCount = sheet.getLastRowNum(); // excluding header assumption (row 0 is header)
            int colCount = sheet.getRow(0).getLastCellNum();

            data = new Object[rowCount][colCount];
            DataFormatter formatter = new DataFormatter();

            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < colCount; j++) {
                    if (row == null) {
                        data[i - 1][j] = "";
                    } else {
                        Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        data[i - 1][j] = formatter.formatCellValue(cell);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Helper method to write sample Excel test data programmatically
     */
    public static void createSampleExcelFile(String filePath, String sheetName, String[][] data) throws IOException {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet(sheetName);

        for (int r = 0; r < data.length; r++) {
            Row row = sheet.createRow(r);
            for (int c = 0; c < data[r].length; c++) {
                Cell cell = row.createCell(c);
                cell.setCellValue(data[r][c]);
            }
        }

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            wb.write(fos);
        }
        wb.close();
    }
}
