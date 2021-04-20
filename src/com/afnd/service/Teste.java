package com.afnd.service;

import com.afnd.data.AFNDRule;

import java.util.ArrayList;
import java.util.List;

public class Teste {

    public static void main(String[] args) throws Exception {


        List<AFNDRule> rules = new ArrayList<>();

        List<String> targetStat = new ArrayList<>();

        targetStat.add("q1");
        targetStat.add("q2");
        rules.add(new AFNDRule("q0", '0', targetStat));

        targetStat = new ArrayList<>();
        targetStat.add("q0");
        rules.add(new AFNDRule("q0", '1', targetStat));

        targetStat = new ArrayList<>();
        targetStat.add("q1");
        rules.add(new AFNDRule("q1", '1', targetStat));

        targetStat = new ArrayList<>();
        targetStat.add("q1");
        rules.add(new AFNDRule("q1", '0', targetStat));

        targetStat = new ArrayList<>();
        targetStat.add("q3");
        rules.add(new AFNDRule("q2", '1', targetStat));

        targetStat = new ArrayList<>();
        targetStat.add("q2");
        rules.add(new AFNDRule("q2", '0', targetStat));

        List<Character> sequence = new ArrayList<>();
        sequence.add('0');
        sequence.add('1');

        AFNDService service = new AFNDService();


        List<String> finalStates = new ArrayList<>();
        finalStates.add("q3");


        service.processSequence(sequence, "q0", rules, finalStates);
        List<AFNDRule> response = service.getStackSequence();
        System.out.println(service.getSequenceValidate());
    }
}
