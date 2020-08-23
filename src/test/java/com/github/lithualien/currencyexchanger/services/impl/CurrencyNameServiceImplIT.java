package com.github.lithualien.currencyexchanger.services.impl;

import com.github.lithualien.currencyexchanger.commands.lbxml.LBCurrencyNameDataCommand;
import com.github.lithualien.currencyexchanger.commands.lbxml.LBLanguageCommand;
import com.github.lithualien.currencyexchanger.commands.v1.CurrencyCommand;
import com.github.lithualien.currencyexchanger.domains.CurrencyName;
import com.github.lithualien.currencyexchanger.exceptions.ResourceNotFoundException;
import com.github.lithualien.currencyexchanger.services.CurrencyNameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class CurrencyNameServiceImplIT {

    private final String CODE_1 = "EUR";
    private final String CODE_2 = "QQQ";
    private final String LT_DESCRIPTION_1 = "Euras";
    private final String EN_DESCRIPTION_1 = "Euro";
    private final String LT_DESCRIPTION_2 = "Kju";
    private final String EN_DESCRIPTION_2 = "Que";
    private final String LANGUAGE_1 = "LT";
    private final String LANGUAGE_2 = "EN";

    @Autowired
    private CurrencyNameService service;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getCurrencyNameByCode() {
        // when
        CurrencyName currencyName = service.getCurrencyNameByCode(CODE_1);

        // then
        assertNotNull(currencyName);
        assertEquals(CODE_1, currencyName.getCurrencyCode());
        assertEquals(LT_DESCRIPTION_1, currencyName.getLtDescription());
        assertEquals(EN_DESCRIPTION_1, currencyName.getEnDescription());
    }

    @Test
    void getCurrencies() {
        // when
        List<CurrencyCommand> currencyCommands = service.getCurrencies();

        // then
        assertNotNull(currencyCommands);
        assertFalse(currencyCommands.isEmpty());
    }

    @Test
    void save() {
        // given
        LBCurrencyNameDataCommand lbCurrencyNameDataCommand = new LBCurrencyNameDataCommand();
        lbCurrencyNameDataCommand.setCode(CODE_2);

        List<LBLanguageCommand> languageCommands = new ArrayList<>();

        LBLanguageCommand lbLanguageCommand1 = new LBLanguageCommand();
        lbLanguageCommand1.setLanguage(LANGUAGE_1);
        lbLanguageCommand1.setContent(LT_DESCRIPTION_2);

        LBLanguageCommand lbLanguageCommand2 = new LBLanguageCommand();
        lbLanguageCommand2.setLanguage(LANGUAGE_2);
        lbLanguageCommand2.setContent(EN_DESCRIPTION_2);

        languageCommands.add(lbLanguageCommand1);
        languageCommands.add(lbLanguageCommand2);

        lbCurrencyNameDataCommand.setLbLanguageCommands(languageCommands);

        // when
        CurrencyName currencyName = service.save(lbCurrencyNameDataCommand);

        // then
        assertNotNull(currencyName);
        assertEquals(CODE_2, currencyName.getCurrencyCode());
        assertEquals(EN_DESCRIPTION_2, currencyName.getEnDescription());
        assertEquals(LT_DESCRIPTION_2, currencyName.getLtDescription());

    }

    @Test
    void getNullCurrencyName() {
        assertThrows(ResourceNotFoundException.class, () -> service.getCurrencyNameByCode(null));
    }

    @Test
    void saveIfExists() {
        // given
        LBCurrencyNameDataCommand lbCurrencyNameDataCommand = new LBCurrencyNameDataCommand();
        lbCurrencyNameDataCommand.setCode(CODE_1);

        List<LBLanguageCommand> languageCommands = new ArrayList<>();

        LBLanguageCommand lbLanguageCommand1 = new LBLanguageCommand();
        lbLanguageCommand1.setLanguage(LANGUAGE_1);
        lbLanguageCommand1.setContent(LT_DESCRIPTION_1);

        LBLanguageCommand lbLanguageCommand2 = new LBLanguageCommand();
        lbLanguageCommand2.setLanguage(LANGUAGE_2);
        lbLanguageCommand2.setContent(EN_DESCRIPTION_2);

        languageCommands.add(lbLanguageCommand1);
        languageCommands.add(lbLanguageCommand2);

        lbCurrencyNameDataCommand.setLbLanguageCommands(languageCommands);

        // when
        CurrencyName currencyName = service.save(lbCurrencyNameDataCommand);

        // then
        assertNull(currencyName);
    }

}