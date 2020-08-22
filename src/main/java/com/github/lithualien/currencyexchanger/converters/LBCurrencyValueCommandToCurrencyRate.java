package com.github.lithualien.currencyexchanger.converters;

import com.github.lithualien.currencyexchanger.commands.lbxml.LBCurrencyValueCommand;
import com.github.lithualien.currencyexchanger.domains.CurrencyName;
import com.github.lithualien.currencyexchanger.domains.CurrencyRate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LBCurrencyValueCommandToCurrencyRate {

    public CurrencyRate convert(LBCurrencyValueCommand source, CurrencyName currencyName, LocalDate date) {

        CurrencyRate currencyRate = new CurrencyRate();

        currencyRate.setDate(date);
        currencyRate.setRate(source.getValue());
        currencyRate.setCurrencyName(currencyName);
        return currencyRate;
    }

}
