package com.github.lithualien.currencyexchanger.repositories;

import com.github.lithualien.currencyexchanger.domains.CurrencyName;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDate;

@org.springframework.stereotype.Repository
public interface CurrencyNameRepository extends ReactiveCrudRepository<CurrencyName, Long> {

    Mono<CurrencyName> findByCurrencyCode(String currencyCode);

    @Query("select name.currencyName from CurrencyRate name where name.date = :localDate " +
            "ORDER BY name.currencyName.currencyCode ASC")
    Flux<CurrencyName> findAllByDate(LocalDate localDate);

}
