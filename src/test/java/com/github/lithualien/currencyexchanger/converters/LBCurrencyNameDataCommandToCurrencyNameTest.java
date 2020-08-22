package com.github.lithualien.currencyexchanger.converters;

import com.github.lithualien.currencyexchanger.commands.lbxml.LBCurrencyNameDataCommand;
import com.github.lithualien.currencyexchanger.commands.lbxml.LBLanguageCommand;
import com.github.lithualien.currencyexchanger.domains.CurrencyName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LBCurrencyNameDataCommandToCurrencyNameTest {

    private LBCurrencyNameDataCommandToCurrencyName converter;
    private static final String CODE = "EUR";
    private static final String LANGUAGE_1 = "LT";
    private static final String LANGUAGE_2 = "EN";
    private static final String CONTENT_1 = "Euras";
    private static final String CONTENT_2 = "Euro";

    @BeforeEach
    void setUp() {
        converter = new LBCurrencyNameDataCommandToCurrencyName();
    }

    @Test
    void convert() {
        // given
        LBCurrencyNameDataCommand lbCurrencyNameDataCommand = new LBCurrencyNameDataCommand();
        List<LBLanguageCommand> lbLanguageCommands = new ArrayList<>();

        LBLanguageCommand lbLanguageCommand1 = new LBLanguageCommand();
        lbLanguageCommand1.setContent(CONTENT_1);
        lbLanguageCommand1.setLanguage(LANGUAGE_1);

        LBLanguageCommand lbLanguageCommand2 = new LBLanguageCommand();
        lbLanguageCommand2.setContent(CONTENT_2);
        lbLanguageCommand2.setLanguage(LANGUAGE_2);

        lbLanguageCommands.add(lbLanguageCommand1);
        lbLanguageCommands.add(lbLanguageCommand2);

        lbCurrencyNameDataCommand.setCode(CODE);
        lbCurrencyNameDataCommand.setLbLanguageCommands(lbLanguageCommands);

        // when
        CurrencyName currencyName = converter.convert(lbCurrencyNameDataCommand);

        // then
        assertNotNull(currencyName);
        assertEquals(CODE, currencyName.getCurrencyCode());
        assertEquals(CONTENT_1, currencyName.getLtDescription());
        assertEquals(CONTENT_2, currencyName.getEnDescription());

    }

    @Test
    void nullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void emptyObject() {
        assertNotNull(converter.convert(new LBCurrencyNameDataCommand()));
    }

}