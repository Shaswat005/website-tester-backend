package com.example.demo.controller;

import com.example.demo.entity.TestResult;
import com.example.demo.service.SeleniumTestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
