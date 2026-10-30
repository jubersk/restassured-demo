***

```markdown
# 🚀 REST Assured API Testing Framework - Master Guide

Welcome to the REST Assured API Testing Framework. This document serves as the master guide for the project, detailing prerequisites, project structure, execution steps, and CI/CD integrations. 

---

## 📋 Step 1: Prerequisites

Ensure the following tools are installed on your machine before proceeding:
*   **Java Development Kit (JDK) 11** or higher (`java -version`).
*   **Apache Maven** (`mvn -v`).
*   **IDE** (IntelliJ IDEA recommended).
*   **Git** (for version control).

---

## 🛠️ Step 2: Environment Setup & Dependencies

1.  **Project Initialization**: Create a new Maven project in your IDE with Java 11.
2.  **`pom.xml` Configuration**: 
    *   Refer to the `pom.xml` in the project root.
    *   It includes dependencies for: `rest-assured`, `testng`, `hamcrest`, `jackson-databind` (for JSON serialization), and `allure-testng` (for reporting).
    *   It includes the `maven-surefire-plugin` configured with the `io.qameta.allure.testng.AllureTestNg` listener to generate raw results during test execution.
    *   It includes the `allure-maven` plugin configured with `<singleFile>true</singleFile>`. This is crucial as it allows you to open the generated HTML report directly without needing a local web server.
    *   *Note: All test dependencies must have `<scope>test</scope>`.*

---

## 📁 Step 3: Framework Structure

The project follows a standard Maven directory structure. All test automation code is placed under `src/test/java`.

```text
restassured-demo/
├── pom.xml
├── run-tests.bat                  # Windows execution script
├── run-tests.sh                   # Linux/CI execution script
└── src/
    └── test/
        ├── java/
        │   ├── base/
        │   │   └── BaseTest.java          # Base class for RestAssured setup & RequestSpec
        │   ├── models/
        │   │   └── User.java              # POJO classes for object mapping
        │   ├── tests/
        │   │   ├── GetRequestTest.java    # Extends BaseTest
        │   │   ├── PostRequestTest.java   # Extends BaseTest
        │   │   ├── PutPatchDeleteTest.java# Extends BaseTest
        │   │   └── AuthenticationTest.java# Extends BaseTest
        │   └── utils/
        │       ├── JsonDataReader.java    # Utility to read external JSON test data
        │       └── UserDataProvider.java  # TestNG DataProvider for parameterized tests
        └── resources/
            └── testdata.json              # External test data file
```

---

## 💻 Step 4: Core Code Implementation (References)

*   **Base Class (`base.BaseTest`)**: Handles common setup. It sets the `baseURI` (currently `https://httpbin.org`), configures `RequestSpecification` with content-type JSON, and attaches request/response logging filters. It contains a `getRequestSpec()` method for tests to use.
*   **Utilities (`utils.JsonDataReader`)**: Reads `testdata.json` from the resources folder and caches it. Uses Jackson `ObjectMapper` to parse the data.
*   **Test Classes (`tests.*`)**: Extend `BaseTest` and use `getRequestSpec()` to execute API calls. They utilize Hamcrest matchers (`equalTo`, `containsString`) for assertions. Some use `@DataProvider` from `UserDataProvider` for data-driven testing.

---

## 🚀 Step 5: Execution & Scripts

You can run the tests and generate reports using any of the following methods:

### Option A: IDE
Right-click on any test class or the `tests` folder in IntelliJ and select **Run**.

### Option B: Maven Command (Single Command)
To run all tests and generate the Allure report in one go, execute this in the project root:
```bash
mvn clean test allure:report
```

### Option C: Execution Scripts
*   **Windows**: Double-click `run-tests.bat` or run `.\run-tests.bat` in the terminal. It runs the Maven command, stops if any test fails, and informs you where the report is located.
*   **Linux/Mac**: Run `./run-tests.sh` in the terminal.

---

## 📊 Step 6: Viewing the Allure Report

Thanks to the `<singleFile>true</singleFile>` configuration in `pom.xml`:
1.  Navigate to the `target/site/allure-maven-plugin/` directory.
2.  Locate `index.html`.
3.  **Double-click `index.html`**. It will open a fully interactive dashboard in your browser showing test metrics, suites, and execution times. (No local server or `allure serve` command required).

---

## ⚙️ Step 7: CI/CD Integration

The framework is pre-configured for continuous integration.

### Jenkins Pipeline
Use the `run-tests.sh` script. The Allure Jenkins Plugin can automatically parse the `target/allure-results` directory to display the report directly on the Jenkins dashboard.

```groovy
pipeline {
    agent any
    stages {
        stage('API Tests') {
            steps {
                sh './run-tests.sh'
            }
        }
        stage('Publish Allure') {
            steps {
                allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
            }
        }
    }
}
```

### GitHub Actions (`.github/workflows/api-tests.yml`)
The provided workflow runs the Maven command and uploads the single-file HTML report as an artifact. You can download it from the Actions tab even if the tests fail.

```yaml
name: API Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: '11'
          cache: maven
      - name: Run Tests and Generate Report
        run: mvn clean test allure:report
      - name: Upload Report
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: Allure-Report
          path: target/site/allure-maven-plugin/
```

---

## 💡 Step 8: Troubleshooting & Best Practices

*   **Duplicate Class Errors**: Ensure there are no duplicate classes (e.g., two `BaseTest` files). All Java files must reside in the correct package folder (e.g., `package tests;` must be inside `src/test/java/tests/`).
*   **Blank Report**: If the report is blank, ensure you ran `mvn clean test` *before* generating the report. Also, verify `<singleFile>true</singleFile>` is present in `pom.xml`.
*   **JSON Serializer Error**: If you see `Cannot serialize object because no JSON serializer found`, ensure `jackson-databind` is in your `pom.xml`.
*   **Missing TestNG Annotations**: TestNG is test-scoped. If you cannot import `@DataProvider` or `@Test`, ensure your class is inside `src/test/java` and not `src/main/java`.
```