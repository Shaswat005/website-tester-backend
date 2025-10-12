package com.example.demo.controller;

import com.example.demo.entity.TestResult;
import com.example.demo.service.SeleniumTestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tests")
@CrossOrigin(origins = {
        "https://website-tester-backend.onrender.com",
        "http://localhost:3000"
})
public class TestController {

    private final SeleniumTestService seleniumTestService;

    public TestController(SeleniumTestService seleniumTestService) {
        this.seleniumTestService = seleniumTestService;
    }

    @PostMapping("/run")
    public ResponseEntity<?> runTests(@RequestBody UrlRequest request) {
        try {
            List<TestResult> results = seleniumTestService.runTests(request.getUrl());
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            e.printStackTrace(); // logs on Render
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    public static class UrlRequest {
        private String url;
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }
}
