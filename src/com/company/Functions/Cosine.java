package com.company.Functions;

import static java.lang.Math.cos;

/**
 * Класс, описывающий функции вида y = A*cos(W*x + F) + C
 */
public class Cosine extends Sine {
    @Override
    public double compute(double x) {
        return getA() * cos(getW() * x + getF()) + getC();
    }

    public String getFuncBuilder() {
        return !super.getFlag() ? ("start: y," +
                "symbol: =," +
                "param: A," +
                "func: cos," +
                "symbol: (," +
                "param: W," +
                "symbol: *," +
                "arg: x," +
                "op: +," +
                "param: F," +
                "symbol: )," +
                "op: +," +
                "param: C" ) :
                ("symbol: (," +
                "param: A1," +
                "func: cos," +
                "symbol: (," +
                "param: W1," +
                "symbol: *," +
                "arg: x," +
                "op: +," +
                "param: F1," +
                "symbol: )," +
                "op: +," +
                "param: C1," +
                "symbol: )"
                );
    }

    @Override
    public void setNewValue(String key, double newValue) {
        super.setNewValue(key, newValue);
    }
}
