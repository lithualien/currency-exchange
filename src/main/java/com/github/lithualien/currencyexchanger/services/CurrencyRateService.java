package com.github.lithualien.currencyexchanger.services;

import com.github.lithualien.currencyexchanger.commands.lbxml.LBCurrencyRateDataCommand;
import com.github.lithualien.currencyexchanger.commands.lbxml.LBCurrencyValueCommand;
import com.github.lithualien.currencyexchanger.commands.v1.CurrencyInputCommand;
import com.github.lithualien.currencyexchanger.commands.v1.CurrencyOutputCommand;
import com.github.lithualien.currencyexchanger.domains.CurrencyRate;
import com.github.lithualien.currencyexchanger.exceptions.ResourceNotFoundException;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface CurrencyRateService {

    CurrencyOutputCommand getCurrencyValue(CurrencyInputCommand currencyInputCommand);

    Mono<CurrencyRate> save(LBCurrencyRateDataCommand rateDataCommand, LocalDate date) throws ResourceNotFoundException;

}
