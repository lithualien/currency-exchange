package com.github.lithualien.currencyexchanger.repositories;

import com.github.lithualien.currencyexchanger.domains.CurrencyRate;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface CurrencyRateRepository extends Repository<CurrencyRate, Long> {



}
