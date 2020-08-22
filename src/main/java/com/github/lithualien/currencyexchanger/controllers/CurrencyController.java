package com.github.lithualien.currencyexchanger.controllers;

import com.github.lithualien.currencyexchanger.commands.v1.CurrencyInputCommand;
import com.github.lithualien.currencyexchanger.commands.v1.CurrencyOutputCommand;
import com.github.lithualien.currencyexchanger.services.CurrencyNameService;
import com.github.lithualien.currencyexchanger.services.CurrencyRateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return new ResponseEntity<>(currencyNameService.getCurrencies(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CurrencyOutputCommand> getCurrencyPrice(@RequestBody CurrencyInputCommand input) {
        return new ResponseEntity<>(currencyRateService.getCurrencyValue(input), HttpStatus.OK);
    }

}
