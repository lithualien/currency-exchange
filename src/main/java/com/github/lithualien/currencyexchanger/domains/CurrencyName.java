package com.github.lithualien.currencyexchanger.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "currency_names")
public class CurrencyName extends BaseEntity {

    private String currencyCode;
    private String enDescription;
    private String ltDescription;

    @OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH },
            mappedBy = "currencyName")
    private Set<CurrencyRate> currencyRates = new HashSet<>();
}
