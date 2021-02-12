package com.bilisel.bianalyser.controller;

import org.apache.commons.cli.MissingOptionException;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InputOptionsTest {
    private InputOptions inputOptions = InputOptions.getInstance();

    private String merchantValue = "Bilal";
    private String fromDateValue = "20/08/2020 12:50:02";
    private String toDateValue = "20/09/2020 12:50:02";

    @Test
    public void testGivenMissingOptionMerchantThenThrowMissingOptionException() throws Exception {
        // given
        String[] args = new String[4];
        args[0] = "-fd";
        args[1] = fromDateValue;
        args[2] = "-td";
        args[3] = toDateValue;

        // then
        assertThrows(MissingOptionException.class, () -> {
            // when
            inputOptions.readOptions(args);
        });
    }

    @Test
    public void testGivenMissingOptionFromDateThenThrowMissingOptionException() throws Exception {
        // given
        String[] args = new String[4];
        args[0] = "-td";
        args[1] = toDateValue;
        args[2] = "-m";
        args[3] = merchantValue;

        // then
        assertThrows(MissingOptionException.class, () -> {
            // when
            inputOptions.readOptions(args);
        });
    }

    @Test
    public void testGivenMissingOptionToDateThenThrowMissingOptionException() throws Exception {
        // given
        String[] args = new String[4];
        args[0] = "-fd";
        args[1] = fromDateValue;
        args[2] = "-m";
        args[3] = merchantValue;

        // then
        assertThrows(MissingOptionException.class, () -> {
            // when
            inputOptions.readOptions(args);
        });
    }

    @Test
    public void testGivenAllRequiredOptionsThenNotThrowMissingOptionException() throws Exception {
        // given
        String[] args = new String[6];
        args[0] = "-fd";
        args[1] = fromDateValue;
        args[2] = "-td";
        args[3] = toDateValue;
        args[4] = "-m";
        args[5] = merchantValue;

        // when
        inputOptions.readOptions(args);

        // then
        assertEquals(merchantValue, inputOptions.getMerchant());
    }

    @Test
    public void testGivenWrongFromDateFormatThenThrowParseException() throws Exception {
        // given
        String[] args = new String[6];
        args[0] = "-fd";
        args[1] = "20--08-2020 12:50:02";
        args[2] = "-td";
        args[3] = toDateValue;
        args[4] = "-m";
        args[5] = merchantValue;

        // then
        assertThrows(ParseException.class, () -> {
            // when
            inputOptions.readOptions(args);
        });
    }

    @Test
    public void testGivenWrongToDateFormatThenThrowParseException() throws Exception {
        // given
        String[] args = new String[6];
        args[0] = "-fd";
        args[1] = fromDateValue;
        args[2] = "-td";
        args[3] = "20-09-2020 12:50:02";
        args[4] = "-m";
        args[5] = merchantValue;

        // then
        assertThrows(ParseException.class, () -> {
            // when
            inputOptions.readOptions(args);
        });
    }

    @Test
    public void testNotGivenFileOptionThenReturnDefaultFileLocation() throws Exception {
        // given
        String[] args = new String[6];
        args[0] = "-fd";
        args[1] = fromDateValue;
        args[2] = "-td";
        args[3] = toDateValue;
        args[4] = "-m";
        args[5] = merchantValue;

        // when
        inputOptions.readOptions(args);

        // then
        boolean isFileLocationContainTestCsvFile = inputOptions.getFileLocation().endsWith("test.csv");
        assertEquals(true, isFileLocationContainTestCsvFile);
    }

    @Test
    public void testGivenFileOptionThenReturnGivenFileLocation() throws Exception {
        // given
        String[] args = new String[8];
        args[0] = "-fd";
        args[1] = fromDateValue;
        args[2] = "-td";
        args[3] = toDateValue;
        args[4] = "-m";
        args[5] = merchantValue;
        args[6] = "-f";
        args[7] = "C:\\input.csv";

        // when
        inputOptions.readOptions(args);

        // then
        assertEquals("C:\\input.csv", inputOptions.getFileLocation());
    }
}
