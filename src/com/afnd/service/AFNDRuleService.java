package com.afnd.service;

import com.afnd.data.AFDRule;
import com.afnd.data.AFNDRule;
import com.afnd.repository.RuleAFNDRepository;

import java.util.ArrayList;
import java.util.List;

public class AFNDRuleService {

    RuleAFNDRepository ruleRepository = new RuleAFNDRepository();

    public AFNDRule getApplicableRule(List<AFNDRule> rules, String currentState, char currentSymbol) throws Exception {
        return rules.stream()
                .filter(rule -> isRuleApplicable(rule, currentState, currentSymbol))
                .findFirst()
                .orElseThrow(() -> new Exception("Regra não encontrada"));
    }

    private boolean isRuleApplicable(AFNDRule rule, String currentState, char currentSymbol) {
        return rule.getSourceState().equals(currentState) && rule.getSymbol() == currentSymbol;
    }

    public int countApplicableRules(List<AFNDRule> rules, String currentState, char currentSymbol) {
        return (int) rules.stream()
                .filter(rule -> isRuleApplicable(rule, currentState, currentSymbol))
                .count();
    }

    public void addCoveredRule(AFNDRule applicableRule) {
        ruleRepository.coveredRules.add(applicableRule);
    }

    public String applyRule(AFDRule applicableRule) {
        return applicableRule.getTargetState();
    }


    public List<AFNDRule> getCoveredRules(int position) {
        return ruleRepository.coveredRules;
    }

    public void cleanCoveredRules(int position) {
        ruleRepository.coveredRules = new ArrayList<>();
    }
}
