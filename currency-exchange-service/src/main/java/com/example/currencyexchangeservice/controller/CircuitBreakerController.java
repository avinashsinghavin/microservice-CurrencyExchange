package com.example.currencyexchangeservice.controller;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    @GetMapping("/sample-api")
//    @Retry(name = "sample-api", fallbackMethod = "hardCodeResponse") // Name match with application.property
    @CircuitBreaker(name = "samename", fallbackMethod = "hardCodeResponse")
    @RateLimiter(name = "samename")
    public String sampleApi() {
        System.out.println("Trying Api Connection");
        ResponseEntity<String> responseEntity = new RestTemplate().getForEntity(
          "http://localhost:8909/dummytest-notActualAPi", String.class
        );
        return responseEntity.getBody();
    }

    public String hardCodeResponse(Exception ex) {
        return "Hard Code Value! Fall Back.";
    }

}
