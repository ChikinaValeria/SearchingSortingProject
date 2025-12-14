package SortAndSearch;

import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import org.apache.poi.xddf.usermodel.chart.*;

import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A utility class to export sorting performance data to an Excel file with charts.
 * Uses modern Apache POI XDDF classes for chart generation.
 */
public class ExcelExporter {

    private static final Logger LOGGER = Logger.getLogger(ExcelExporter.class.getName());
    private static final String FILE_NAME = "SortingPerformanceReport.xlsx";

    /**
     * Exports the collected sorting performance data to an Excel file with two charts.
     * Assumes the 'results' array structure:
     * Rows: 0:Bubble Comp, 1:Bubble MS, 2:Selection Comp, 3:Selection MS, 4:Merge Comp, 5:Merge MS, 6:Quick Comp, 7:Quick MS.
     * @param sizes The array of data set sizes (X-axis for both charts).
     * @param results The 2D array of performance results [8 rows][size count].
     */
    public static void exportDataWithCharts(int[] sizes, long[][] results) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Performance Data");

            // 1. Write Data to the Sheet
            writeDataToSheet(sheet, sizes, results);

            // 2. Create the Time Chart (Algorithms' Execution Time Graph)
            createTimeChart(sheet, sizes.length);

            // 3. Create the Comparison Count Chart (Algorithms' Operation Count Graph)
            createComparisonChart(sheet, sizes.length);

