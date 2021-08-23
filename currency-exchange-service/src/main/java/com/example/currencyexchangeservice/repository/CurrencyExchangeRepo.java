package com.example.currencyexchangeservice.repository;

import com.example.currencyexchangeservice.entity.CurrencyExchangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CurrencyExchangeRepo extends JpaRepository<CurrencyExchangeEntity, Long> {

    CurrencyExchangeEntity findByFromAndTo(String from, String to);

}
