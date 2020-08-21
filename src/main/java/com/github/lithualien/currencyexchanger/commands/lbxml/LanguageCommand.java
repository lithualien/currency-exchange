package com.github.lithualien.currencyexchanger.commands.lbxml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "CcyNm")
@XmlAccessorType(XmlAccessType.FIELD)
public class LanguageCommand {

    @XmlAttribute(name = "lang")
    private String language;

    @XmlValue
    private String content;

}
