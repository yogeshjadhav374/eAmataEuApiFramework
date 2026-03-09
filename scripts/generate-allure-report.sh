#!/bin/bash
# Generate and serve Allure report for eAmata API Automation
# Usage: ./scripts/generate-allure-report.sh [--serve] [--zip]

RESULTS_DIR="allure-results"
REPORT_DIR="allure-report"

echo "=== eAmata API Automation - Allure Report Generator ==="

# Check if allure CLI is installed
if ! command -v allure &> /dev/null; then
    echo "Allure CLI not found. Installing via npm..."
    npm install -g allure-commandline
fi

# Check if results exist
if [ ! -d "$RESULTS_DIR" ]; then
    echo "Error: No allure-results directory found."
    echo "Run tests first: mvn clean test"
    exit 1
fi

# Generate report
echo "Generating Allure report..."
allure generate "$RESULTS_DIR" --clean -o "$REPORT_DIR"

if [ $? -eq 0 ]; then
    echo "Report generated successfully at: $REPORT_DIR"
else
    echo "Failed to generate report."
    exit 1
fi

# Optional: Create ZIP for distribution
if [ "$1" == "--zip" ] || [ "$2" == "--zip" ]; then
    ZIP_NAME="allure-report-$(date +%Y%m%d_%H%M%S).zip"
    echo "Creating ZIP archive: $ZIP_NAME"

    if command -v zip &> /dev/null; then
        zip -r "$ZIP_NAME" "$REPORT_DIR/"
    elif command -v powershell &> /dev/null; then
        powershell -command "Compress-Archive -Path '$REPORT_DIR\*' -DestinationPath '$ZIP_NAME' -Force"
    fi

    echo "ZIP created: $ZIP_NAME"
fi

# Optional: Serve report
if [ "$1" == "--serve" ] || [ "$2" == "--serve" ]; then
    echo "Opening Allure report in browser..."
    allure open "$REPORT_DIR" --port 9090
fi

echo "=== Done ==="
