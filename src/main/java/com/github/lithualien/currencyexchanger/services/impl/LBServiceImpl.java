package com.github.lithualien.currencyexchanger.services.impl;

import com.github.lithualien.currencyexchanger.commands.lbxml.CurrencyNameCommand;
import com.github.lithualien.currencyexchanger.commands.lbxml.CurrencyRateCommand;
import com.github.lithualien.currencyexchanger.services.LBService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Log4j2
@Service(value = "lbServiceImpl")
public class LBServiceImpl implements LBService {

    private final WebClient webClient;

    public LBServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public void addCurrencies() {

        Flux<CurrencyNameCommand> currencies = webClient.get()
                .uri("/getCurrencyList?")
                .accept(MediaType.TEXT_XML)
                .retrieve()
                .bodyToFlux(CurrencyNameCommand.class);

        log.info("Fetched currency");
    }

    @Scheduled(fixedDelay = 1_000_000)
    @Override
    public void addCurrencyRates() {
        String url = "/getFxRates?tp=LT&dt=" + LocalDate.now();

        Flux<CurrencyRateCommand> currencyValues = webClient.get()
                .uri(url)
                .accept(MediaType.TEXT_XML)
                .retrieve()
                .bodyToFlux(CurrencyRateCommand.class);

        log.info("Updated currency rates.");

    }
}
