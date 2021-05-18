package com.afne.service;

import com.afne.data.AFNDRule;
import com.afne.data.RuleDTO;
import com.afne.repository.RuleAFNDRepository;

import java.util.ArrayList;
import java.util.List;

public class AFNDRuleService {

    RuleAFNDRepository ruleRepository = new RuleAFNDRepository();

    public List<AFNDRule> getApplicableRule(List<AFNDRule> rules, String currentState, char currentSymbol) throws Exception {

        AFNDRule afndRule =  rules.stream()
                .filter(rule -> isRuleApplicable(rule, currentState, currentSymbol))
                .findFirst()
                .orElseThrow(() -> new Exception("Regra não encontrada"));

        List<AFNDRule> afndRules = new ArrayList<>();
        afndRules.add(getApplicableRuleEmpty(rules, currentState));
        afndRules.add(afndRule);

        return afndRules;
    }

    public AFNDRule getApplicableRuleEmpty(List<AFNDRule> rules, String currentState) throws Exception {
        return rules.stream()
                .filter(rule -> isRuleApplicable(rule, currentState, 'ε'))
                .findFirst()
                .orElseThrow(() -> new Exception("Regra não encontrada"));
    }

    private boolean isRuleApplicable(AFNDRule rule, String currentState, char currentSymbol) {
        return rule.getSourceState().equals(currentState) && rule.getSymbol() == currentSymbol;
    }

    public void addCoveredRule(RuleDTO ruleDTO) {
        ruleRepository.coveredRules.add(ruleDTO);
    }

    public void cleanCoveredRules() {
        ruleRepository.coveredRules = new ArrayList<>();
    }
}
