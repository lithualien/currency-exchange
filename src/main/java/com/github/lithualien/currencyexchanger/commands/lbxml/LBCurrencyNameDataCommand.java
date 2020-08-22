package com.github.lithualien.currencyexchanger.commands.lbxml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "CcyNtry")
@XmlAccessorType(XmlAccessType.FIELD)
public class LBCurrencyNameDataCommand {

    @XmlElement(name = "Ccy")
    private String code;

    @XmlElement(name = "CcyNm")
    private List<LBLanguageCommand> lbLanguageCommands = new ArrayList<>();

}
