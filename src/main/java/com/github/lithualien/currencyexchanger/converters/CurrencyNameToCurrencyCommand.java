package com.github.lithualien.currencyexchanger.converters;

import com.github.lithualien.currencyexchanger.commands.v1.CurrencyCommand;
import com.github.lithualien.currencyexchanger.domains.CurrencyName;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CurrencyNameToCurrencyCommand implements Converter<CurrencyName, CurrencyCommand> {

    @Nullable
    @Override
    public CurrencyCommand convert(CurrencyName source) {

        if(source == null) {
            return null;
        }

        final CurrencyCommand currencyCommand = new CurrencyCommand();

        currencyCommand.setId(source.getId());
        currencyCommand.setCode(source.getCurrencyCode());
        currencyCommand.setDescription(source.getLtDescription());

        return currencyCommand;

    }

}
