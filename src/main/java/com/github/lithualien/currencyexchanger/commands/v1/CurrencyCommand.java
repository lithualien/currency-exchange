package com.github.lithualien.currencyexchanger.commands.v1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyCommand implements Serializable {

    private Long id;
    private String code;
    private String description;

}
