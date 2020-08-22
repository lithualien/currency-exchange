package com.github.lithualien.currencyexchanger.converters;

import com.github.lithualien.currencyexchanger.commands.lbxml.LBCurrencyNameDataCommand;
import com.github.lithualien.currencyexchanger.commands.lbxml.LBLanguageCommand;
import com.github.lithualien.currencyexchanger.domains.CurrencyName;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LBCurrencyNameDataCommandToCurrencyName implements Converter<LBCurrencyNameDataCommand, CurrencyName> {

    @Override
    public CurrencyName convert(LBCurrencyNameDataCommand source) {

        final CurrencyName currencyName = new CurrencyName();

        List<LBLanguageCommand> languageCommands = source.getLBLanguageCommands();
        currencyName.setCurrencyCode(source.getName());
        currencyName.setLtDescription(languageCommands.get(0).getContent());
        currencyName.setEnDescription(languageCommands.get(1).getContent());

        return currencyName;
    }

}
