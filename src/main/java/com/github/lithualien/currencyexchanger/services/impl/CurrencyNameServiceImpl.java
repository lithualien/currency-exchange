package com.github.lithualien.currencyexchanger.services.impl;

import com.github.lithualien.currencyexchanger.commands.lbxml.LBCurrencyNameDataCommand;
import com.github.lithualien.currencyexchanger.converters.LBCurrencyNameDataCommandToCurrencyName;
import com.github.lithualien.currencyexchanger.domains.CurrencyName;
import com.github.lithualien.currencyexchanger.exceptions.ResourceNotFoundException;
import com.github.lithualien.currencyexchanger.repositories.CurrencyNameRepository;
import com.github.lithualien.currencyexchanger.services.CurrencyNameService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CurrencyNameServiceImpl implements CurrencyNameService {

    private final CurrencyNameRepository repository;
    private final LBCurrencyNameDataCommandToCurrencyName converter;

    public CurrencyNameServiceImpl(LBCurrencyNameDataCommandToCurrencyName converter,
                                   CurrencyNameRepository repository) {
        this.repository = repository;
        this.converter = converter;
    }

    @Transactional
    @Override
    public void save(LBCurrencyNameDataCommand nameDataCommand) {

        if (repository.existsByCurrencyCode(nameDataCommand.getName())) {
            return;
        }

        CurrencyName currencyName = converter.convert(nameDataCommand);

        if (currencyName != null) {
            repository.save(currencyName);
        }

    }

    @Override
    public CurrencyName getCurrencyNameByCode(String currencyCode) {

        String message = "Currency with name of " + currencyCode + " was not found.";

        return repository.findByCurrencyCode(currencyCode)
                .<ResourceNotFoundException> orElseThrow( () -> {
                    throw new ResourceNotFoundException(message);
                });

    }

}
