package com.bilisel.bianalyser;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testMain() throws IOException {
        // given
        String[] args = new String[6];
        args[0] = "-fromDate";
        args[1] = "20/08/2020 12:00:00";
        args[2] = "-toDate";
        args[3] = "20/08/2020 13:00:00";
        args[4] = "-merchant";
        args[5] = "Kwik-E-Mart";

        // when
        Main.main(args);

        // then
        String expectedOutput = String.format("Number of transactions = 1%n" + "Average Transaction Value = 59.99%n");
        assertEquals(outContent.toString(), expectedOutput);
    }
}
