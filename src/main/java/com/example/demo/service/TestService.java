package com.example.demo.service;

import com.example.demo.entity.TestResult;
import com.example.demo.repository.TestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestResultRepository testResultRepository;

    // Simulate running tests (in real project: Selenium, RestAssured, etc.)
    public List<TestResult> runTests(String url) {
        List<TestResult> results = new ArrayList<>();

        // Example test cases
        results.add(new TestResult("Homepage Load", "PASS", url));
        results.add(new TestResult("Login Page", "FAIL", url));
        results.add(new TestResult("Search Function", "PASS", url));

        // Save results to DB
        testResultRepository.saveAll(results);

        return results;
    }
}
