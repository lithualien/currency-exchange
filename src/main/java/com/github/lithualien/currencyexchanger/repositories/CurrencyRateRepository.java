package com.github.lithualien.currencyexchanger.repositories;

import com.github.lithualien.currencyexchanger.domains.CurrencyName;
import com.github.lithualien.currencyexchanger.domains.CurrencyRate;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface CurrencyRateRepository extends CrudRepository<CurrencyRate, Long> {

    boolean existsByCurrencyNameAndDate(CurrencyName currencyName, LocalDate localDate);

    Optional<CurrencyRate> findFirstByCurrencyNameIdOrderByDateDesc(Long currencyNameId);

}
