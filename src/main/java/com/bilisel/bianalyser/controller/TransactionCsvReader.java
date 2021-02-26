package com.bilisel.bianalyser.controller;

import com.bilisel.bianalyser.model.Transaction;
import com.bilisel.bianalyser.model.TransactionType;
import com.bilisel.bianalyser.util.DateHelper;
import com.univocity.parsers.common.routine.InputDimension;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvRoutines;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class TransactionCsvReader implements TransactionFileReader {

    private static TransactionCsvReader instance;

    private TransactionCsvReader() {
    }

    public Map<String, Transaction> read(String fileLocation) throws IOException, ParseException {

        File file = new File(fileLocation);

        int mapCapacity = calculateMapCapacity(file);
        Map<String, Transaction> modelMap = new HashMap(mapCapacity);

        CsvParserSettings settings = new CsvParserSettings();
        settings.setIgnoreLeadingWhitespaces(true);
        settings.setHeaderExtractionEnabled(true);

        CsvParser parser = new CsvParser(settings);
        String[] row;
        parser.beginParsing(file);

        while ((row = parser.parseNext()) != null) {
            Transaction model = new Transaction(
                    row[0],
                    DateHelper.parseDate(row[1]),
                    new BigDecimal(row[2]),
                    row[3],
                    TransactionType.valueOf(row[4])
            );

            if (row[5] != null && !row[5].isEmpty()) {
                modelMap.get(row[5]).addReversalTransaction(model);
            }

            modelMap.put(model.getID(), model);
        }

        return modelMap;
    }

    private int calculateMapCapacity(File file) {
        InputDimension inputDimension = new CsvRoutines().getInputDimension(file);
        return (int) ((inputDimension.rowCount()) / 0.75 + 1);
    }

    public static TransactionCsvReader getInstance() {
        if (instance == null) {
            instance = new TransactionCsvReader();
        }
        return instance;
    }
}
