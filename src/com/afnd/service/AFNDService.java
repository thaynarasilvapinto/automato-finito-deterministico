package com.afnd.service;

import com.afnd.data.AFNDAutomaton;
import com.afnd.data.AFNDRule;
import com.afnd.repository.RuleAFNDRepository;
import com.afnd.view.InitialView;

import java.util.ArrayList;
import java.util.List;

public class AFNDService {


    AFNDRuleService afndRuleService = new AFNDRuleService();

    public void processSequence(List<Character> sequence, String currentState, List<AFNDRule> rules, int position) throws Exception {

        position = afndRuleService.injectList(position);
        int count = 0;

        for (char currentSymbol : sequence) {

            count = count + 1;

            AFNDRule applicableRule = afndRuleService.getApplicableRule(rules, currentState, currentSymbol);
            System.out.println("-------------");
            System.out.println(applicableRule.getSourceState());
            System.out.println(applicableRule.getSymbol());
            System.out.println(applicableRule.getTargetStates());
            System.out.println("-------------");
            if(applicableRule.getTargetStates().size() > 1) { //verifica a regra e aplica dentro de uma nova rule
                for (String targetStates : applicableRule.getTargetStates()) {
                    processSequence(
                            sequence.subList(count, sequence.size()),
                            targetStates,
                            rules,
                            position); //recursão
                }
            }
//            afndRuleService.addCoveredRule(applicableRule, position);
//            currentState = afndRuleService.applyRule(applicableRule);
        }
    }

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
