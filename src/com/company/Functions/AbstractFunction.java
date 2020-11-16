package com.company.Functions;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractFunction implements IFunction {

    private Map<String, Double> params = new HashMap<>();

    public Map<String, Double> getParams() {
        return params;
    }

    public abstract String getFuncBuilder() throws Exception;

    public abstract void setNewValue(String key, double newValue);
}
