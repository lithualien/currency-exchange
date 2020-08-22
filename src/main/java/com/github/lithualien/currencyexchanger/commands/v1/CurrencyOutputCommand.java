package com.github.lithualien.currencyexchanger.commands.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyOutputCommand implements Serializable {

    @JsonProperty("currency_value")
    private BigDecimal currencyValue;

}
