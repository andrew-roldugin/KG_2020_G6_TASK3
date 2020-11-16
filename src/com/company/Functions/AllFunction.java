package com.company.Functions;

public enum AllFunction {
    SINUS("y = A + sin(W * x + F) + C"),
    COSINUS("y = A + cos(W * x + F) + C"),
    POLYNOMIAL("y = A * x^3 + B * x^2 + C * x + D"),
    INV_FUNCTION("x = A * y^3 + B * y^2 + C * y + D"),
    HYPERBOLA("y = A / (B * x + D) + C"),
    SIN_COS("y = A1 * sin(W1 * x + F1) * (A2 * cos(W2 * x + F2) + C2) + C1"),
    SIN_EXP("y = A * sin(W * x + F) * (e ^ (-x / T)) + C"),
    EXP_HYP("y = A * 1 / (1 + e^(-x)) + C");

    private final String eq;
    AllFunction(String eq){
        this.eq = eq;
    }
}
