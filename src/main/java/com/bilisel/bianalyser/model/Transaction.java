package com.bilisel.bianalyser.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Transaction {

    private final String ID;
    private final Date date;
    private final double amount;
    private final String merchant;
    private final TransactionType transactionType;
    private final Set<Transaction> reversalTransactions;

    public Transaction(String ID, Date date, Double amount, String merchant, TransactionType transactionType) {
        this.ID = ID;
        this.date = date;
        this.amount = amount;
        this.merchant = merchant;
        this.transactionType = transactionType;
        this.reversalTransactions = new HashSet<>();
    }

    public String getID() {
        return ID;
    }

    public Date getDate() {
        return new Date(date.getTime());
    }

    public double getAmount() {
        return amount;
    }

    public String getMerchant() {
        return merchant;
    }

    public TransactionType getType() {
        return transactionType;
    }

    public Set<Transaction> getReversalTransactions() {
        return Collections.unmodifiableSet(reversalTransactions);
    }

    public void addReversalTransaction(Transaction transaction) {
        reversalTransactions.add(transaction);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Transaction model = (Transaction) o;

        return new EqualsBuilder()
                .append(ID, model.ID)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(ID)
                .toHashCode();
    }
}
