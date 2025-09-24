package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "test_result")
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String testCaseName;
    private String status;
    private String url;
    private LocalDateTime executedAt;

    public TestResult() {}

    // IMPORTANT: Keep this parameter order: (testCaseName, status, url)
    public TestResult(String testCaseName, String status, String url) {
        this.testCaseName = testCaseName;
        this.status = status;
        this.url = url;
        this.executedAt = LocalDateTime.now();
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTestCaseName() { return testCaseName; }
    public void setTestCaseName(String testCaseName) { this.testCaseName = testCaseName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public LocalDateTime getExecutedAt() { return executedAt; }
    public void setExecutedAt(LocalDateTime executedAt) { this.executedAt = executedAt; }
}
