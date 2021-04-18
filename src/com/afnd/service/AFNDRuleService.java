package com.afnd.service;

import com.afnd.data.AFDRule;
import com.afnd.data.AFNDRule;
import com.afnd.repository.RuleAFNDRepository;

import java.util.ArrayList;
import java.util.List;

public class AFNDRuleService {

    List<RuleAFNDRepository> ruleRepository = new ArrayList<>();

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

    public void addCoveredRule(AFDRule applicableRule, int position) {
        ruleRepository.get(position).coveredRules.add(applicableRule);
    }

    public String applyRule(AFDRule applicableRule) {
        return applicableRule.getTargetState();
    }


    public List<AFDRule> getCoveredRules(int position) {
        return ruleRepository.get(position).coveredRules;
    }

    public void cleanCoveredRules(int position) {
        ruleRepository.get(position).coveredRules = new ArrayList<>();
    }

    public int injectList(int position){
        if(ruleRepository.isEmpty()){
            RuleAFNDRepository newRepository = new RuleAFNDRepository();
            ruleRepository.add(newRepository);
            return ruleRepository.size();
        }
        RuleAFNDRepository newRepository = new RuleAFNDRepository();
        newRepository.coveredRules.addAll(ruleRepository.get(position).coveredRules);
        ruleRepository.add(newRepository);
        return ruleRepository.size();
    }
}
