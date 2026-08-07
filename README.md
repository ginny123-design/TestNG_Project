# TestNG + Maven + Excel Data Driven + Extent Reports Project

This project demonstrates a complete Data-Driven Automation Testing Framework using **TestNG**, **Apache Maven**, **Apache POI** (Excel reader), and **ExtentReports** (Rich HTML execution reports).

---

## 📁 Project Architecture & Structure

```
TestNG-Maven-Excel-ExtentReport/
├── pom.xml                                   # Maven POM dependencies & build configuration
├── testng.xml                                # TestNG Suite runner configuration
├── src/
│   ├── main/java/com/example/
│   │   ├── utils/
│   │   │   ├── ExcelUtils.java               # Apache POI Excel sheet reader for TestNG DataProvider
│   │   │   ├── ExtentReportManager.java     # Thread-safe ExtentReports & SparkReporter initialization
│   │   │   └── CreateTestData.java          # Utility to programmatically generate testdata.xlsx
│   │   └── listeners/
│   │       └── TestListener.java             # TestNG ITestListener for automated report logging
│   └── test/
│       ├── java/com/example/tests/
│       │   └── DataDrivenTest.java           # Data-Driven TestNG test cases
│       └── resources/
│           └── testdata.xlsx                 # Excel Test Data spreadsheet
└── test-output/                              # Generated Extent HTML Reports
```

---

## 🛠️ Key Framework Components

### 1. **Data-Driven Testing (`ExcelUtils.java`)**
* Uses **Apache POI** (`XSSFWorkbook`, `Sheet`, `Row`, `Cell`, `DataFormatter`) to dynamically parse Excel `.xlsx` spreadsheets.
* Converts sheet records into a 2D `Object[][]` array passed directly to TestNG `@DataProvider`.

### 2. **Extent Reports Integration (`ExtentReportManager.java` & `TestListener.java`)**
* Uses **ExtentReports v5** (`ExtentSparkReporter`) with dark theme styling.
* Integrates seamlessly with TestNG execution lifecycle via `ITestListener`.
* ThreadLocal instance management enables safe parallel execution.

### 3. **Test Execution (`DataDrivenTest.java`)**
* Consumes `@DataProvider(name = "loginData")`.
* Logs step-by-step test execution status (`INFO`, `PASS`, `FAIL`, `SKIP`) directly into ExtentReports.

---

## 🚀 How to Run the Tests

### Option A: Run using Maven Command Line
```bash
mvn clean test
```

### Option B: Run via TestNG XML file in IDE
Right-click `testng.xml` -> **Run 'testng.xml'** in IntelliJ IDEA / Eclipse.

---

## 📊 Viewing Test Reports
After running the tests, navigate to the `test-output/` folder and open `ExtentReport_<timestamp>.html` in any web browser.
