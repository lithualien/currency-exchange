package com.github.lithualien.currencyexchanger.services.impl;

import com.github.lithualien.currencyexchanger.commands.lbxml.LBCurrencyNameCommand;
import com.github.lithualien.currencyexchanger.commands.lbxml.LBCurrencyNameDataCommand;
import com.github.lithualien.currencyexchanger.commands.lbxml.LBCurrencyRateCommand;
import com.github.lithualien.currencyexchanger.commands.lbxml.LBCurrencyValueCommand;
import com.github.lithualien.currencyexchanger.exceptions.ResourceNotFoundException;
import com.github.lithualien.currencyexchanger.services.CurrencyNameService;
import com.github.lithualien.currencyexchanger.services.CurrencyRateService;
import com.github.lithualien.currencyexchanger.services.LBService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Log4j2
@Service
public class LBServiceImpl implements LBService {

    private final WebClient webClient;
    private final CurrencyNameService currencyNameService;
    private final CurrencyRateService currencyRateService;
    public static String updateAt;

    public LBServiceImpl(WebClient webClient, CurrencyNameService currencyNameService, CurrencyRateService currencyRateService) {
        this.webClient = webClient;
        this.currencyNameService = currencyNameService;
        this.currencyRateService = currencyRateService;
    }

    @Override
    public void addCurrencies() {

        Flux<LBCurrencyNameCommand> currencies = webClient.get()
                .uri("/getCurrencyList?")
                .accept(MediaType.TEXT_XML)
                .retrieve()
                .bodyToFlux(LBCurrencyNameCommand.class);

        Set<LBCurrencyNameDataCommand> nameDataCommands = getNameDataCommands(currencies);

        nameDataCommands.forEach(currencyNameService::save);

        log.info("Updated currency name list");
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Override
    public void addCurrencyRates() {
        LocalDate localDate = LocalDate.now();
        String url = "/getFxRates?tp=LT&dt=" + localDate;

        Flux<LBCurrencyRateCommand> currencyValues = webClient.get()
                .uri(url)
                .accept(MediaType.TEXT_XML)
                .retrieve()
                .bodyToFlux(LBCurrencyRateCommand.class);

        List<LBCurrencyValueCommand> valueCommands = getValueCommands(currencyValues);

        for(int i = 0; i < valueCommands.size(); i++) {
            try {
                currencyRateService.save(valueCommands.get(i), localDate);
            }
            catch (ResourceNotFoundException ex) {
                log.info(ex.getMessage());
                addCurrencies();
                i--;
            }
        }

        setUpdatedAt();

        log.info("Updated currency rates to " + localDate + " rates");

    }

    private List<LBCurrencyValueCommand> getValueCommands(Flux<LBCurrencyRateCommand> data) {

        Set<LBCurrencyValueCommand> valueCommands = new HashSet<>();

        data.toStream()
                .forEach(wrapper -> wrapper.getLBCurrencyRateDataCommands()
                        .forEach(data1 -> valueCommands.addAll(data1.getLBCurrencyValueCommands()))
                );

        return new ArrayList<>(valueCommands);
    }

    private Set<LBCurrencyNameDataCommand> getNameDataCommands(Flux<LBCurrencyNameCommand> data) {

        Set<LBCurrencyNameDataCommand> nameDataCommands = new HashSet<>();

        data.toStream()
                .forEach(wrapper -> nameDataCommands.addAll(wrapper.getLBCurrencyNameDataCommands()));

        return nameDataCommands;
    }

    private static void setUpdatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        updateAt = LocalDateTime.now().format(formatter);
    }

}
