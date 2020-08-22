package com.github.lithualien.currencyexchanger.services.impl;

import com.github.lithualien.currencyexchanger.commands.lbxml.LBCurrencyValueCommand;
import com.github.lithualien.currencyexchanger.converters.LBCurrencyValueCommandToCurrencyRate;
import com.github.lithualien.currencyexchanger.domains.CurrencyName;
import com.github.lithualien.currencyexchanger.domains.CurrencyRate;
import com.github.lithualien.currencyexchanger.exceptions.ResourceNotFoundException;
import com.github.lithualien.currencyexchanger.repositories.CurrencyRateRepository;
import com.github.lithualien.currencyexchanger.services.CurrencyNameService;
import com.github.lithualien.currencyexchanger.services.CurrencyRateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class CurrencyRateServiceImpl implements CurrencyRateService {

    private final CurrencyRateRepository repository;
    private final LBCurrencyValueCommandToCurrencyRate converter;
    private final CurrencyNameService currencyNameService;

    public CurrencyRateServiceImpl(LBCurrencyValueCommandToCurrencyRate converter,
                                   CurrencyRateRepository repository, CurrencyNameService currencyNameService) {
        this.repository = repository;
        this.converter = converter;
        this.currencyNameService = currencyNameService;
    }

    @Transactional
    @Override
    public void save(LBCurrencyValueCommand valueCommand, LocalDate date) throws ResourceNotFoundException {

        CurrencyName currencyName;

        try {
            currencyName = currencyNameService.getCurrencyNameByCode(valueCommand.getName());
        }
        catch (ResourceNotFoundException ex) {
            throw new ResourceNotFoundException("New currency found");
        }

        if(repository.existsByCurrencyNameAndDate(currencyName, date)) {
            return;
        }

        CurrencyRate currencyRate = converter.convert(valueCommand, currencyName, date);

        repository.save(currencyRate);

    }
}
