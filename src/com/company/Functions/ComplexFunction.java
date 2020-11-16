package com.company.Functions;

import java.util.HashMap;
import java.util.Map;

public class ComplexFunction extends AbstractFunction {
    private AbstractFunction function1, function2;
    private double rest;
    private char operator;

    public ComplexFunction(AbstractFunction function1, AbstractFunction function2, char operator, double rest) {
        this.function1 = function1;
        this.function2 = function2;
        this.operator = operator;
        this.rest = rest;
    }

    @Override
    public double compute(double x) {
        double result;
        switch (operator){
            case '+':
                result =function1.compute(x) + function2.compute(x);
                break;
            case '-':
                result = function1.compute(x) - function2.compute(x);
                break;
            case '/':
                result = function1.compute(x) / function2.compute(x);
                break;
            case '*':
                result = function1.compute(x) * function2.compute(x);
                break;
            default:
                return 0;
        }
        return result + rest;
    }

    @Override
    public Map<String, Double> getParams() {
        HashMap<String, Double> temp = new HashMap<String, Double>(function1.getParams());
        for (Map.Entry<String, Double> kv: function2.getParams().entrySet()) {
            if(function2 instanceof Cosine)
                temp.put(kv.getKey() + "1", kv.getValue());
            else
                temp.put(kv.getKey(), kv.getValue());
        }
        return temp;
    }

    @Override
    public String getFuncBuilder() throws Exception {
        if(function2 instanceof Cosine){
            ((Cosine) function2).setFlag(true);
        } else if(function2 instanceof InvExp){
            ((InvExp) function2).setFlag(true);
        }else{
            throw new Exception("Неподдерживаемая функция");
        }
        ((Sine) function1).setFlag(true);
        return  function1.getFuncBuilder() + ","  + "symbol: " + operator + "," + function2.getFuncBuilder() + "," + "op: +," + "rest: " + rest;
    }

    public char getOperator() {
        return operator;
    }

    @Override
    public void setNewValue(String key, double newValue) {
        if(key.length() == 2){
            function2.setNewValue(key.charAt(0) + "", newValue);
            return;
        }
        try{
            function1.setNewValue(key, newValue);
        }catch (IllegalArgumentException ex){
            function2.setNewValue(key, newValue);
        }
    }
    public double getRest() {
        return rest;
    }
    public void setRest(double rest) {
        this.rest = rest;
    }
    public AbstractFunction getFunction1() {
        return function1;
    }

    public AbstractFunction getFunction2() {
        return function2;
    }
}
