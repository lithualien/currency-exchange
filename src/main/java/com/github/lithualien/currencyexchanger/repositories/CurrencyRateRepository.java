package com.github.lithualien.currencyexchanger.repositories;

import com.github.lithualien.currencyexchanger.domains.CurrencyName;
import com.github.lithualien.currencyexchanger.domains.CurrencyRate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface CurrencyRateRepository extends CrudRepository<CurrencyRate, Long> {

    boolean existsByCurrencyNameAndDate(CurrencyName currencyName, LocalDate localDate);

    @Query("select name.currencyName from CurrencyRate name where name.date = :localDate " +
            "ORDER BY name.currencyName.currencyCode ASC")
    List<CurrencyRate> findAllByDate(LocalDate localDate);

    Optional<CurrencyRate> findByCurrencyNameIdOrderByDateDesc(Long currencyNameId);

}
