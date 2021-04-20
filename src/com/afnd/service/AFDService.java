package com.afnd.service;

import com.afnd.data.AFDAutomaton;
import com.afnd.data.AFDRule;
import com.afnd.repository.AFDRuleRepository;

import java.util.List;

public class AFDService {

    AFDRuleService ruleService;

    public AFDService(AFDRuleRepository ruleRepository) {
        this.ruleService = new AFDRuleService(ruleRepository);
    }

    public boolean belongsToLanguage(String sequence, AFDAutomaton M) throws Exception {
        String endState = processSequence(sequence, M.getInitialState(), M.getRules());
        return isAcceptableState(endState, M.getFinalStates());
    }

    private String processSequence(String sequence, String initialState, List<AFDRule> rules) throws Exception {
        String currentState = initialState;
        for (char currentSymbol : sequence.toCharArray()) {
            AFDRule applicableRule = ruleService.getApplicableRule(rules, currentState, currentSymbol);
            ruleService.addCoveredRule(applicableRule);
            currentState = ruleService.applyRule(applicableRule);
        }
        return currentState;
    }

    private boolean isAcceptableState(String state, List<String> acceptableStates) {
        return acceptableStates.contains(state);
    }
}
