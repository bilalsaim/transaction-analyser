package com.bilisel.bianalyser.util;

import com.bilisel.bianalyser.model.Transaction;
import com.bilisel.bianalyser.model.TransactionType;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import static com.bilisel.bianalyser.util.TestHelper.createDecimal;

public class TransactionBuilder {

    private String id = "RandomId";
    private String merchant = "Random";
    private Date date = new Date();
    private BigDecimal amount = BigDecimal.ZERO;
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
        this.amount = createDecimal(amount);
        return this;
    }

    public Transaction build(String tID) {
        return new Transaction(tID, date, amount, merchant, type);
    }

    public Transaction build() {
        return new Transaction(id, date, amount, merchant, type);
    }
}
