package com.github.lithualien.currencyexchanger.converters;

import com.github.lithualien.currencyexchanger.commands.v1.CurrencyCommand;
import com.github.lithualien.currencyexchanger.domains.CurrencyName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyNameToCurrencyCommandTest {

    private CurrencyNameToCurrencyCommand converter;
    private final static Long ID = 1L;
    private final static String CURRENCY_CODE = "EUR";
    private final static String EN_DESCRIPTION = "Euro";
    private final static String LT_DESCRIPTION = "Euras";

    @BeforeEach
    void setUp() {
        converter = new CurrencyNameToCurrencyCommand();
    }

    @Test
    void convert() {
        // given
        CurrencyName currencyName = new CurrencyName();
        currencyName.setId(ID);
        currencyName.setCurrencyCode(CURRENCY_CODE);
        currencyName.setEnDescription(EN_DESCRIPTION);
        currencyName.setLtDescription(LT_DESCRIPTION);

        // when
        CurrencyCommand currencyCommand = converter.convert(currencyName);

        //then
        assertNotNull(currencyCommand);
        assertEquals(ID, currencyCommand.getId());
        assertEquals(CURRENCY_CODE, currencyCommand.getCode());
        assertEquals(LT_DESCRIPTION, currencyCommand.getDescription());
    }

    @Test
    void nullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void emptyObject() {
        assertNotNull(converter.convert(new CurrencyName()));
    }

}