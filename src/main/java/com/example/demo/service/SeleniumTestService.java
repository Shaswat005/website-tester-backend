package com.example.demo.service;

import com.example.demo.entity.TestResult;
import com.example.demo.repository.TestResultRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeleniumTestService {

    private final TestResultRepository repo;

    public SeleniumTestService(TestResultRepository repo) {
        this.repo = repo;
    }

    public List<TestResult> runTests(String url) {
        List<TestResult> results = new ArrayList<>();

        // Setup Chrome driver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // Test 1: Homepage load
            driver.get(url);
            Thread.sleep(2000); // wait so you can see
            if (driver.getTitle() != null && !driver.getTitle().isEmpty()) {
                results.add(new TestResult("Homepage Load Test", "PASS", url));
            } else {
                results.add(new TestResult("Homepage Load Test", "FAIL", url));
            }

            // Test 2: Fake Login Test (for demo)
            try {
                driver.findElement(By.name("username")).sendKeys("testuser");
                driver.findElement(By.name("password")).sendKeys("testpass");
                driver.findElement(By.name("login")).click();
                Thread.sleep(2000);
                results.add(new TestResult("Login Test", "PASS",url ));
            } catch (Exception e) {
                results.add(new TestResult("Login Test", "FAIL" ,url));
            }

         // Test 3: Google Search test
            try {
                driver.get("https://www.google.com");
                Thread.sleep(2000); // wait for page to load

                // Type into the search bar
                driver.findElement(By.name("q")).sendKeys("Spring Boot Selenium");

                // Submit instead of clicking button
                driver.findElement(By.name("q")).submit();
                Thread.sleep(3000); // wait for results

                results.add(new TestResult("Google Search Test", "PASS", url));
            } catch (Exception e) {
                results.add(new TestResult("Google Search Test", "FAIL",url ));
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            driver.quit(); // close Chrome after tests
        }

        repo.saveAll(results);
        return results;
    }
}
