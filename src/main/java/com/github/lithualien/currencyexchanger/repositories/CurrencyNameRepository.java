package com.github.lithualien.currencyexchanger.repositories;

import com.github.lithualien.currencyexchanger.domains.CurrencyName;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface CurrencyNameRepository extends CrudRepository<CurrencyName, Long> {

    Optional<CurrencyName> findByCurrencyCode(String currencyCode);

    Boolean existsByCurrencyCode(String currencyCode);

}
