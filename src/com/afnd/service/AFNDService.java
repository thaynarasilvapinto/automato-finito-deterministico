package com.afnd.service;

import com.afnd.data.AFNDAutomaton;
import com.afnd.data.AFNDRule;

import java.util.List;

public class AFNDService {

    public void validateAutomaton(AFNDAutomaton automaton) throws Exception {
        List<String> states = automaton.getStates();
        List<String> finalStates = automaton.getFinalStates();
        String initialState = automaton.getInitialState();
        String alphabet = automaton.getAlphabet();
        List<AFNDRule> rules = automaton.getRules();

        validateStates(states);
        validateInitialStates(states, initialState);
        validateFinalStates(states, finalStates);
        validateRules(rules, states, alphabet);
    }

    private void validateStates(List<String> states) throws Exception {
        if (states.isEmpty())
            throw new Exception("Conjunto de estados deve possuir pelo menos um estado");
    }

    private void validateInitialStates(List<String> states, String initialState) throws Exception {
        if(!states.contains(initialState))
            throw new Exception("Estado inicial deve pertencer ao conjunto de estados!");
    }

    private void validateFinalStates(List<String> states, List<String> finalStates) throws Exception {
        if (finalStates.isEmpty())
            throw new Exception("Deve haver pelo menos um estado final");
        for (String finalState : finalStates) {
            if (!states.contains(finalState))
                throw new Exception("Estados finais devem pertencer ao conjunto de estados!");
        }
    }

    private void validateRules(List<AFNDRule> rules, List<String> states, String alphabet) throws Exception {
        for (AFNDRule rule: rules) {
            for (String targetState: rule.getTargetStates()) {
               if (!states.contains(targetState))
                   throw new Exception("Estados das regras devem pertencer ao conjunto de estados!");
            }
            if(!states.contains(rule.getSourceState()))
                throw new Exception("Estados das regras devem pertencer ao conjunto de estados!");
            if(alphabet.indexOf(rule.getSymbol()) == -1)
                throw new Exception("Símbolos das regras devem pertencer ao alfabeto!");
        }
    }
}
