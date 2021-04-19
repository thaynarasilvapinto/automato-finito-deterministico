package com.afnd.data;

import java.util.List;

public class AFNDAutomaton {
    private final List<String> states;
    private final String alphabet;
    private final List<AFNDRule> AFNDRules;
    private final String initialState;
    private final List<String> finalStates;

    public AFNDAutomaton(List<String> states, String alphabet, List<AFNDRule> AFNDRules, String initialState, List<String> finalStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.AFNDRules = AFNDRules;
        this.initialState = initialState;
        this.finalStates = finalStates;
    }

    public List<AFNDRule> getRules() {
        return AFNDRules;
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
}
