package com.github.lithualien.currencyexchanger.services;

import com.github.lithualien.currencyexchanger.commands.lbxml.LBCurrencyNameDataCommand;
import com.github.lithualien.currencyexchanger.commands.v1.CurrencyCommand;
import com.github.lithualien.currencyexchanger.domains.CurrencyName;

import java.util.List;

public interface CurrencyNameService {

    List<CurrencyCommand> getCurrencies();
    CurrencyName getCurrencyNameByCode(String currencyName);

    void save(LBCurrencyNameDataCommand nameDataCommand);

}
