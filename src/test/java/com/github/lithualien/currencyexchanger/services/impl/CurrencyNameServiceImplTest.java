package com.github.lithualien.currencyexchanger.services.impl;

import com.github.lithualien.currencyexchanger.domains.CurrencyName;
import com.github.lithualien.currencyexchanger.exceptions.ResourceNotFoundException;
import com.github.lithualien.currencyexchanger.repositories.CurrencyNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyNameServiceImplTest {

    private final static Long ID_1 = 1L;
    private static final String CODE_1 = "EUR";
    private final static String EN_DESCRIPTION_1 = "Euro";
    private final static String LT_DESCRIPTION_1 = "Euras";


    @Mock
    private CurrencyNameRepository repository;

    @InjectMocks
    private CurrencyNameServiceImpl service;

    @BeforeEach
    void setUp() {

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
    void findByCurrencyCodeNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> service.getCurrencyNameByCode(CODE_1));
    }
}