package cn.edu.dlut.mail.wuchen2020.calculator;

import org.junit.Test;

import brookelovesummer.OperateString2;

import static org.junit.Assert.*;

/**
 * Local unit test, which will execute on the development machine (host).
 * </br>
 * Used to test calculation.
 */
public class CalculationUnitTest {
    OperateString2 operator = new OperateString2();

    private static int factorial(int n) {
        if (n <= 0) return 1;
        return n * factorial(n - 1);
    }

    @Test
    public void isError() {
        final String formula = "++--";
        // assertThrows(Exception.class, () -> operator.calculate(formula));
        assertEquals("错误", operator.calculate(formula));
        assertEquals("错误", operator.calculate("错误"));
        assertEquals("错误", operator.calculate("0.0.0.0.0"));
        assertEquals("错误", operator.calculate("(1+1"));
        assertEquals("错误", operator.calculate("si"));
        assertEquals("错误", operator.calculate("cos"));
        assertEquals("错误", operator.calculate("tan()"));
        assertEquals("错误", operator.calculate("错误(1+1)"));
    }

    @Test
    public void addition_isCorrect() {
        assertEquals("2", operator.calculate("1+1"));
        assertEquals("-1", operator.calculate("1+-2"));
    }

    @Test
    public void subtraction_isCorrect() {
        assertEquals("1", operator.calculate("2-1"));
        assertEquals("-1", operator.calculate("1-2"));
        assertEquals("2", operator.calculate("1--1"));
    }

    @Test
    public void multiplication_isCorrect() {
        assertEquals("4", operator.calculate("2*2"));
        assertEquals("4.92", operator.calculate("1.23*4"));
        assertEquals("-39996", operator.calculate("-9999*4"));
    }

    @Test
    public void division_isCorrect() {
        assertEquals("0.25", operator.calculate("1/4"));
        assertEquals("2", operator.calculate("4/2"));
        assertEquals("-2499.75", operator.calculate("-9999/4"));
    }

    @Test
    public void pow_isCorrect() {
        assertEquals("4", operator.calculate("2^2"));
        assertEquals("8", operator.calculate("2^3"));
        assertEquals("2", operator.calculate("√(2^2)"));
        assertEquals("2", operator.calculate("4^0.5"));
        assertEquals("3", operator.calculate("9^(1/2)"));
        assertEquals("0.5", operator.calculate("4^-0.5"));
        assertEquals(operator.calculate("2*√2"), operator.calculate("8^(1/2)"));
        assertEquals("2", operator.calculate("ln(e^2)"));
        assertEquals("-0.25", operator.calculate("ln(e^(-1/4))"));
        assertEquals(operator.calculate("e"), operator.calculate("ln(e^e)"));
    }

    @Test
    public void sqrt_isCorrect() {
        assertEquals("1", operator.calculate("√1"));
        assertEquals("1.41421", operator.calculate("√2"));
        assertEquals("2", operator.calculate("√4"));
        assertEquals(operator.calculate("2*√2"), operator.calculate("√8"));
    }

    @Test
    public void log_isCorrect() {
        assertEquals("1", operator.calculate("ln(e)"));
        assertEquals("2", operator.calculate("ln(e^2)"));
        assertEquals("1", operator.calculate("lg(10)"));
        assertEquals("2", operator.calculate("lg(100)"));
        assertEquals(operator.calculate("2*lg(2)"), operator.calculate("lg(4)"));
        assertEquals(operator.calculate("3*ln(e)"), operator.calculate("ln(e^3)"));
    }

    @Test
    public void factorial_isCorrect() {
        for (int i = 1; i < 10; ++i) {
            assertEquals(String.valueOf(factorial(i)), operator.calculate(i + "!"));
        }
    }

    @Test
    public void tri_func_isCorrect() {
        assertEquals("1", operator.calculate("sin(π/2)"));
        assertEquals("-1", operator.calculate("cos(-π)"));
        assertEquals("∞", operator.calculate("tan(π/2)"));
    }

    @Test
    public void arc_tri_func_isCorrect() {
        assertEquals("0", operator.calculate("arcsin(0)"));
        assertEquals("0", operator.calculate("arccos(1)"));
        assertEquals(operator.calculate("π/4"), operator.calculate("arctan(1)"));
        // 额外测试
        assertEquals(operator.calculate("π/4"), operator.calculate("arcsin(√2/2)"));
    }

    // 最后的测试
    @Test
    public void complex_isCorrect() {
        String result;
        result = String.valueOf(3*0.27/65+77);
        assertEquals(result.substring(0, result.indexOf('.') + 6), operator.calculate("3*27%/65+77"));
        assertEquals("32.75", operator.calculate("(5+6)*4-9*(5/4)"));
        result = String.valueOf(Math.log(Math.E * Math.E) * Math.cos(Math.PI / 4) + Math.sqrt(3));
        System.out.println(result);
        assertEquals(result.substring(0, result.indexOf('.') + 6), operator.calculate("ln(e^2)*cos(π/4)+√3"));
    }
}
