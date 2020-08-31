package com.github.lithualien.currencyexchanger.services;

import reactor.core.publisher.Mono;

public interface LBService {

    Mono<Void> addCurrencies(String code);

    Mono<Void> addCurrencyRates();
}
