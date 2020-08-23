package com.github.lithualien.currencyexchanger.services;

import com.github.lithualien.currencyexchanger.commands.lbxml.LBCurrencyValueCommand;
import com.github.lithualien.currencyexchanger.commands.v1.CurrencyInputCommand;
import com.github.lithualien.currencyexchanger.commands.v1.CurrencyOutputCommand;
import com.github.lithualien.currencyexchanger.domains.CurrencyRate;
import com.github.lithualien.currencyexchanger.exceptions.ResourceNotFoundException;

import java.time.LocalDate;

public interface CurrencyRateService {

    CurrencyOutputCommand getCurrencyValue(CurrencyInputCommand currencyInputCommand);

    CurrencyRate save(LBCurrencyValueCommand rateDataCommand, LocalDate date) throws ResourceNotFoundException;

}
