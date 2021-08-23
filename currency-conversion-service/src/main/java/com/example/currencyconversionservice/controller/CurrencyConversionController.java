package com.example.currencyconversionservice.controller;


import com.example.currencyconversionservice.entity.CurrencyConversionEntity;
import com.example.currencyconversionservice.proxy.CurrencyExchangeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeProxy proxy;

    @GetMapping("/currency-conversion/form/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionEntity calculateCurrencyConversion(
            @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

        HashMap<String, String> uriVariable = new HashMap<>();
        uriVariable.put("from", from);
        uriVariable.put("to", to);

        ResponseEntity<CurrencyConversionEntity> responseEntity = new RestTemplate().getForEntity(
                "http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionEntity.class, uriVariable);

        CurrencyConversionEntity cu = responseEntity.getBody();

        return new CurrencyConversionEntity(
                1000L, from, to, quantity, cu.getConversionMultiple(),
                quantity.multiply(cu.getConversionMultiple()), cu.getEnvironment()+" - local");
    }

    @GetMapping("/currency-conversion-feign/form/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionEntity calculateCurrencyConversionFeign(
            @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

        CurrencyConversionEntity cu = proxy.retriveExchangeValue(from, to);
        return new CurrencyConversionEntity(
                1000L, from, to, quantity, cu.getConversionMultiple(),
                quantity.multiply(cu.getConversionMultiple()), cu.getEnvironment()+" - feign");
    }

}
