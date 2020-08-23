package com.github.lithualien.currencyexchanger.services.impl;

import com.github.lithualien.currencyexchanger.commands.lbxml.LBCurrencyValueCommand;
import com.github.lithualien.currencyexchanger.commands.v1.CurrencyInputCommand;
import com.github.lithualien.currencyexchanger.commands.v1.CurrencyOutputCommand;
import com.github.lithualien.currencyexchanger.domains.CurrencyName;
import com.github.lithualien.currencyexchanger.domains.CurrencyRate;
import com.github.lithualien.currencyexchanger.exceptions.ResourceNotFoundException;
import com.github.lithualien.currencyexchanger.services.CurrencyNameService;
import com.github.lithualien.currencyexchanger.services.CurrencyRateService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
class CurrencyRateServiceImplIT {

    private static final LocalDate DATE_1 = LocalDate.of(2020, 8, 15);
    private static final LocalDate DATE_2 = LocalDate.of(2020, 8, 22);
    private static final Long FROM_1 = 67L;
    private static final Long TO_1 = 14L;
    private static final Long FROM_2 = 46L;
    private static final Long TO_2 = 86L;
    private static final Long FROM_3 = 170L;
    private static final Long TO_3 = 138L;
    private static final Long FROM_4 = -1L;
    private static final BigDecimal RESULT_1 = BigDecimal.valueOf(0.71579);
    private static final BigDecimal RESULT_2 = BigDecimal.valueOf(1.30371);
    private static final BigDecimal RESULT_3 = BigDecimal.valueOf(10.76728);
    private static final String CODE_1 = "EUR";
    private static final String CODE_2 = "QQQ";

    @Autowired
    private CurrencyRateService service;

    @Autowired
    private CurrencyNameService currencyNameService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getCurrencyValue() {
        // given
        CurrencyInputCommand currencyInputCommand1 = new CurrencyInputCommand();
        currencyInputCommand1.setFromCurrency(FROM_1);
        currencyInputCommand1.setToCurrency(TO_1);

        CurrencyInputCommand currencyInputCommand2 = new CurrencyInputCommand();
        currencyInputCommand2.setFromCurrency(FROM_2);
        currencyInputCommand2.setToCurrency(TO_2);

        CurrencyInputCommand currencyInputCommand3 = new CurrencyInputCommand();
        currencyInputCommand3.setFromCurrency(FROM_3);
        currencyInputCommand3.setToCurrency(TO_3);

        // when
        CurrencyOutputCommand currencyOutputCommand1 = service.getCurrencyValue(currencyInputCommand1);
        CurrencyOutputCommand currencyOutputCommand2 = service.getCurrencyValue(currencyInputCommand2);
        CurrencyOutputCommand currencyOutputCommand3 = service.getCurrencyValue(currencyInputCommand3);

        // then
        assertNotNull(currencyOutputCommand1);
        assertNotNull(currencyOutputCommand2);
        assertNotNull(currencyOutputCommand3);
        assertEquals(RESULT_1, currencyOutputCommand1.getCurrencyValue());
        assertEquals(RESULT_2, currencyOutputCommand2.getCurrencyValue());
        assertEquals(RESULT_3, currencyOutputCommand3.getCurrencyValue());
    }

    @Test
    void save() {
        // given
        LBCurrencyValueCommand valueCommand = new LBCurrencyValueCommand();
        valueCommand.setValue(RESULT_1);
        valueCommand.setCode(CODE_1);

        CurrencyName currencyName = currencyNameService.getCurrencyNameByCode(CODE_1);

        // when
        CurrencyRate currencyRate = service.save(valueCommand, DATE_1);

        // then
        assertNotNull(currencyRate);
        assertEquals(RESULT_1, currencyRate.getRate());
        assertEquals(DATE_1, currencyRate.getDate());
        assertEquals(currencyName ,currencyRate.getCurrencyName());
    }

    @Test
    void testCurrencyCodeNotFound() {

        // given
        CurrencyInputCommand currencyInputCommand = new CurrencyInputCommand();
        currencyInputCommand.setFromCurrency(FROM_4);
        currencyInputCommand.setToCurrency(TO_1);

        // when

        assertThrows(ResourceNotFoundException.class, () -> service.getCurrencyValue(currencyInputCommand));
    }

    @Test
    void testEmptyCurrencyInputCode() {
        // given
        CurrencyInputCommand currencyInputCommand = new CurrencyInputCommand();

        // when
        assertThrows(ResourceNotFoundException.class, () -> service.getCurrencyValue(currencyInputCommand));

    }

    @Test
    void testNewCurrency() {
        // given
        LBCurrencyValueCommand valueCommand = new LBCurrencyValueCommand();
        valueCommand.setCode(CODE_2);
        valueCommand.setValue(RESULT_2);

        // when

        assertThrows(ResourceNotFoundException.class, () -> service.save(valueCommand, DATE_1));

    }

    @Test
    void testExistingData() {
        // given
        LBCurrencyValueCommand valueCommand = new LBCurrencyValueCommand();
        valueCommand.setCode(CODE_1);
        valueCommand.setValue(RESULT_2);

        // when
        CurrencyRate currencyRate = service.save(valueCommand, DATE_2);

        // then
        assertNull(currencyRate);
    }
}