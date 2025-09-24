package com.example.demo.service;

import com.example.demo.entity.TestResult;
import com.example.demo.repository.TestResultRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReportService {

    private final TestResultRepository repo;

    public ReportService(TestResultRepository repo) {
        this.repo = repo;
    }

    public ByteArrayInputStream generateExcelReport() {
        List<TestResult> results = repo.findAll();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Test Results");

            // Timestamp row at top
            Row tsRow = sheet.createRow(0);
            tsRow.createCell(0).setCellValue("Generated on:");
            tsRow.createCell(1).setCellValue(timestamp);

            // Header row (row index 1)
            Row header = sheet.createRow(1);
            header.createCell(0).setCellValue("Test Case Name");
            header.createCell(1).setCellValue("Status");
            header.createCell(2).setCellValue("URL");

            // Data rows start at row index 2
            int rowIdx = 2;
            for (TestResult r : results) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(r.getTestCaseName());
                row.createCell(1).setCellValue(r.getStatus());
                row.createCell(2).setCellValue(r.getUrl());
            }

            // Autosize columns
            for (int i = 0; i < 3; i++) sheet.autoSizeColumn(i);

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate Excel report", e);
        }
    }

    public String generateTimestampedFileName() {
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return "test-report-" + ts + ".xlsx";
    }
}
