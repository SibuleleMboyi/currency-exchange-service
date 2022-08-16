package com.in28minutes.microservices.currencyexchangeservice.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.retry.annotation.Retry;


@RestController
public class CircuitBreakerController {

    
    @Autowired(required =  false)
    private Logger logger = LogManager.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
    @Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")
    public String sampleApi() {

        logger.info("Sample API call recieved");
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("htt://localhost:8000/some-dummy",
                String.class);
        return forEntity.getBody();
    }

    public String hardcodedResponse(Exception exception){
        return "fallback-response";
    }
}