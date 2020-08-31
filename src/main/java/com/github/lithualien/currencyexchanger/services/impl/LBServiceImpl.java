package com.github.lithualien.currencyexchanger.services.impl;

import com.github.lithualien.currencyexchanger.commands.lbxml.*;
import com.github.lithualien.currencyexchanger.commands.v1.DateCommand;
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
import reactor.core.publisher.Mono;

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
    private static String updateAt;

    public LBServiceImpl(WebClient webClient, CurrencyNameService currencyNameService, CurrencyRateService currencyRateService) {
        this.webClient = webClient;
        this.currencyNameService = currencyNameService;
        this.currencyRateService = currencyRateService;
    }

    @Override
    public Mono<Void> addCurrencies(String code) {

        Flux<LBCurrencyNameDataCommand> currencyNames = webClient.get()
                .uri("/getCurrencyList?")
                .accept(MediaType.TEXT_XML)
                .retrieve()
                .bodyToFlux(LBCurrencyNameDataCommand.class);

        currencyNames
                .filter(names -> names.getCode().equals(code))
                .take(1)
                .flatMap(currencyNameService::save);

        return Mono.empty();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Override
    public Mono<Void> addCurrencyRates() {
        LocalDate localDate = LocalDate.now();
        String url = "/getFxRates?tp=LT&dt=" + localDate;

        Flux<LBCurrencyRateDataCommand> currencyValues = webClient.get()
                .uri(url)
                .accept(MediaType.TEXT_XML)
                .retrieve()
                .bodyToFlux(LBCurrencyRateDataCommand.class);

        currencyValues
                .flatMap(value -> currencyRateService.save(value, localDate));

        log.info("Updated currency rates to " + localDate + " rates");

//        List<LBCurrencyValueCommand> valueCommands = getValueCommands(currencyValues);
//
//        for(int i = 0; i < valueCommands.size(); i++) {
//            try {
//                currencyRateService.save(valueCommands.get(i), localDate);
//            }
//            catch (ResourceNotFoundException ex) {
//                log.info(ex.getMessage());
//                addCurrencies(valueCommands.get(i).getCode());
//                i--;
//            }
//        }
//
//        setUpdatedAt();
//

        return Mono.empty();

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

    public static DateCommand getLocalDateTime() {
        if (updateAt.isEmpty()) {
            setUpdatedAt();
        }

        DateCommand dateCommand = new DateCommand(updateAt);
        log.info("Fetched date.");

        return dateCommand;
    }

}
