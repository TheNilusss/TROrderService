package com.study.orderService.entity;

import org.springframework.data.annotation.Id;

public class Ordern {

    @Id
    public String id;

    public String customerID;
    public String cartID;
    public double moneyPaid;
    public String currency;

    public Ordern() {
    }

    public Ordern(String customerID, String cartID, double moneyPaid, String currency) {
        this.customerID = customerID;
        this.cartID = cartID;
        this.moneyPaid = moneyPaid;
        this.currency = currency;
    }
}
