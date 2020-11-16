package com.company.Functions;

import static java.lang.Math.exp;

/**
 * Класс, описывающий функции вида y = e^(-x/T)
 */
public class InvExp extends AbstractFunction {

    private boolean flag;

    public InvExp(){
        this.getParams().put("T", 1.0);
    }

    public double getT() {
        return this.getParams().get("T");
    }

    public InvExp setT(double t) {
        this.getParams().put("T", t);
        return this;
    }

    @Override
    public double compute(double x) {
        return getT() == 0 ? Double.POSITIVE_INFINITY : exp(-x / getT());
    }

    public String getFuncBuilder() {
        return !flag ? ("start: y," +
                "symbol: =,") : "" +
                "num: e," +
                "op: ^," +
                "symbol: (," +
                "symbol: -," +
                "arg: x," +
                "op: /," +
                "param: T," +
                "symbol: )";
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void setNewValue(String key, double newValue) {
        if ("T".equals(key)) {
            setT(newValue);
        } else {
            throw new IllegalArgumentException("Такого параметра нет");
        }
    }
}
