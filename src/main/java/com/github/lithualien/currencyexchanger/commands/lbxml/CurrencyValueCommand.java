package com.github.lithualien.currencyexchanger.commands.lbxml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "CcyAmt")
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrencyValueCommand {

    @XmlElement(name = "Ccy")
    private String name;

    @XmlElement(name = "Amt")
    private BigDecimal value;

}
