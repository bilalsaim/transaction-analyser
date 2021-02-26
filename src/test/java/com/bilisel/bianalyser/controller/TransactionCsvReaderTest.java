package com.bilisel.bianalyser.controller;

import com.bilisel.bianalyser.model.Transaction;
import com.bilisel.bianalyser.util.TransactionBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.Optional;

import static com.bilisel.bianalyser.model.TransactionType.PAYMENT;
import static com.bilisel.bianalyser.model.TransactionType.REVERSAL;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionCsvReaderTest {

    private TransactionCsvReader transactionCsvReader;
    private static final String DEFAULT_FILE_LOCATION = "testReader.csv";
    private String fileLocation;

    @BeforeEach
    public void setUp() {
        ClassLoader classLoader = getClass().getClassLoader();
        fileLocation = classLoader.getResource(DEFAULT_FILE_LOCATION).getFile();
    }

    @Test
    public void testCsvReader() throws IOException, ParseException {
        // given
        transactionCsvReader = TransactionCsvReader.getInstance();

        // when
        Map<String, Transaction> result = transactionCsvReader.read(fileLocation);

        // expected
        Transaction LFVCTEYM = TransactionBuilder.builder()
                .withDate("20/08/2020 12:50:02")
                .withMerchant("MacLaren")
                .withAmount(5.00)
                .withType(PAYMENT)
                .build("LFVCTEYM");

        Transaction YGXKOEIA = TransactionBuilder.builder()
                .withDate("20/08/2020 13:12:22")
                .withMerchant("Kwik-E-Mart")
                .withAmount(5.00)
                .withType(PAYMENT)
                .build("YGXKOEIA");

        Transaction AKNBVHMN = TransactionBuilder.builder()
                .withDate("20/08/2020 13:14:11")
                .withMerchant("Kwik-E-Mart")
                .withAmount(10.95)
                .withType(REVERSAL)
                .build("AKNBVHMN");

        YGXKOEIA.addReversalTransaction(AKNBVHMN);

        // then
        assertEquals(true, result.containsKey("LFVCTEYM"));
        assertEqualsTransactions(LFVCTEYM, result.get("LFVCTEYM"));

        assertEquals(true, result.containsKey("YGXKOEIA"));
        assertEqualsTransactions(YGXKOEIA, result.get("YGXKOEIA"));

        assertEquals(true, result.containsKey("AKNBVHMN"));
        Optional<Transaction> reversalActual = result.get("YGXKOEIA").getReversalTransactions().stream().findFirst();
        assertEquals(true, reversalActual.isPresent());
        assertEqualsTransactions(AKNBVHMN, reversalActual.get());

    }

    private void assertEqualsTransactions(Transaction t1, Transaction t2) {
        assertEquals(t1, t2);
        assertEquals(t1.getMerchant(), t2.getMerchant());
        assertEquals(t1.getDate(), t2.getDate());
        assertEquals(t1.getAmount(), t2.getAmount());
        assertEquals(t1.getReversalTransactions(), t2.getReversalTransactions());
        assertEquals(t1.getType(), t2.getType());
    }
}
