package com.github.lithualien.currencyexchanger.converters;

import com.github.lithualien.currencyexchanger.commands.lbxml.LBCurrencyValueCommand;
import com.github.lithualien.currencyexchanger.domains.CurrencyName;
import com.github.lithualien.currencyexchanger.domains.CurrencyRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LBCurrencyValueCommandToCurrencyRateTest {

    private LBCurrencyValueCommandToCurrencyRate converter;
    private static final String CODE = "EUR";
    private static final BigDecimal VALUE = BigDecimal.valueOf(0.004150);
    private final static Long ID = 1L;
    private final static String EN_DESCRIPTION = "Euro";
    private final static String LT_DESCRIPTION = "Euras";
    private final static LocalDate DATE = LocalDate.now();
    private LBCurrencyValueCommand lbCurrencyValueCommand;
    private CurrencyName currencyName;

    @BeforeEach
    void setUp() {
        converter = new LBCurrencyValueCommandToCurrencyRate();

        // given

        lbCurrencyValueCommand = new LBCurrencyValueCommand();
        lbCurrencyValueCommand.setValue(VALUE);
        lbCurrencyValueCommand.setCode(CODE);

        currencyName = new CurrencyName();
        currencyName.setId(ID);
        currencyName.setCurrencyCode(CODE);
        currencyName.setLtDescription(LT_DESCRIPTION);
        currencyName.setEnDescription(EN_DESCRIPTION);
    }

    @Test
    void convert() {
        // when
        CurrencyRate currencyRate = converter.convert(lbCurrencyValueCommand, currencyName, DATE);

        // then
        assertNotNull(currencyRate);
        assertNull(currencyRate.getId());
        assertEquals(DATE, currencyRate.getDate());
        assertEquals(VALUE, currencyRate.getRate());
        assertEquals(currencyName, currencyRate.getCurrencyName());
    }

    @Test
    void testNullLBCurrencyValueCommand() {
        assertNull(converter.convert(null, currencyName, DATE));
    }

    @Test
    void testNullCurrencyName() {
        assertNotNull(converter.convert(lbCurrencyValueCommand, null, DATE));
    }

    @Test
    void testNullDate() {
        assertNotNull(converter.convert(lbCurrencyValueCommand, currencyName, null));
    }

    @Test
    void testEmptyLBCurrencyValueCommand() {
        assertNotNull(converter.convert(new LBCurrencyValueCommand(), currencyName, DATE));
    }

    @Test
    void testEmptyCurrencyName() {
        assertNotNull(converter.convert(lbCurrencyValueCommand, new CurrencyName(), DATE));
    }

}