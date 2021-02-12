package com.bilisel.bianalyser.controller;

import com.bilisel.bianalyser.util.DateHelper;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class InputOptions {

    private static Logger logger = LoggerFactory.getLogger(InputOptions.class);
    private static InputOptions instance;
    private final String DEFAULT_FILE_LOCATION = "test.csv";

    private Date fromDate;
    private Date toDate;
    private String merchant;
    private String fileLocation;
    private Options options = new Options();

    private InputOptions() {
    }

    public void readOptions(String[] args) throws Exception {

        Option fromDateOption = new Option("fd", "fromDate", true, "from date");
        fromDateOption.setRequired(true);
        options.addOption(fromDateOption);

        Option toDateOption = new Option("td", "toDate", true, "to date");
        toDateOption.setRequired(true);
        options.addOption(toDateOption);

        Option merchantOption = new Option("m", "merchant", true, "Merchant");
        merchantOption.setRequired(true);
        options.addOption(merchantOption);

        Option csvOption = new Option("f", "file", true, "File Location");
        options.addOption(csvOption);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        fromDate = DateHelper.parseDate(cmd.getOptionValue("fromDate"));
        toDate = DateHelper.parseDate(cmd.getOptionValue("toDate"));
        merchant = cmd.getOptionValue("merchant");
        fileLocation = cmd.getOptionValue("file");

        if (fileLocation == null || fileLocation.isEmpty()) {
            ClassLoader classLoader = getClass().getClassLoader();
            fileLocation = classLoader.getResource(DEFAULT_FILE_LOCATION).getFile();
        }

        logger.debug("Input fromDate: {}, toDate: {}, merchant: {}, fileLocation: {}",
                fromDate, toDate, merchant, fileLocation);
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public String getMerchant() {
        return merchant;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public static InputOptions getInstance() {
        if (instance == null) {
            instance = new InputOptions();
        }
        return instance;
    }

    public void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Merchant", options);
    }
}
