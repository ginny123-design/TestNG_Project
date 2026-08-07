package com.example.utils;

import java.io.File;
import java.io.IOException;

public class CreateTestData {

    public static void main(String[] args) {
        String resourcesDir = System.getProperty("user.dir") + "/src/test/resources";
        File dir = new File(resourcesDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String filePath = resourcesDir + "/testdata.xlsx";

        String[][] sampleData = {
            {"Username", "Password", "ExpectedOutcome", "Description"},
            {"student", "Password123", "Success", "Valid login credential check"},
            {"incorrectUser", "Password123", "InvalidUsername", "Invalid username login validation"},
            {"student", "incorrectPassword", "InvalidPassword", "Invalid password login validation"}
        };

        try {
            ExcelUtils.createSampleExcelFile(filePath, "LoginTests", sampleData);
            System.out.println("Web Test Excel data file generated successfully at: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
