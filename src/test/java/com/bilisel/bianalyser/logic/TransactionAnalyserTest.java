package com.bilisel.bianalyser.logic;

import com.bilisel.bianalyser.model.AnalysisResult;
import com.bilisel.bianalyser.model.Transaction;
import com.bilisel.bianalyser.model.TransactionBuilder;
import com.bilisel.bianalyser.util.DateHelper;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.bilisel.bianalyser.model.TransactionType.PAYMENT;
import static com.bilisel.bianalyser.model.TransactionType.REVERSAL;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionAnalyserTest {

    private TransactionAnalyser transactionAnalyser;

    @Test
    public void testGivenMerchantThenFilterAnalyseForMerchant() {
        // given
        String merchant = "Bilal";
        Date possibleMinFromDate = new Date(Long.MIN_VALUE);
        Date possibleMaxToDate = new Date(Long.MAX_VALUE);

        Transaction t0 = TransactionBuilder.builder()
                .withAmount(10.0)
                .withMerchant(merchant)
                .withType(PAYMENT)
                .build();
        Transaction t1 = TransactionBuilder.builder()
                .withAmount(5.0)
                .withMerchant("Eda")
                .withType(PAYMENT)
                .build();
        Transaction t2 = TransactionBuilder.builder()
                .withAmount(15.0)
                .withMerchant(merchant)
                .withType(PAYMENT)
                .build();

        Map<String, Transaction> transactionMap = new HashMap<>();
        transactionMap.put("Bilal1", t0);
        transactionMap.put("Eda1", t1);
        transactionMap.put("Bilal2", t2);
        transactionAnalyser = new TransactionAnalyser(transactionMap);

        // when
        AnalysisResult result = transactionAnalyser.analyze(possibleMinFromDate, possibleMaxToDate, merchant);

        // then
        assertEquals(2, result.getNumberOfTransaction());
        assertEquals(25.0, result.getAverageTransactionValue());
    }

    @Test
    public void testGivenDateRangeThenFilterAnalyseForDateRange() throws ParseException {
        // given
        String merchant = "Bilal";
        Date fromDate = DateHelper.parseDate("20/08/2020 13:14:11");
        Date toDate = DateHelper.parseDate("20/10/2020 13:14:11");

        Transaction t0 = TransactionBuilder.builder()
                .withAmount(10.0)
                .withMerchant(merchant)
                .withDate("20/09/2020 12:14:11")
                .withType(PAYMENT)
                .build();
        Transaction t1 = TransactionBuilder.builder()
                .withAmount(5.0)
                .withMerchant(merchant)
                .withDate("20/10/2020 12:14:11")
                .withType(PAYMENT)
                .build();
        Transaction t2 = TransactionBuilder.builder()
                .withAmount(15.0)
                .withMerchant(merchant)
                .withDate("20/07/2020 12:14:11")
                .withType(PAYMENT)
                .build();

        Map<String, Transaction> transactionMap = new HashMap<>();
        transactionMap.put("Bilal1", t0);
        transactionMap.put("Bilal2", t1);
        transactionMap.put("Bilal3", t2);
        transactionAnalyser = new TransactionAnalyser(transactionMap);

        // when
        AnalysisResult result = transactionAnalyser.analyze(fromDate, toDate, merchant);

        // then
        assertEquals(2, result.getNumberOfTransaction());
        assertEquals(15.0, result.getAverageTransactionValue());
    }

    @Test
    public void testGivenReversalTransactionsThenFilterOutReversalTransactions() throws ParseException {
        // given
        String merchant = "Bilal";
        Date possibleMinFromDate = new Date(Long.MIN_VALUE);
        Date possibleMaxToDate = new Date(Long.MAX_VALUE);

        Transaction t0 = TransactionBuilder.builder()
                .withAmount(10.0)
                .withMerchant(merchant)
                .withDate("20/09/2020 12:14:11")
                .withType(PAYMENT)
                .build();
        Transaction t1 = TransactionBuilder.builder()
                .withAmount(5.0)
                .withMerchant(merchant)
                .withDate("20/10/2020 12:14:14")
                .withType(REVERSAL)
                .build();
        t0.addReversalTransaction(t1);

        Transaction t2 = TransactionBuilder.builder()
                .withAmount(35.0)
                .withMerchant(merchant)
                .withDate("20/07/2020 12:14:11")
                .withType(PAYMENT)
                .build();

        Map<String, Transaction> transactionMap = new HashMap<>();
        transactionMap.put("Bilal1", t0);
        transactionMap.put("Bilal2", t1);
        transactionMap.put("Bilal3", t2);
        transactionAnalyser = new TransactionAnalyser(transactionMap);

        // when
        AnalysisResult result = transactionAnalyser.analyze(possibleMinFromDate, possibleMaxToDate, merchant);

        // then
        assertEquals(2, result.getNumberOfTransaction());
        assertEquals(40.0, result.getAverageTransactionValue());
    }

    @Test
    public void testAllFiltersTogether() throws ParseException {
        // given
        String merchant = "Bilal";
        Date fromDate = DateHelper.parseDate("20/08/2020 13:14:11");
        Date toDate = DateHelper.parseDate("20/10/2020 13:14:11");

        Transaction hasReversalTransaction = TransactionBuilder.builder()
                .withAmount(10.0)
                .withMerchant(merchant)
                .withDate("20/09/2020 12:14:11")
                .withType(PAYMENT)
                .build();
        Transaction reversalTransaction = TransactionBuilder.builder()
                .withAmount(5.0)
                .withMerchant(merchant)
                .withDate("20/10/2020 14:14:11")
                .withType(REVERSAL)
                .build();
        hasReversalTransaction.addReversalTransaction(reversalTransaction);

        Transaction notInDateRangeTransaction = TransactionBuilder.builder()
                .withAmount(35.0)
                .withMerchant(merchant)
                .withDate("20/07/2020 12:14:11")
                .withType(PAYMENT)
                .build();
        Transaction validTransaction1 = TransactionBuilder.builder()
                .withAmount(35.0)
                .withMerchant(merchant)
                .withDate("20/09/2020 12:14:11")
                .withType(PAYMENT)
                .build();
        Transaction validTransaction2 = TransactionBuilder.builder()
                .withAmount(55.0)
                .withMerchant(merchant)
                .withDate("20/09/2020 11:14:11")
                .withType(PAYMENT)
                .build();
        Transaction notMatchingMerchantTransaction = TransactionBuilder.builder()
                .withAmount(35.0)
                .withMerchant("Eda1")
                .withDate("20/09/2020 12:14:11")
                .withType(PAYMENT)
                .build();

        Map<String, Transaction> transactionMap = new HashMap<>();
        transactionMap.put("Bilal1", hasReversalTransaction);
        transactionMap.put("Bilal2", reversalTransaction);
        transactionMap.put("Bilal3", notInDateRangeTransaction);
        transactionMap.put("Bilal4", validTransaction1);
        transactionMap.put("Bilal5", validTransaction2);
        transactionMap.put("Eda1", notMatchingMerchantTransaction);
        transactionAnalyser = new TransactionAnalyser(transactionMap);

        // when
        AnalysisResult result = transactionAnalyser.analyze(fromDate, toDate, merchant);

        // then
        assertEquals(2, result.getNumberOfTransaction());
        assertEquals(90.0, result.getAverageTransactionValue());
    }
}
