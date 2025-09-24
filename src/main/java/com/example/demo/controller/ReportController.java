package com.example.demo.controller;

import com.example.demo.service.ReportService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "http://localhost:3000")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadReport() {
        ByteArrayInputStream in = reportService.generateExcelReport();
        byte[] bytes = in.readAllBytes(); // Java 11+: no checked exception here

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + reportService.generateTimestampedFileName());

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bytes);
    }
}
