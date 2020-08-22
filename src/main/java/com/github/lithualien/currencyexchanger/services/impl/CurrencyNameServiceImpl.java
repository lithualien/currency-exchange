package com.github.lithualien.currencyexchanger.services.impl;

import com.github.lithualien.currencyexchanger.commands.lbxml.LBCurrencyNameDataCommand;
import com.github.lithualien.currencyexchanger.commands.v1.CurrencyCommand;
import com.github.lithualien.currencyexchanger.converters.CurrencyRateToCurrencyCommand;
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
    private final CurrencyRateToCurrencyCommand currencyCommandConverter;

    public CurrencyNameServiceImpl(LBCurrencyNameDataCommandToCurrencyName converter,
                                   CurrencyRateToCurrencyCommand currencyCommandConverter,
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

    private List<CurrencyCommand> getCurrencyCommands(List<CurrencyName> currencyRates) {

        List<CurrencyCommand> currencyCommands = new ArrayList<>();

        currencyRates.forEach(rate -> {
            CurrencyCommand currencyCommand = currencyCommandConverter.convert(rate);
            currencyCommands.add(currencyCommand);
        });

        return currencyCommands;
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
}
