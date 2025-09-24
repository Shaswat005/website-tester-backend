package com.example.demo.controller;

import com.example.demo.entity.TestResult;
import com.example.demo.service.SeleniumTestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tests")
@CrossOrigin(origins = "http://localhost:3000")
public class TestController {

    private final SeleniumTestService seleniumTestService;

    public TestController(SeleniumTestService seleniumTestService) {
        this.seleniumTestService = seleniumTestService;
    }

    @PostMapping("/run")
    public List<TestResult> runTests(@RequestBody UrlRequest request) {
        return seleniumTestService.runTests(request.getUrl());
    }

    public static class UrlRequest {
        private String url;
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }
    @RestController
    @RequestMapping("/selenium")
    public class SeleniumController {

        private final SeleniumTestService service;

        public SeleniumController(SeleniumTestService service) {
            this.service = service;
        }

        @GetMapping("/run")
        public List<TestResult> run(@RequestParam String url) {
            return service.runTests(url);
        }
    }

}
