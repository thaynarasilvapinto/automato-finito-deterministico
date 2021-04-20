package com.afnd.service;

import com.afnd.data.AFDRule;
import com.afnd.repository.AFDRuleRepository;

import java.util.ArrayList;
import java.util.List;

public class AFDRuleService {
    AFDRuleRepository ruleRepository;

    public AFDRuleService(AFDRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    public AFDRule getApplicableRule(List<AFDRule> rules, String currentState, char currentSymbol) throws Exception {
        return rules.stream()
                .filter(rule -> isRuleApplicable(rule, currentState, currentSymbol))
                .findFirst()
                .orElseThrow(() -> new Exception("Regra não encontrada"));
    }

    private boolean isRuleApplicable(AFDRule rule, String currentState, char currentSymbol) {
        return rule.getSourceState().equals(currentState) && rule.getSymbol() == currentSymbol;
    }

    public void addCoveredRule(AFDRule applicableRule) {
        ruleRepository.coveredRules.add(applicableRule);
    }

    public String applyRule(AFDRule applicableRule) {
        return applicableRule.getTargetState();
    }


    public List<AFDRule> getCoveredRules() {
        return ruleRepository.coveredRules;
    }

    public void cleanCoveredRules() {
        ruleRepository.coveredRules = new ArrayList<>();
    }
}
