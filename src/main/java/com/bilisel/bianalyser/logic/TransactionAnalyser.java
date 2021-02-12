package com.bilisel.bianalyser.logic;

import com.bilisel.bianalyser.model.AnalysisResult;
import com.bilisel.bianalyser.model.Transaction;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.bilisel.bianalyser.model.TransactionType.REVERSAL;
import static java.util.stream.Collectors.toList;

public class TransactionAnalyser {

    private Map<String, Transaction> modelList;

    public TransactionAnalyser(Map<String, Transaction> modelList) {
        this.modelList = modelList;
    }

    public AnalysisResult analyze(Date fromDate, Date toDate, String merchant) {

        List<Transaction> filteredTransactions = filterTransactions(fromDate, toDate, merchant);

        double averageAmount = calculateAverageAmount(filteredTransactions);

        return new AnalysisResult(filteredTransactions.size(), averageAmount);
    }

    private double calculateAverageAmount(List<Transaction> filtered) {
        return filtered.parallelStream()
                .map(Transaction::getAmount)
                .reduce(0.0, Double::sum);
    }

    public List<Transaction> filterTransactions(Date fromDate, Date toDate, String merchant) {
        return modelList.values().parallelStream()
                .filter(x -> fromDate.before(x.getDate()) && toDate.after(x.getDate()))
                .filter(x -> merchant.equals(x.getMerchant()))
                //.filter(x -> !x.getType().equals(REVERSAL))
                .filter(x -> x.getReversalTransactions().isEmpty())
                .collect(toList());
    }
}
