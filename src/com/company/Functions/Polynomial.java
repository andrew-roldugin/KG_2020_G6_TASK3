package com.company.Functions;

/**
 * Класс, описывающий функции вида y = A*x^3 + B*x^2 + C*x + D
 */
public class Polynomial extends AbstractFunction {

    public Polynomial(){
        this.getParams().put("A", 1.0);
        this.getParams().put("B", 1.0);
        this.getParams().put("C", 1.0);
        this.getParams().put("D", .0);
    }

    public double getA() {
        return this.getParams().get("A");
    }

    public Polynomial setA(double a) {
        this.getParams().put("A", a);
        return this;
    }

    public double getB() {
        return this.getParams().get("B");
    }

    public Polynomial setB(double b) {
        this.getParams().put("B", b);
        return this;
    }

    public double getC() {
        return this.getParams().get("C");
    }

    public Polynomial setC(double c) {
        this.getParams().put("C", c);
        return this;
    }

    public double getD() {
        return this.getParams().get("D");
    }

    public Polynomial setD(double d) {
        this.getParams().put("D", d);
        return this;
    }

    @Override
    public double compute(double x) {
        return x * x * x * getA() + x * x * getB() + x * getC() + getD();
    }

    @Override
    public String getFuncBuilder() {
        return "start: y," +
                "symbol: =," +
                "param: A," +
                "op: *," +
                "arg: x," +
                "op: ^," +
                "num: 3," +
                "op: +," +
                "param: B," +
                "op: *," +
                "arg: x," +
                "op: ^," +
                "num: 2," +
                "op: +," +
                "param: C," +
                "op: *," +
                "arg: x," +
                "op: +," +
                "param: D";
    }

    @Override
    public void setNewValue(String key, double newValue) {
        switch (key){
            case "A":
                setA(newValue);
                break;
            case "B":
                setB(newValue);
                break;
            case "C":
                setC(newValue);
                break;
            case "D":
                setD(newValue);
                break;
            default:
                throw new IllegalArgumentException("Такого параметра нет");
        }
    }
}
