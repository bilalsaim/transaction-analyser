package com.bilisel.bianalyser.logic;

import com.bilisel.bianalyser.model.AnalysisResult;
import com.bilisel.bianalyser.model.Transaction;
import org.eclipse.collections.impl.collector.BigDecimalSummaryStatistics;
import org.eclipse.collections.impl.collector.Collectors2;

import java.util.Date;
import java.util.Map;

public class TransactionAnalyser {

    private Map<String, Transaction> modelList;

    public TransactionAnalyser(Map<String, Transaction> modelList) {
        this.modelList = modelList;
    }

    public AnalysisResult analyze(Date fromDate, Date toDate, String merchant) {

        BigDecimalSummaryStatistics statistics =
                modelList.values().parallelStream()
                .filter(x -> fromDate.before(x.getDate()) && toDate.after(x.getDate()))
                .filter(x -> merchant.equals(x.getMerchant()))
                .filter(x -> x.getReversalTransactions().isEmpty())
                .collect(Collectors2.summarizingBigDecimal(e -> e.getAmount()));

        return new AnalysisResult((int) statistics.getCount(), statistics.getAverage());
    }
}
