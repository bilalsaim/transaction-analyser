package com.bilisel.bianalyser.model;

public class AnalysisResult {
    private int numberOfTransaction;
    private double averageTransactionValue;

    public AnalysisResult(int numberOfTransaction, double averageTransactionValue) {
        this.numberOfTransaction = numberOfTransaction;
        this.averageTransactionValue = averageTransactionValue;
    }

    public int getNumberOfTransaction() {
        return numberOfTransaction;
    }

    public double getAverageTransactionValue() {
        return averageTransactionValue;
    }
}