            // Save the workbook
            try (FileOutputStream fileOut = new FileOutputStream(FILE_NAME)) {
                workbook.write(fileOut);
                System.out.println("Success: Excel report with charts created at: " + FILE_NAME);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error creating or writing to Excel file.", e);
            System.out.println("Error: Could not create Excel file. Check file permissions or Apache POI dependencies.");
        }
    }

    /**
     * Writes the header and performance data to the Excel sheet.
     * Data is ordered for chart series selection (Time rows first, then Comparison rows).
     */
    private static void writeDataToSheet(XSSFSheet sheet, int[] sizes, long[][] results) {
        // Header Row (Row 0)
        String[] headers = {"Algorithm/Metric"};
        for (int size : sizes) {
            headers = java.util.Arrays.copyOf(headers, headers.length + 1);
            headers[headers.length - 1] = String.valueOf(size);
        }

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        // Data Rows (Row 1 to 8)
        String[] rowNames = {
                "BubbleSort, ms", "SelectionSort, ms", "MergeSort, ms", "QuickSort, ms",
                "BubbleSort, comparisons", "SelectionSort, comparisons", "MergeSort, comparisons", "QuickSort, comparisons"
        };

        // Data indices from the 'results' array: MS (1, 3, 5, 7) then Comp (0, 2, 4, 6)
        int[] dataIndices = {1, 3, 5, 7, 0, 2, 4, 6};

        for (int i = 0; i < rowNames.length; i++) {
            Row row = sheet.createRow(i + 1); // Excel rows 1 through 8
            row.createCell(0).setCellValue(rowNames[i]); // Row label

            int dataRowIndex = dataIndices[i]; // Index in the 'results' array

            for (int j = 0; j < sizes.length; j++) {
                // j+1 is the column index
                row.createCell(j + 1).setCellValue(results[dataRowIndex][j]);
            }
        }

        // Auto-size columns for better readability
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    /**
     * Creates and configures the Algorithms' Execution Time Graph (Rows 1-4, MS data).
     */
    private static void createTimeChart(Sheet sheet, int dataSize) {
        XSSFSheet xssfSheet = (XSSFSheet) sheet;
        XSSFDrawing drawing = xssfSheet.createDrawingPatriarch();

        // Define anchor for the chart (top-left: K1, bottom-right: V20)
        ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 10, 1, 21, 20);

        XSSFChart chart = drawing.createChart(anchor);
        chart.setTitleText("Algorithms' Execution Time Graph");
        chart.setTitleOverlay(false);

        // **FIX: Create axes BEFORE creating chart data**
        XDDFCategoryAxis catAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        catAxis.setTitle("Data Amount");

        XDDFValueAxis valAxis = chart.createValueAxis(AxisPosition.LEFT);
        valAxis.setTitle("Milliseconds");

        // Now create chart data with the axes
        XDDFLineChartData data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, catAxis, valAxis);
        data.setVaryColors(false);

        // X-axis data source (Row 0, Columns 1 to dataSize)
        XDDFNumericalDataSource<Double> xs = XDDFDataSourcesFactory.fromNumericCellRange(xssfSheet,
                new CellRangeAddress(0, 0, 1, dataSize));

        // Add series for MS data (Excel Rows 1, 2, 3, 4)
        addXddfSeries(data, xssfSheet, xs, 1, dataSize); // BubbleSort, ms
        addXddfSeries(data, xssfSheet, xs, 2, dataSize); // SelectionSort, ms
        addXddfSeries(data, xssfSheet, xs, 3, dataSize); // MergeSort, ms
        addXddfSeries(data, xssfSheet, xs, 4, dataSize); // QuickSort, ms

        chart.plot(data);

        // Ensure the chart's legend is visible
        chart.getOrAddLegend().setPosition(LegendPosition.BOTTOM);
    }

    /**
     * Creates and configures the Algorithms' Operation Count Graph (Rows 5-8, Comparison data).
     */
    private static void createComparisonChart(Sheet sheet, int dataSize) {
        XSSFSheet xssfSheet = (XSSFSheet) sheet;
        XSSFDrawing drawing = xssfSheet.createDrawingPatriarch();

        // Define anchor for the chart (top-left: K21, bottom-right: V40)
        ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 10, 21, 21, 40);

        XSSFChart chart = drawing.createChart(anchor);
        chart.setTitleText("Algorithms' Operation Count Graph");
        chart.setTitleOverlay(false);

        // **FIX: Create axes BEFORE creating chart data**
        XDDFCategoryAxis catAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        catAxis.setTitle("Data Amount");

        XDDFValueAxis valAxis = chart.createValueAxis(AxisPosition.LEFT);
        valAxis.setTitle("Comparison Count");

        // Now create chart data with the axes
        XDDFLineChartData data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, catAxis, valAxis);
        data.setVaryColors(false);

        // X-axis data source (Row 0, Columns 1 to dataSize)
        XDDFNumericalDataSource<Double> xs = XDDFDataSourcesFactory.fromNumericCellRange(xssfSheet,
                new CellRangeAddress(0, 0, 1, dataSize));

        // Add series for Comparison data (Excel Rows 5, 6, 7, 8)
        addXddfSeries(data, xssfSheet, xs, 5, dataSize); // BubbleSort, comparisons
        addXddfSeries(data, xssfSheet, xs, 6, dataSize); // SelectionSort, comparisons
        addXddfSeries(data, xssfSheet, xs, 7, dataSize); // MergeSort, comparisons
        addXddfSeries(data, xssfSheet, xs, 8, dataSize); // QuickSort, comparisons

        chart.plot(data);

        chart.getOrAddLegend().setPosition(LegendPosition.BOTTOM);
    }

    /**
     * Helper method to add a series to a chart using XDDFDataSource.
     * @param data The chart data object.
     * @param sheet The sheet containing the data.
     * @param xs The X-axis data source (Data Amount).
     * @param rowIdx The Excel row index for the Y-axis data (1 to 8).
     * @param dataSize The number of data points.
     */
    private static void addXddfSeries(XDDFChartData data, XSSFSheet sheet, XDDFNumericalDataSource<Double> xs, int rowIdx, int dataSize) {
        // Y-axis data series (Row rowIdx, Columns 1 to dataSize)
        XDDFNumericalDataSource<Double> ys = XDDFDataSourcesFactory.fromNumericCellRange(sheet,
                new CellRangeAddress(rowIdx, rowIdx, 1, dataSize));

        // Get the series title from the first cell of the current row and extract only algorithm name
        String fullTitle = sheet.getRow(rowIdx).getCell(0).getStringCellValue();
        // Extract only the algorithm name (before the comma)
        String seriesTitle = fullTitle.contains(",") ? fullTitle.split(",")[0] : fullTitle;

        XDDFChartData.Series series = data.addSeries(xs, ys);
        series.setTitle(seriesTitle, null);
    }
}