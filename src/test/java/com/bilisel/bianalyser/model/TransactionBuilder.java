package com.bilisel.bianalyser.model;

import com.bilisel.bianalyser.util.DateHelper;

import java.text.ParseException;
import java.util.Date;

public class TransactionBuilder {

    private String id = "RandomId";
    private String merchant = "Random";
    private Date date = new Date();
    private double amount = 0.0;
    private TransactionType type = TransactionType.PAYMENT;

    private TransactionBuilder() {
    }

    public static TransactionBuilder builder() {
        return new TransactionBuilder();
    }

    public TransactionBuilder withDate(String date) throws ParseException {
        this.date = DateHelper.parseDate(date);
        return this;
    }

    public TransactionBuilder withType(TransactionType type) {
        this.type = type;
        return this;
    }

    public TransactionBuilder withMerchant(String merchant) {
        this.merchant = merchant;
        return this;
    }

    public TransactionBuilder withAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public Transaction build(String tID) {
        return new Transaction(tID, date, amount, merchant, type);
    }

    public Transaction build() {
        return new Transaction(id, date, amount, merchant, type);
    }
}
