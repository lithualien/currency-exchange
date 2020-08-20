package com.github.lithualien.currencyexchanger.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "currency_rates")
public class CurrencyRate extends BaseEntity {

    private BigDecimal rate;
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    private CurrencyName currencyName;

}
