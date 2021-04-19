package com.afnd.service;

import com.afnd.data.AFDAutomaton;
import com.afnd.data.AFDRule;
import com.afnd.data.AFNDAutomaton;
import com.afnd.data.AFNDRule;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

public class AFNDConverter {

    AFNDAutomaton afnd;
    AFDAutomaton afd;
    Map<String, List<String>> composition; // Ex: q0q1: [q0, q1]

    public AFNDConverter(AFNDAutomaton afnd) {
        this.afnd = afnd;
        this.afd = new AFDAutomaton(
                new ArrayList<>(),
                afnd.getAlphabet(),
                new ArrayList<>(),
                afnd.getInitialState(),
                new ArrayList<>()
        );
        this.composition = new HashMap<>();
    }

    public AFDAutomaton mapToAFD() {

        addAFDStateIfNew(singletonList(afnd.getInitialState()));

        while(true) {
            Optional<String> notMappedState = getFirstNotMappedState();
            if (!notMappedState.isPresent()) {
                return afd;
            }
            addAFDStateRules(notMappedState.get());
        }
    }

    private String addAFDStateIfNew(List<String> states) {
        String newState = appendNonRepetitiveStatesNames(states);

        if (afd.getStates().contains(newState)){
            return newState;
        }

        composition.put(newState, states);
        afd.getStates().add(newState);
        if(isFinal(states)){
            afd.getFinalStates().add(newState);
        }
        return newState;
    }

    private String appendNonRepetitiveStatesNames(List<String> states) {
        List<String> nonRepetitiveStates = expandStates(states);

        StringBuilder stringBUilder = new StringBuilder();
        nonRepetitiveStates.forEach(stringBUilder::append);
        return stringBUilder.toString();
    }

    private List<String> expandStates(List<String> states) {
        Set<String> resultStates = new HashSet<>();
        for(String state: states) {
            List<String> composedBy = composition.get(state);
            if (composedBy == null) {
                resultStates.add(state);
                continue;
            }
            resultStates.addAll(new HashSet<>(composedBy));
        }
        List<String> resultAsList = new ArrayList<>(resultStates);
        Collections.sort(resultAsList);
        return resultAsList;
    }

    private boolean isFinal(List<String> states) {
        return states.stream().anyMatch(afnd.getFinalStates()::contains);
    }

    private Optional<String> getFirstNotMappedState() {
        List<String> rulesSourceStates = afd.getRules().stream().map(AFDRule::getSourceState).collect(Collectors.toList());

        return afd.getStates().stream()
                .filter(state -> !rulesSourceStates.contains(state))
                .findFirst();
    }

    private void addAFDStateRules(String notMappedState) {
        for(int i = 0; i < afnd.getAlphabet().length(); i ++) {
            List<String> afdTargetStates = new ArrayList<>();
            for(String state : composition.get(notMappedState)) {
                for(AFNDRule rule: afnd.getRules()) {
                    if(acceptableRule(state, afnd.getAlphabet().charAt(i), rule)) {
                        afdTargetStates.addAll(rule.getTargetStates());
                    }
                }
            }
            afd.getRules().add( new AFDRule(
                    notMappedState,
                    afnd.getAlphabet().charAt(i),
                    addAFDStateIfNew(afdTargetStates)
            ));
        }
    }

    private boolean acceptableRule(String state, char symbol, AFNDRule rule) {
        return rule.getSourceState().equals(state) && rule.getSymbol() == symbol;
    }
}
