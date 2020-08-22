package com.github.lithualien.currencyexchanger.services;

import com.github.lithualien.currencyexchanger.commands.lbxml.LBCurrencyNameDataCommand;
import com.github.lithualien.currencyexchanger.domains.CurrencyName;

public interface CurrencyNameService {

    void save(LBCurrencyNameDataCommand nameDataCommand);

    CurrencyName getCurrencyNameByCode(String currencyName);

}
