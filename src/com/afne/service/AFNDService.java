package com.afne.service;

import com.afne.data.AFNDAutomaton;
import com.afne.data.AFNDRule;
import com.afne.data.RuleDTO;

import java.util.ArrayList;
import java.util.List;

public class AFNDService {


    private final AFNDRuleService afndRuleService = new AFNDRuleService();
    private boolean sequenceValidate = false;


    public void belongsToLanguage(String sequence, AFNDAutomaton automaton) throws Exception {
        List<Character> sequenceRequest = new ArrayList<>();

        for (char item: sequence.toCharArray()) {
            sequenceRequest.add(item);
        }

        if(sequenceRequest.isEmpty()){
            List<String> initialState = new ArrayList<>();
            initialState.add(automaton.getInitialState());
            sequenceValidate = isAcceptable(initialState, automaton.getFinalStates());
            if(!sequenceValidate){
                AFNDRule applicableRule = afndRuleService.getApplicableRuleEmpty(automaton.getRules(), automaton.getInitialState());
                sequenceValidate = isAcceptable(applicableRule.getTargetStates(), automaton.getFinalStates());
            }

        }else{
            processSequence(sequenceRequest,
                    automaton.getInitialState(),
                    automaton.getRules(),
                    automaton.getFinalStates());
        }
    }

    public List<RuleDTO> getStackSequence(){
        return afndRuleService.ruleRepository.coveredRules;
    }

    public Boolean getSequenceValidate(){
        return sequenceValidate;
    }

    private void processSequence(List<Character> sequence,
                                String currentState,
                                List<AFNDRule> rules,
                                List<String> finalStates) throws Exception {
        int aux = 0;

        for (char currentSymbol : sequence) {
            aux = aux + 1;
            RuleDTO applicableRule = afndRuleService.getApplicableRule(rules, currentState, currentSymbol);

            afndRuleService.addCoveredRule(applicableRule);
            System.out.println("-------");
            System.out.println(applicableRule.getSourceState());
            System.out.println(applicableRule.getSymbol());
            System.out.println(applicableRule.getTargetStates());
            System.out.println(applicableRule.getEmptyStateRule());
            System.out.println("-------");
            if(applicableRule.getTargetStates().size() > 1) {
                for (String targetStates : applicableRule.getTargetStates()) {
                    if(sequence.subList(aux, sequence.size()).size() != 0){

                        if(applicableRule.getEmptyStateRule()){
                            processSequence(
                                    sequence,
                                    targetStates,
                                    rules,
                                    finalStates); //recursão
                        }else{
                            processSequence(
                                    sequence.subList(aux, sequence.size()),
                                    targetStates,
                                    rules,
                                    finalStates); //recursão
                        }
                    }
                }
            }else{
                if(applicableRule.getTargetStates().size() == 1) {
                    currentState = applicableRule.getTargetStates().get(0);
                }else if (applicableRule.getTargetStates().isEmpty()){
                    break;
                }
            }
        }
        isAcceptableState(finalStates);
        System.out.println("------------------------------");
        System.out.println(sequenceValidate);
        System.out.println("------------------------------");
    }

    private void isAcceptableState(List<String> finalStates){
        if(!sequenceValidate)
            sequenceValidate = isAcceptable(getStackSequence().get(getStackSequence().size()-1).getTargetStates(), finalStates);
    }

    private boolean isAcceptable(List<String> state, List<String> acceptableStates) {
        for (String item: state) {
            return acceptableStates.contains(item);
        }
        return false;
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
        }
    }

    public AFNDRuleService getAfndRuleService() {
        return afndRuleService;
    }

    public void setSequenceValidate(boolean sequenceValidate) {
        this.sequenceValidate = sequenceValidate;
    }
}
