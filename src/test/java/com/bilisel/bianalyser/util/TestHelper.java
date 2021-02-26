package com.bilisel.bianalyser.util;

import java.math.BigDecimal;

public class TestHelper {

    public static BigDecimal createDecimal(double val) {
        return new BigDecimal(val).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
