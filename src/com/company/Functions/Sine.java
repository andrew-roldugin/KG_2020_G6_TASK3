package com.company.Functions;

import static java.lang.Math.sin;
/**
 * Класс, описывающий функции вида y=A*sin(W*x + F) + C
 */
public class Sine extends AbstractFunction {

    private boolean flag;

    public Sine(){
        this.getParams().put("A", 1.0);
        this.getParams().put("W", 1.0);
        this.getParams().put("F", .0);
        this.getParams().put("C", .0);
    }

    public double getA() {
        return this.getParams().get("A");
    }

    public Sine setA(double a) {
        this.getParams().put("A", a);
        return this;
    }

    public double getW() {
        return this.getParams().get("W");
    }

    public Sine setW(double w) {
        this.getParams().put("W", w);
        return this;
    }

    public double getF() {
        return this.getParams().get("F");
    }

    public Sine setF(double f) {
        this.getParams().put("F", f);
        return this;
    }

    public double getC() {
        return this.getParams().get("C");
    }

    public Sine setC(double c) {
        this.getParams().put("C", c);
        return this;
    }

    @Override
    public double compute(double x) {
        return getA() * sin(getW() * x + getF()) + getC();
    }

    public String getFuncBuilder() {
        return "start: y," +
                "symbol: =," +
                (flag ? "symbol: (," : "") +
                "param: A," +
                "func: sin," +
                "symbol: (," +
                "param: W," +
                "symbol: *," +
                "arg: x," +
                "op: +," +
                "param: F," +
                "symbol: )," +
                (!flag ? "op: +," + "param: C" : "symbol: )");
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void setNewValue(String key, double newValue) {
        switch (key){
            case "A":
                setA(newValue);
                break;
            case "W":
                setW(newValue);
                break;
            case "F":
                setF(newValue);
                break;
            case "C":
                setC(newValue);
                break;
            default:
                throw new IllegalArgumentException("Такого параметра нет");
        }
    }

    protected boolean getFlag() {
        return flag;
    }
}
