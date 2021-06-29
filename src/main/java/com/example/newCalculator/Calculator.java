package com.example.newCalculator;

public class Calculator {
    public Integer sum(Integer val1, Integer val2) {
        return val1 +val2;
    }
    public Integer substraction(Integer val1, Integer val2) {
        return val1 - val2;
    }
    public Integer multiply(Integer val1, Integer val2) {
        return val1 * val2;
    }
    public Integer division(Integer val1, Integer val2) {
        return val1 / val2;
    }
    public Integer exponentiation(Integer val1, Integer val2) {
        return (int) Math.pow(val1, val2);
    }
    public Double rootExtraction (Double val1, Double val2) {
        return  Math.pow(val1, 1/val2);
    }
    public Double logarithmExtraction(Double logNumber, Double base) {
        return Math.log(logNumber) / Math.log(base);
    }
}
