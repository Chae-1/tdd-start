package com.example.calculator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    @Test
    void add() {
        Calculator calculator = new Calculator();
        int result = calculator.add(1, 2);
        Assertions.assertEquals(3, result);

        int result2 = calculator.add(3, 4);
        Assertions.assertEquals(7, result2);
    }
}