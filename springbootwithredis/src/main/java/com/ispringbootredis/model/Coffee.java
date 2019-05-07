package com.ispringbootredis.model;

import lombok.*;
import org.hibernate.annotations.Type;
import org.joda.money.Money;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_COFFEE")
public class Coffee extends BaseEntity implements Serializable {
    private String name;

    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyMinorAmount",
    parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "CNY")}
    )
    private Money price;
}
