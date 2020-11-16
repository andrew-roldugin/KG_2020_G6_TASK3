package com.company.Functions;

public class FunctionFactory {
    public AbstractFunction getFunction(AllFunction type) {
        AbstractFunction function;
        switch (type){
            case SINUS:
                function = new Sine();
                break;
            case POLYNOMIAL:
                function = new Polynomial();
                break;
            case EXP_HYP:
                function = new ComplexExp();
                break;
            case HYPERBOLA:
                function = new Hyperbola();
                break;
            case INV_FUNCTION:
                function = new InvFunction();
                break;
            case SIN_COS:
                function = new ComplexFunction(new Sine(), new Cosine(), '*', 0);
                break;
            case SIN_EXP:
                function = new ComplexFunction(new Sine(), new InvExp(), '*', 0);
                break;
            case COSINUS:
                function = new Cosine();
                break;
            default:
                throw new IllegalArgumentException("Error!");
        }
        return function;
    }
}
