package com.github.lithualien.currencyexchanger.configs;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDate> {

    private static final String formatStyle = "yyyy-MM-dd";
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(formatStyle);

    @Override
    public LocalDate unmarshal(String date) throws Exception {
        return LocalDate.parse(date, dateFormatter);
    }

    @Override
    public String marshal(LocalDate localDate) throws Exception {
        return localDate.format(dateFormatter);
    }
}
