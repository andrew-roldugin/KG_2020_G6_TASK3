package com.company.Functions;

/**
 * Класс, описывающий функции вида y = A / (B*x+D) + C
 */
public class Hyperbola extends AbstractFunction {

    public Hyperbola(){
        this.getParams().put("A", 1.0);
        this.getParams().put("B", 1.0);
        this.getParams().put("C", .0);
        this.getParams().put("D", .0);
    }

    public double getA() {
        return this.getParams().get("A");
    }

    public Hyperbola setA(double a) {
        this.getParams().put("A", a);
        return this;
    }

    public double getB() {
        return this.getParams().get("B");
    }

    public Hyperbola setB(double b) {
        this.getParams().put("B", b);
        return this;
    }

    public double getC() {
        return this.getParams().get("C");
    }

    public Hyperbola setC(double c) {
        this.getParams().put("C", c);
        return this;
    }

    public double getD() {
        return this.getParams().get("D");
    }

    public Hyperbola setD(double d) {
        this.getParams().put("D", d);
        return this;
    }

    @Override
    public double compute(double x) {
       return getA() / (getB() * x + getD()) + getC();
    }

    public String getFuncBuilder() {
        return "start: y," +
                "symbol: =," +
                "param: A," +
                "op: /," +
                "symbol: (," +
                "param: B," +
                "op: *," +
                "arg: x," +
                "op: +," +
                "param: D," +
                "symbol: )," +
                "op: +," +
                "param: C";
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
