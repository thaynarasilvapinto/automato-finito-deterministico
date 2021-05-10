package com.afne.service;

import com.afne.data.AFNDRule;
import com.afne.repository.RuleAFNDRepository;

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

    public void addCoveredRule(AFNDRule applicableRule) {
        ruleRepository.coveredRules.add(applicableRule);
    }

    public void cleanCoveredRules() {
        ruleRepository.coveredRules = new ArrayList<>();
    }
}
