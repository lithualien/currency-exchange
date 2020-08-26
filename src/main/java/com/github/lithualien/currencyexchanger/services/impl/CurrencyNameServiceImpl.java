package com.github.lithualien.currencyexchanger.services.impl;

import com.github.lithualien.currencyexchanger.commands.lbxml.LBCurrencyNameDataCommand;
import com.github.lithualien.currencyexchanger.commands.v1.CurrencyCommand;
import com.github.lithualien.currencyexchanger.converters.CurrencyNameToCurrencyCommand;
import com.github.lithualien.currencyexchanger.converters.LBCurrencyNameDataCommandToCurrencyName;
import com.github.lithualien.currencyexchanger.domains.CurrencyName;
import com.github.lithualien.currencyexchanger.exceptions.ResourceNotFoundException;
import com.github.lithualien.currencyexchanger.repositories.CurrencyNameRepository;
import com.github.lithualien.currencyexchanger.services.CurrencyNameService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyNameServiceImpl implements CurrencyNameService {

    private final CurrencyNameRepository repository;
    private final LBCurrencyNameDataCommandToCurrencyName converter;
    private final CurrencyNameToCurrencyCommand currencyCommandConverter;

    public CurrencyNameServiceImpl(LBCurrencyNameDataCommandToCurrencyName converter,
                                   CurrencyNameToCurrencyCommand currencyCommandConverter,
                                   CurrencyNameRepository repository) {
        this.repository = repository;
        this.converter = converter;
        this.currencyCommandConverter = currencyCommandConverter;
    }

    @Override
    public CurrencyName getCurrencyNameByCode(String currencyCode) {
        String message = "Currency with name of " + currencyCode + " was not found.";

        return repository.findByCurrencyCode(currencyCode)
                .<ResourceNotFoundException> orElseThrow( () -> {
                    throw new ResourceNotFoundException(message);
                });
    }

    @Override
    public List<CurrencyCommand> getCurrencies() {
        return getCurrencyCommands(repository.findAllByDate(LocalDate.now()));
    }

    private List<CurrencyCommand> getCurrencyCommands(List<CurrencyName> currencyNames) {

        List<CurrencyCommand> currencyCommands = new ArrayList<>();

        currencyNames.forEach(rate -> {
            CurrencyCommand currencyCommand = currencyCommandConverter.convert(rate);
            currencyCommands.add(currencyCommand);
        });

        return currencyCommands;
    }

    @Transactional
    @Override
    public CurrencyName save(LBCurrencyNameDataCommand nameDataCommand) {

        CurrencyName currencyName = converter.convert(nameDataCommand);

        if(currencyName != null) {
            return repository.save(currencyName);
        }

        return null;
    }
}
