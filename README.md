# Description

The goal of the system is to display statistical information about processed financial transactions.
<img src="https://bilalsaim.com/wp-content/uploads/2020/07/Logo-e1595765973754.png" align="right"
     alt="Size Limit logo by Anton Lovchikov" width="227" height="91">
     
![Build Status](https://github.com/bilalsaim/hoolah-transaction-analyser/workflows/Java%20CI%20with%20Maven/badge.svg)


## Usage

- [Arguments](#arguments)
  - `-m`   (or) `--merchant` [**Required**]
  - `-fd`   (or) `--fromDate` [**Required**]
  - `-td`   (or) `--toDate` [**Required**]
  - `-f`   (or) `--file` [**Optional** - Default value: **test.csv**(File inside the resources folder)]

Example without file location: 

`--fromDate "20/08/2020 12:00:00" --toDate "20/08/2020 13:00:00" --merchant "Kwik-E-Mart"`

Example with file location: 

`--fromDate "20/08/2020 12:00:00" --toDate "20/08/2020 13:00:00" --merchant "Kwik-E-Mart" --file "C:\\input.csv"`

I advise you to run the application firstly without --file argument. You can pick one of the way to test your input data:

### From Unit test

<details><summary><b>Show instructions</b></summary>

1. Goto MainTest.java inside the test folder.

2. Edit arguments inside userArgumentTest()

3. Execute the test

</details>

### From Intellij

<details><summary><b>Show instructions</b></summary>


1. Edit confiuration --> Program arguments --> Put your arguments there and run the application

</details>

### From command line

<details><summary><b>Show instructions</b></summary>

1. Goto project folder and create a jar file with maven:

    ```
    mvn clean assembly:assembly
    ```

2. Execute the following command with your paramaters:

    ```
    java -jar target/hoolah-transaction-analyser.jar --fromDate "20/08/2020 12:00:00" --toDate "20/08/2020 13:00:00" --merchant "Kwik-E-Mart" --file "C:\\input.csv"
    ```

</details>

