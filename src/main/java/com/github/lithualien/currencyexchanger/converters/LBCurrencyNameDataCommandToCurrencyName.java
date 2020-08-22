package com.github.lithualien.currencyexchanger.converters;

import com.github.lithualien.currencyexchanger.commands.lbxml.LBCurrencyNameDataCommand;
import com.github.lithualien.currencyexchanger.commands.lbxml.LBLanguageCommand;
import com.github.lithualien.currencyexchanger.domains.CurrencyName;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LBCurrencyNameDataCommandToCurrencyName implements Converter<LBCurrencyNameDataCommand, CurrencyName> {

    @Nullable
    @Override
    public CurrencyName convert(LBCurrencyNameDataCommand source) {

        if(source == null) {
            return null;
        }

        final CurrencyName currencyName = new CurrencyName();

        List<LBLanguageCommand> languageCommands = source.getLbLanguageCommands();
        currencyName.setCurrencyCode(source.getCode());

        if(!languageCommands.isEmpty()) {
            currencyName.setLtDescription(languageCommands.get(0).getContent());
            currencyName.setEnDescription(languageCommands.get(1).getContent());
        }

        return currencyName;
    }

}
