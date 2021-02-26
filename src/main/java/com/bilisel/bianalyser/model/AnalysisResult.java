package com.bilisel.bianalyser.model;

import java.math.BigDecimal;

public class AnalysisResult {
    private int numberOfTransaction;
    private BigDecimal averageTransactionValue;

    public AnalysisResult(int numberOfTransaction, BigDecimal averageTransactionValue) {
        this.numberOfTransaction = numberOfTransaction;
        this.averageTransactionValue = averageTransactionValue;
    }

    public int getNumberOfTransaction() {
        return numberOfTransaction;
    }

    public BigDecimal getAverageTransactionValue() {
        return averageTransactionValue;
    }
}
