# Sort and Search Project

A program for testing and comparing the performance of various sorting and searching algorithms.

## Prerequisites

- **Java 11 or higher**
- **Maven 3.6+** (for dependency management and project building)

## Installation and Running

### 1. Clone the repository
```bash
git clone <repository-url>
cd <project-folder>
```

### 2. Build the project with Maven
```bash
mvn clean compile
```

### 3. Run the program
```bash
mvn exec:java -Dexec.mainClass="SortAndSearch.SortAndSearchTester"
```

Alternatively, run via IDE (IntelliJ IDEA):
- Open the project
- Locate `SortAndSearchTester.java`
- Right-click → Run 'SortAndSearchTester.main()'

## Output

When selecting the "Sorting Performance" option, the program generates an Excel file `SortingPerformanceReport.xlsx` in the project root directory with performance charts.