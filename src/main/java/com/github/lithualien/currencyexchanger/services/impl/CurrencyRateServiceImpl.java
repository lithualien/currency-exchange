package com.github.lithualien.currencyexchanger.services.impl;

import com.github.lithualien.currencyexchanger.commands.lbxml.LBCurrencyValueCommand;
import com.github.lithualien.currencyexchanger.commands.v1.CurrencyInputCommand;
import com.github.lithualien.currencyexchanger.commands.v1.CurrencyOutputCommand;
import com.github.lithualien.currencyexchanger.converters.LBCurrencyValueCommandToCurrencyRate;
import com.github.lithualien.currencyexchanger.domains.CurrencyName;
import com.github.lithualien.currencyexchanger.domains.CurrencyRate;
import com.github.lithualien.currencyexchanger.exceptions.ResourceNotFoundException;
import com.github.lithualien.currencyexchanger.repositories.CurrencyRateRepository;
import com.github.lithualien.currencyexchanger.services.CurrencyNameService;
import com.github.lithualien.currencyexchanger.services.CurrencyRateService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Log4j2
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

    @Override
    public CurrencyOutputCommand getCurrencyValue(CurrencyInputCommand currencyInputCommand) {
        CurrencyRate from = getCurrencyRateByCurrencyNameId(currencyInputCommand.getFromCurrency());
        CurrencyRate to = getCurrencyRateByCurrencyNameId(currencyInputCommand.getToCurrency());
        BigDecimal value = to.getRate().divide(from.getRate(), 5, RoundingMode.HALF_DOWN);

        log.info("Converting from " + from.getCurrencyName().getCurrencyCode()
                + " to " + to.getCurrencyName().getCurrencyCode() + ", value = " + value);

        return new CurrencyOutputCommand(value);
    }

    @Transactional
    @Override
    public void save(LBCurrencyValueCommand valueCommand, LocalDate date) throws ResourceNotFoundException {

        CurrencyName currencyName;

        try {
            currencyName = currencyNameService.getCurrencyNameByCode(valueCommand.getCode());
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

    private CurrencyRate getCurrencyRateByCurrencyNameId(Long id) {
        String message = "Currency rate with id=" + id + " was not found.";

        return repository
                .findFirstByCurrencyNameIdOrderByDateDesc(id)
                .<ResourceNotFoundException>orElseThrow(() -> {
                    throw new ResourceNotFoundException(message);
                });
    }

}
