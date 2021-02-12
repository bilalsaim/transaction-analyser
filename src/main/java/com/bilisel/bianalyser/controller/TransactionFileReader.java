package com.bilisel.bianalyser.controller;

import com.bilisel.bianalyser.model.Transaction;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public interface TransactionFileReader {
    Map<String, Transaction> read(String location) throws IOException, ParseException;
}
