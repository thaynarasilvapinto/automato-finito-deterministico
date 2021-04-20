package com.afnd.view;

import com.afnd.data.AFDAutomaton;
import com.afnd.data.AFNDAutomaton;
import com.afnd.repository.AFDRuleRepository;

import javax.swing.*;
import java.awt.*;

public class ComparisonView extends JFrame {

    JButton validateButton = new JButton("VALIDAR");
    JButton changeButton = new JButton("TROCAR AUTOMATO");
    JTextField wordField = new JTextField();
    JLabel wordLabel = new JLabel("PALAVRA:");
    JLabel afnd = new JLabel("AFND");
    JLabel afd = new JLabel("AFD");

    AFDAutomaton afdAutomaton;
    AFNDAutomaton afndAutomaton;

    public ComparisonView(AFDAutomaton afdAutomaton, AFNDAutomaton afndAutomaton) {
        this.afdAutomaton = afdAutomaton;
        this.afndAutomaton = afndAutomaton;
        setSize(800, 580);
        setVisible(true);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        afnd.setBounds(180, 10, 230, 30);
        afnd.setForeground(Color.blue);
        add(afnd);

        afd.setBounds(580, 10, 220, 30);
        afd.setForeground(Color.blue);
        add(afd);

        wordField.setBounds(95, 470, 590, 20);
        wordField.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        add(wordField);

        wordLabel.setBounds(20, 470, 70, 20);
        add(wordLabel);

        validateButton.setBounds(700, 470, 80, 20);
        validateButton.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        add(validateButton);

        changeButton.setBounds(20, 500, 760, 20);
        changeButton.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        add(changeButton);

        JScrollPane paneAFND = new JScrollPane(buildTextArea(afndAutomaton.toString()));
        paneAFND.setBounds(20, 40, 360, 400);
        add(paneAFND);

        JScrollPane paneAFD = new JScrollPane(buildTextArea(afdAutomaton.toString()));
        paneAFD.setBounds(420, 40, 360, 400);
        add(paneAFD);

        setValidateButton();
        setChangeButton();

    }

    public JTextArea buildTextArea(String text) {
        JTextArea textArea = new JTextArea();
        textArea.setText(text);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createBevelBorder(1));
        textArea.setFont(new Font("", Font.PLAIN, 17));
        textArea.setEditable(false);
        textArea.setCaretPosition(0);

        return textArea;
    }

    //TODO
    public void setValidateButton() {
        validateButton.addActionListener(event -> {
            try {
                String sequence = wordField.getText();
                validateSequence(afdAutomaton.getAlphabet(), sequence);
                new AFDStepView(new AFDRuleRepository(), afdAutomaton, sequence);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void setChangeButton() {
        changeButton.addActionListener(event -> {
            new InitialView();
            dispose();
        });
    }

    public void validateSequence(String alphabet, String sequence) throws Exception {
        for (int i = 0; i < sequence.length(); i++) {
            boolean found = false;
            for (int j = 0; j < alphabet.length(); j++) {
                if (sequence.charAt(i) == alphabet.charAt(j)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new Exception("Elementos da cadeia devem pertencer ao alfabeto!");
            }
        }
    }

}
