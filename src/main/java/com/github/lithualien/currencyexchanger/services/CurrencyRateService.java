package com.github.lithualien.currencyexchanger.services;

import com.github.lithualien.currencyexchanger.commands.lbxml.LBCurrencyValueCommand;
import com.github.lithualien.currencyexchanger.exceptions.ResourceNotFoundException;

import java.time.LocalDate;

public interface CurrencyRateService {

    void save(LBCurrencyValueCommand rateDataCommand, LocalDate date) throws ResourceNotFoundException;

}
