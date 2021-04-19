package com.afnd.data;

import java.util.List;


public class AFDAutomaton {
    private final List<String> states;
    private final String alphabet;
    private final List<AFDRule> AFDRules;
    private final String initialState;
    private final List<String> finalStates;

    public AFDAutomaton(List<String> states, String alphabet, List<AFDRule> AFDRules, String initialState, List<String> finalStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.AFDRules = AFDRules;
        this.initialState = initialState;
        this.finalStates = finalStates;
    }

    public List<AFDRule> getRules() {
        return AFDRules;
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
