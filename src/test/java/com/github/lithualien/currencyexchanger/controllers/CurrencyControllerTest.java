package com.github.lithualien.currencyexchanger.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lithualien.currencyexchanger.commands.v1.CurrencyCommand;
import com.github.lithualien.currencyexchanger.commands.v1.CurrencyInputCommand;
import com.github.lithualien.currencyexchanger.commands.v1.CurrencyOutputCommand;
import com.github.lithualien.currencyexchanger.services.CurrencyNameService;
import com.github.lithualien.currencyexchanger.services.CurrencyRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CurrencyControllerTest {

    @Mock
    private CurrencyRateService currencyRateService;

    @Mock
    private CurrencyNameService currencyNameService;

    @InjectMocks
    private CurrencyController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getCurrencies() throws Exception {
        final Long ID = 1L;
        final String CODE = "EUR";
        final String DESCRIPTION = "EURAS";

        // given
        List<CurrencyCommand> currencyNames = new ArrayList<>();

        CurrencyCommand currencyCommand = new CurrencyCommand();
        currencyCommand.setId(ID);
        currencyCommand.setCode(CODE);
        currencyCommand.setDescription(DESCRIPTION);

        currencyNames.add(currencyCommand);

        // when
        when(currencyNameService.getCurrencies()).thenReturn(currencyNames);

        // then
        mockMvc.perform(get("/api/currencies/v1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].code", is(CODE)))
                .andExpect(jsonPath("$[0].description", is(DESCRIPTION)));
    }

    @Test
    void getCurrencyPrice() throws Exception {
        final BigDecimal RESULT = BigDecimal.valueOf(10.4174);
        final Long FROM = 67L;
        final Long TO = 14L;

        // given
        CurrencyOutputCommand currencyOutputCommand = new CurrencyOutputCommand(RESULT);
        CurrencyInputCommand currencyInputCommand = new CurrencyInputCommand();
        currencyInputCommand.setFromCurrency(FROM);
        currencyInputCommand.setToCurrency(TO);

        String jsonCurrencyInputCommand = (new ObjectMapper()).valueToTree(currencyInputCommand).toString();

        // when
        when(currencyRateService.getCurrencyValue(any())).thenReturn(currencyOutputCommand);

        // then
        MvcResult mvcResult = mockMvc.perform(post("/api/currencies/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCurrencyInputCommand))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResult = mvcResult.getResponse().getContentAsString();
        CurrencyOutputCommand result = new ObjectMapper().readValue(jsonResult, CurrencyOutputCommand.class);

        assertEquals(RESULT, result.getCurrencyValue());
    }
}