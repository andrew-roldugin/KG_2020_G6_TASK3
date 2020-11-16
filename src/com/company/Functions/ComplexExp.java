package com.company.Functions;

import static java.lang.StrictMath.exp;

/**
 * Класс, описывающий функции вида y = A * 1/(1+e^(-x)) + C
 */
public class ComplexExp extends AbstractFunction {

    public ComplexExp(){
        this.getParams().put("A", 1.0);
        this.getParams().put("C", .0);
    }

    public double getA() {
        return this.getParams().get("A");
    }

    public ComplexExp setA(double a) {
        this.getParams().put("A", a);
        return this;
    }

    public double getC() {
        return this.getParams().get("C");
    }

    public ComplexExp setC(double c) {
        this.getParams().put("C", c);
        return this;
    }

    @Override
    public double compute(double x) {
        return getA() * (1 / (1 + exp(-x))) + getC();
    }

    public String getFuncBuilder() {
        return "start: y," +
                "symbol: =," +
                "param: A," +
                "op: *," +
                "num: 1," +
                "op: /," +
                "symbol: (," +
                "num: 1," +
                "op: +," +
                "num: e," +
                "op: ^," +
                "symbol: (," +
                "symbol: -," +
                "arg: x," +
                "symbol: )," +
                "symbol: )," +
                "op: +," +
                "param: C"
                ;
    }

    @Override
    public void setNewValue(String key, double newValue) {
        switch (key){
            case "A":
                setA(newValue);
                break;
            case "C":
                setC(newValue);
                break;
            default:
                throw new IllegalArgumentException("Такого параметра нет");
        }
    }
}
