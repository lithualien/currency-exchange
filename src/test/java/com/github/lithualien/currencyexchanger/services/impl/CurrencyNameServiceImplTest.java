package com.github.lithualien.currencyexchanger.services.impl;

import com.github.lithualien.currencyexchanger.converters.CurrencyNameToCurrencyCommand;
import com.github.lithualien.currencyexchanger.converters.LBCurrencyNameDataCommandToCurrencyName;
import com.github.lithualien.currencyexchanger.domains.CurrencyName;
import com.github.lithualien.currencyexchanger.exceptions.ResourceNotFoundException;
import com.github.lithualien.currencyexchanger.repositories.CurrencyNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyNameServiceImplTest {

    private final static Long ID_1 = 1L;
    private final static Long ID_2 = 1L;
    private static final String CODE_1 = "EUR";
    private static final String CODE_2 = "USD";
    private final static String EN_DESCRIPTION_1 = "Euro";
    private final static String LT_DESCRIPTION_1 = "Euras";
    private final static String EN_DESCRIPTION_2 = "USA Dollar";
    private final static String LT_DESCRIPTION_2 = "Amerikos doleris";

    private LBCurrencyNameDataCommandToCurrencyName converter;
    private CurrencyNameToCurrencyCommand currencyCommandConverter;

    @Mock
    private CurrencyNameRepository repository;

    @InjectMocks
    private CurrencyNameServiceImpl service;

    @BeforeEach
    void setUp() {
        converter = new LBCurrencyNameDataCommandToCurrencyName();
        currencyCommandConverter = new CurrencyNameToCurrencyCommand();
    }

    @Test
    void getCurrencyNameByCode() {
        // given
        CurrencyName currencyName = new CurrencyName();
        currencyName.setId(ID_1);
        currencyName.setCurrencyCode(CODE_1);
        currencyName.setEnDescription(EN_DESCRIPTION_1);
        currencyName.setLtDescription(LT_DESCRIPTION_1);

        // when
        when(repository.findByCurrencyCode(CODE_1)).thenReturn(Optional.of(currencyName));

        // then
        CurrencyName savedCurrencyName = service.getCurrencyNameByCode(CODE_1);
        assertNotNull(savedCurrencyName);
        assertEquals(0, savedCurrencyName.getCurrencyRates().size());
        assertEquals(ID_1, savedCurrencyName.getId());
        assertEquals(CODE_1, savedCurrencyName.getCurrencyCode());
        assertEquals(EN_DESCRIPTION_1, savedCurrencyName.getEnDescription());
        assertEquals(LT_DESCRIPTION_1, savedCurrencyName.getLtDescription());

    }

    @Test
    void getCurrencies() {
        // given
        List<CurrencyName> currencyNames = new ArrayList<>();

        CurrencyName currencyName1 = new CurrencyName();
        currencyName1.setId(ID_1);
        currencyName1.setLtDescription(LT_DESCRIPTION_1);
        currencyName1.setEnDescription(EN_DESCRIPTION_1);
        currencyName1.setCurrencyCode(CODE_1);

        CurrencyName currencyName2 = new CurrencyName();
        currencyName2.setId(ID_2);
        currencyName2.setLtDescription(LT_DESCRIPTION_2);
        currencyName2.setEnDescription(EN_DESCRIPTION_2);
        currencyName2.setCurrencyCode(CODE_2);

        currencyNames.add(currencyName1);
        currencyNames.add(currencyName2);

        // when
        when(repository.findAllByDate(any())).thenReturn(currencyNames);

        // then
    }

    @Test
    void findByCurrencyCodeNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> service.getCurrencyNameByCode(CODE_1));
    }
}