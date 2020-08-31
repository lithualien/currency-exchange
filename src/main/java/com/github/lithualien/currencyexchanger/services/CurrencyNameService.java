package com.github.lithualien.currencyexchanger.services;

import com.github.lithualien.currencyexchanger.commands.lbxml.LBCurrencyNameDataCommand;
import com.github.lithualien.currencyexchanger.commands.v1.CurrencyCommand;
import com.github.lithualien.currencyexchanger.domains.CurrencyName;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CurrencyNameService {

    List<CurrencyCommand> getCurrencies();

    CurrencyName getCurrencyNameByCode(String currencyName);

    Mono<CurrencyName> save(LBCurrencyNameDataCommand nameDataCommand);

}
