package com.github.lithualien.currencyexchanger.repositories;

import com.github.lithualien.currencyexchanger.domains.CurrencyName;
import com.github.lithualien.currencyexchanger.domains.CurrencyRate;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import java.time.LocalDate;

@org.springframework.stereotype.Repository
public interface CurrencyRateRepository extends ReactiveCrudRepository<CurrencyRate, Long> {

    Mono<Boolean> existsByCurrencyNameAndDate(CurrencyName currencyName, LocalDate localDate);

    Mono<CurrencyRate> findFirstByCurrencyNameIdOrderByDateDesc(Long currencyNameId);

}
