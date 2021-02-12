package com.bilisel.bianalyser.controller;

import com.bilisel.bianalyser.model.Transaction;
import com.bilisel.bianalyser.model.TransactionType;
import com.bilisel.bianalyser.util.DateHelper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TransactionCsvReader implements TransactionFileReader {

    private static TransactionCsvReader instance;

    private TransactionCsvReader() {
    }

    public Map<String, Transaction> read(String fileLocation) throws IOException, ParseException {

        File file = new File(fileLocation);

        Map<String, Transaction> modelMap = new HashMap();

        final CSVFormat format = CSVFormat.DEFAULT
                .withIgnoreEmptyLines(true)
                .withHeader()
                .withIgnoreSurroundingSpaces(true)
                .withDelimiter(',');

        CSVParser parser = CSVParser.parse(file, Charset.forName("UTF-8"), format);
        Iterator<CSVRecord> iterator = parser.iterator();

        while (iterator.hasNext()) {
            CSVRecord csv = iterator.next();

            csv.get("Date");
            Transaction model = new Transaction(
                    csv.get("ID"),
                    DateHelper.parseDate(csv.get("Date")),
                    Double.valueOf(csv.get("Amount")),
                    csv.get("Merchant"),
                    TransactionType.valueOf(csv.get("Type"))
            );

            if (!csv.get("Related Transaction").isEmpty()) {
                modelMap.get(csv.get("Related Transaction")).addReversalTransaction(model);
            }

            modelMap.put(model.getID(), model);
        }

        return modelMap;
    }

    public static TransactionCsvReader getInstance() {
        if (instance == null) {
            instance = new TransactionCsvReader();
        }
        return instance;
    }
}
