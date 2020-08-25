package com.github.lithualien.currencyexchanger.controllers;

import com.github.lithualien.currencyexchanger.commands.v1.CurrencyCommand;
import com.github.lithualien.currencyexchanger.commands.v1.CurrencyInputCommand;
import com.github.lithualien.currencyexchanger.commands.v1.CurrencyOutputCommand;
import com.github.lithualien.currencyexchanger.commands.v1.DateCommand;
import com.github.lithualien.currencyexchanger.services.CurrencyNameService;
import com.github.lithualien.currencyexchanger.services.CurrencyRateService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("api/currencies/v1")
public class CurrencyController {

    private final CurrencyRateService currencyRateService;
    private final CurrencyNameService currencyNameService;

    public CurrencyController(CurrencyRateService currencyRateService, CurrencyNameService currencyNameService) {
        this.currencyRateService = currencyRateService;
        this.currencyNameService = currencyNameService;
    }

    @GetMapping
    public ResponseEntity<?> getCurrencies() {
        List<CurrencyCommand> currencyNames = currencyNameService.getCurrencies();
        log.info("Fetched list of currencies");
        return new ResponseEntity<>(currencyNames, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CurrencyOutputCommand> getCurrencyPrice(@RequestBody CurrencyInputCommand input) {
        CurrencyOutputCommand currencyOutputCommand = currencyRateService.getCurrencyValue(input);
        return new ResponseEntity<>(currencyOutputCommand, HttpStatus.OK);
    }

    @GetMapping("/today")
    public ResponseEntity<DateCommand> getDate() {
        DateCommand dateCommand = new DateCommand(LocalDate.now());
        log.info("Fetched date.");
        return new ResponseEntity<>(dateCommand, HttpStatus.OK);
    }

}
