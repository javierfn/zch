package com.inditex.zarachallenge.shared;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String EXCEPTION_MESSAGE = "Exception message: {}";
    public static final String EXCEPTION = "Exception: ";
    public static final long LIMIT_LONG_MAX_NUMBER = 9_999_999_999L;
    public static final BigDecimal LIMIT_BIG_DECIMAL_MAX_NUMBER = BigDecimal.valueOf(9999999999.99);

}
