package com.afnd.data;

import java.util.List;

public class AFNDAutomaton {
    private final List<String> states;
    private final String alphabet;
    private final List<AFNDRule> rules;
    private final String initialState;
    private final List<String> finalStates;

    public AFNDAutomaton(List<String> states, String alphabet, List<AFNDRule> rules, String initialState, List<String> finalStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.rules = rules;
        this.initialState = initialState;
        this.finalStates = finalStates;
    }

    public List<AFNDRule> getRules() {
        return rules;
    }

    public String getInitialState() {
        return initialState;
    }

    public List<String> getFinalStates() {
        return finalStates;
    }

    public List<String> getStates() {
        return states;
    }

    public String getAlphabet() {
        return alphabet;
    }

    @Override
    public String toString() {
        StringBuilder outputRules = new StringBuilder();
        outputRules.append("[\n");
        for (AFNDRule rule : rules) {
            outputRules.append("    ").append(rule.toString()).append(",\n");
        }
        outputRules.append("]");
        return "Q: " + states + ",\n" +
                "\u03A3: " + alphabet + ",\n" +
                "\u03B4: " + outputRules.toString() + ",\n" +
                "q0: " + initialState + ",\n" +
                "F: " + finalStates + "\n";
    }
}
