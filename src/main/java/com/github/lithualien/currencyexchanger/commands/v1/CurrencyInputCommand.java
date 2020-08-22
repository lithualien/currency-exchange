package com.github.lithualien.currencyexchanger.commands.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyInputCommand implements Serializable {

    @JsonProperty("from_currency")
    private Long fromCurrency;

    @JsonProperty("to_currency")
    private Long toCurrency;

}
