package com.github.lithualien.currencyexchanger.repositories;

import com.github.lithualien.currencyexchanger.domains.CurrencyName;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface CurrencyNameRepository extends Repository<CurrencyName, Long> {



}
