package com.company.Functions;
/**
 * Класс, описывающий функции вида x = A*y^3 + B*y^2 + C*y + D
 */
public class InvFunction extends Polynomial{

    @Override
    public double compute(double x) {
        return super.compute(x);
    }

    public String getFuncBuilder() {
        return "start: x," +
                "symbol: =," +
                "param: A," +
                "op: *," +
                "arg: y," +
                "op: ^," +
                "num: 3," +
                "op: +," +
                "param: B," +
                "op: *," +
                "arg: y," +
                "op: ^," +
                "num: 2," +
                "op: +," +
                "param: C," +
                "op: *," +
                "arg: y," +
                "op: +," +
                "param: D";
    }

    @Override
    public void setNewValue(String key, double newValue) {
        super.setNewValue(key, newValue);
    }
}
