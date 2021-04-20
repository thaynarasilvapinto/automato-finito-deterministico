package com.afnd.data;

public class AFDRule {
    private String sourceState;
    private final char symbol;
    private String targetState;

    public AFDRule(String sourceState, char symbol, String targetState) {
        this.sourceState = sourceState;
        this.symbol = symbol;
        this.targetState = targetState;
    }

    public String getSourceState() {
        return sourceState;
    }

    public char getSymbol() {
        return symbol;
    }

    public String getTargetState() {
        return targetState;
    }

    @Override
    public String toString() {
        if(sourceState.equals("")) {
            sourceState = "qV";
        }
        if (targetState.equals("")) {
            targetState = "qV";
        }
        return sourceState + " \u00D7 " + symbol + " \u2192 " + targetState;
    }
}