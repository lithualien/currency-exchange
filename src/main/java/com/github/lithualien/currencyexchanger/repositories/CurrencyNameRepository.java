package com.github.lithualien.currencyexchanger.repositories;

import com.github.lithualien.currencyexchanger.domains.CurrencyName;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface CurrencyNameRepository extends CrudRepository<CurrencyName, Long> {

    Optional<CurrencyName> findByCurrencyCode(String currencyCode);

    @Query("select name.currencyName from CurrencyRate name where name.date = :localDate " +
            "ORDER BY name.currencyName.currencyCode ASC")
    List<CurrencyName> findAllByDate(LocalDate localDate);

}
