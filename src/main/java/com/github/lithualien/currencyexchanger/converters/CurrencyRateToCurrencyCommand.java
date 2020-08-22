package com.github.lithualien.currencyexchanger.converters;

import com.github.lithualien.currencyexchanger.commands.v1.CurrencyCommand;
import com.github.lithualien.currencyexchanger.domains.CurrencyName;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CurrencyRateToCurrencyCommand implements Converter<CurrencyName, CurrencyCommand> {

    @Override
    public CurrencyCommand convert(CurrencyName source) {

        final CurrencyCommand currencyCommand = new CurrencyCommand();

        currencyCommand.setId(source.getId());
        currencyCommand.setCode(source.getCurrencyCode());
        currencyCommand.setDescription(source.getLtDescription());

        return currencyCommand;

    }

}
