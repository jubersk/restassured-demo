#!/usr/bin/env bash

# Exit immediately if a command fails
set -e

echo "========================================"
echo "Step 1: Clean and run tests, generate report"
echo "========================================"
mvn clean test allure:report

echo "========================================"
echo "Done!"
echo "Report generated at: target/site/allure-maven-plugin/index.html"
echo "========================================"