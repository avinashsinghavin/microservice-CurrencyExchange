package com.example.currencyexchangeservice.controller;


import com.example.currencyexchangeservice.entity.CurrencyExchangeEntity;
import com.example.currencyexchangeservice.repository.CurrencyExchangeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private Environment environment;

    @Autowired
    private CurrencyExchangeRepo repo;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchangeEntity retriveExchangeValue(@PathVariable String from, @PathVariable String to) {

        String port = environment.getProperty("local.server.port");
        CurrencyExchangeEntity currencyExchangeEntity = new CurrencyExchangeEntity(1000L, from, to, BigDecimal.valueOf(50), port);

        CurrencyExchangeEntity currRepo = repo.findByFromAndTo(from, to);
        currRepo.setEnvironment(port);

        if(currRepo == null){
            throw new RuntimeException("Data not found in Database");
        }

        return currRepo;
    }

}
