package com.bilisel.bianalyser;

import com.bilisel.bianalyser.controller.InputOptions;
import com.bilisel.bianalyser.controller.TransactionCsvReader;
import com.bilisel.bianalyser.controller.TransactionFileReader;
import com.bilisel.bianalyser.logic.TransactionAnalyser;
import com.bilisel.bianalyser.model.AnalysisResult;
import com.bilisel.bianalyser.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        InputOptions inputs = parseArguments(args);

        Map<String, Transaction> transactions = getTransactions(inputs.getFileLocation());

        AnalysisResult analysisResult = getAnalysisResult(inputs, transactions);

        printAnalysisResult(analysisResult);
    }

    private static InputOptions parseArguments(String[] args) {
        InputOptions inputs = InputOptions.getInstance();

        try {
            inputs.readOptions(args);
        } catch (Exception e) {
            logger.error("Failed to read or parse the arguments: {}", e.getMessage());
            inputs.printHelp();
            System.exit(1);
        }

        return inputs;
    }

    private static Map<String, Transaction> getTransactions(String fileLocation) {
        try {
            TransactionFileReader transactionFileReader = TransactionCsvReader.getInstance();
            return transactionFileReader.read(fileLocation);
        } catch (IOException | ParseException e) {
            logger.error("Failed to read or parse transaction file: {}", e.getMessage());
            System.exit(1);
            return null;
        }
    }

    private static AnalysisResult getAnalysisResult(InputOptions inputs, Map<String, Transaction> transactions) {
        TransactionAnalyser transactionAnalyser = new TransactionAnalyser(transactions);
        return transactionAnalyser.analyze(inputs.getFromDate(), inputs.getToDate(), inputs.getMerchant());
    }

    private static void printAnalysisResult(AnalysisResult analysisResult) {
        System.out.println("Number of transactions = " + analysisResult.getNumberOfTransaction());
        System.out.println("Average Transaction Value = " + analysisResult.getAverageTransactionValue());
    }
}
