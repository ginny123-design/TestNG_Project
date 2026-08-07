# TestNG + Maven + Excel Data Driven + Extent Reports Project

This project demonstrates a complete Data-Driven Automation Testing Framework using **TestNG**, **Apache Maven**, **Apache POI** (Excel reader), **Selenium WebDriver**, and **ExtentReports** (Rich HTML execution reports).

---

## 📁 Project Architecture & Structure

```
TestNG-Maven-Excel-ExtentReport/
├── pom.xml                                   # Maven POM dependencies & build configuration
├── testng.xml                                # TestNG Suite runner configuration
├── Jenkinsfile                               # Jenkins CI/CD Pipeline Script
├── README.md                                 # Framework Documentation
├── .github/workflows/maven-tests.yml         # GitHub Actions Workflow
├── src/
│   ├── main/java/com/example/
│   │   ├── utils/
│   │   │   ├── ExcelUtils.java               # Apache POI Excel sheet reader for TestNG DataProvider
│   │   │   ├── ExtentReportManager.java     # Thread-safe ExtentReports & SparkReporter initialization
│   │   │   ├── ScreenshotUtils.java          # WebDriver screenshot capture utility
│   │   │   └── CreateTestData.java          # Utility to programmatically generate testdata.xlsx
│   └── test/
│       ├── java/com/example/
│       │   ├── listeners/
│       │   │   └── TestListener.java         # TestNG ITestListener for automated report logging
│       │   └── tests/
│       │       └── DataDrivenTest.java       # Web Automation Data-Driven TestNG test cases
│       └── resources/
│           └── testdata.xlsx                 # Excel Test Data spreadsheet
└── test-output/                              # Generated Extent HTML Reports & Screenshots
```

---

## 🛠️ Key Framework Components

### 1. **Data-Driven Web Automation (`DataDrivenTest.java` & `ExcelUtils.java`)**
* Uses **Apache POI** (`XSSFWorkbook`, `Sheet`, `Row`, `Cell`, `DataFormatter`) to dynamically parse Excel `.xlsx` spreadsheets.
* Converts sheet records into a 2D `Object[][]` array passed directly to TestNG `@DataProvider`.
* Executes Selenium WebDriver tests against `https://practicetestautomation.com/practice-test-login/`.

### 2. **Extent Reports Integration (`ExtentReportManager.java` & `TestListener.java`)**
* Uses **ExtentReports v5** (`ExtentSparkReporter`) with dark theme styling.
* Integrates seamlessly with TestNG execution lifecycle via `ITestListener`.
* Automatically captures browser screenshots and embeds them directly inside Extent Reports.

---

## 🚀 How to Run the Tests

### Option A: Run using Maven Wrapper (Command Line)
```bash
# Run in Headed (GUI) mode
./mvnw clean test -Dheadless=false

# Run in Headless mode
./mvnw clean test -Dheadless=true
```

---

## 🔄 CI/CD Integration
- **GitHub Actions**: Configured under `.github/workflows/maven-tests.yml`
- **Jenkins Pipeline**: Configured via `Jenkinsfile` and GitHub Webhook trigger
