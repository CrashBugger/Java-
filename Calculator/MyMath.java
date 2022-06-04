package code;

import java.math.BigDecimal;

public class MyMath {
    private static int DEFAULT_SCALE = 2;

    public static double add(double num1, double num2) {
        BigDecimal first = getBigDecimal(num1);
        BigDecimal second = getBigDecimal(num2);
        return first.add(second).doubleValue();

    }

    public static double subtract(double num1, double num2) {
        BigDecimal first = getBigDecimal(num1);
        BigDecimal second = getBigDecimal(num2);
        return first.subtract(second).doubleValue();
    }

    public static double multiply(double num1, double num2) {
        BigDecimal first = getBigDecimal(num1);
        BigDecimal second = getBigDecimal(num2);
        return first.multiply(second).doubleValue();
    }

    public static double divide(double num1, double num2) {
        BigDecimal first = getBigDecimal(num1);
        BigDecimal second = getBigDecimal(num2);
        return first.divide(second, DEFAULT_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static BigDecimal getBigDecimal(double number) {
        return new BigDecimal(number);
    }
}
