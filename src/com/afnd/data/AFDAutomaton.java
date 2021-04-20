package com.afnd.data;

import java.util.List;

public class AFDAutomaton {
    private final List<String> states;
    private final String alphabet;
    private final List<AFDRule> rules;
    private final String initialState;
    private final List<String> finalStates;

    public AFDAutomaton(List<String> states, String alphabet, List<AFDRule> rules, String initialState, List<String> finalStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.rules = rules;
        this.initialState = initialState;
        this.finalStates = finalStates;
    }

    public List<AFDRule> getRules() {
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
        StringBuilder outputStates = new StringBuilder();
        outputStates.append("[");
        for(int i = 0; i < states.size(); i++) {
            if (states.get(i).equals("")) {
                outputStates.append("qV");
            } else {
                outputStates.append(states.get(i));
            }
            if (i != states.size()-1) {
                outputStates.append(", ");
            }
        }
        outputStates.append("]");

        StringBuilder outputRules = new StringBuilder();
        outputRules.append("[\n");
        for (AFDRule rule : rules) {
            outputRules.append("     ").append(rule.toString()).append(",\n");
        }
        outputRules.append("]");

        StringBuilder outputAlphabet = new StringBuilder();
        outputAlphabet.append("[");
        for(int i = 0; i < alphabet.length(); i++) {
            outputAlphabet.append(alphabet.charAt(i));
            if (i != alphabet.length()-1) {
                outputAlphabet.append(", ");
            }
        }
        outputAlphabet.append("]");

        StringBuilder outputFinals = new StringBuilder();
        outputFinals.append("[");
        for(int i = 0; i < finalStates.size(); i++) {
            if (finalStates.get(i).equals("")) {
                outputFinals.append("qV");
            } else {
                outputFinals.append(states.get(i));
            }
            if (i != finalStates.size()-1) {
                outputFinals.append(", ");
            }
        }
        outputFinals.append("]");

        return "Q: " + outputStates + ",\n" +
                "\u03A3: " + outputAlphabet + ",\n" +
                "\u03B4: " + outputRules + ",\n" +
                "q\u2080: " + initialState + ",\n" +
                "F: " + outputFinals + "\n";
    }
}
