package com.afne.service;

import com.afne.data.AFNDAutomaton;
import com.afne.data.AFNDRule;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputFileService {

    JSONArray jsonArray;
    JSONObject jsonField;

    public AFNDAutomaton parseAutomaton() throws Exception {
        File workingDirectory = new File(System.getProperty("user.dir"));
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(workingDirectory);

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                return parseFile(fileChooser.getSelectedFile().getAbsolutePath());
            } catch (Exception e) {
                throw new Exception("Arquivo inválido!");
            }
        }
        return null;
    }

    private AFNDAutomaton parseFile(String absolutePath) throws IOException, ParseException {
        jsonField = (JSONObject) new JSONParser().parse(new FileReader(absolutePath));

        jsonArray = (JSONArray) jsonField.get("estados");
        List<String> states = parseArrayField(jsonArray);

        String alphabet = parseAlphabet();

        List<AFNDRule> AFNDRules = parseRules();

        String initialState = parseInitialState();

        jsonArray = (JSONArray) jsonField.get("estadosFinais");
        List<String> finalStates = parseArrayField(jsonArray);

        return new AFNDAutomaton(states, alphabet, AFNDRules, initialState, finalStates);
    }

    private List<String> parseArrayField(JSONArray jsonArray) {
        List<String> array = new ArrayList<>();
        for (Object o : jsonArray) {
            array.add((String) o);
        }
        return array;
    }

    private String parseAlphabet() {
        return (String) jsonField.get("alfabeto");
    }

    private List<AFNDRule> parseRules() {
        List<AFNDRule> AFNDRules = new ArrayList<>();
        jsonArray = (JSONArray) jsonField.get("regras");
        for (Object rule : jsonArray) {
            JSONObject jsonRule = (JSONObject) rule;

            String sourceState = (String) jsonRule.get("estadoPartida");

            String symbol = (String) jsonRule.get("simbolo");

            JSONArray targets = (JSONArray) jsonRule.get("estadosDestino");
            List<String> targetStates = parseArrayField(targets);

            AFNDRules.add(new AFNDRule(sourceState, symbol.charAt(0), targetStates));
        }
        return AFNDRules;
    }

    private String parseInitialState() {
        return (String) jsonField.get("estadoInicial");
    }
}

