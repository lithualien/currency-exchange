package com.github.lithualien.currencyexchanger.commands.lbxml;

import com.github.lithualien.currencyexchanger.configs.LocalDateTimeAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "FxRate", namespace = "http://www.lb.lt/WebServices/FxRates")
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrencyRateDataCommand {


    @XmlElement(name = "Dt")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDate localDate;

    @XmlElement(name = "CcyAmt")
    private Set<CurrencyValueCommand> currencyValueCommands = new HashSet<>();

}
