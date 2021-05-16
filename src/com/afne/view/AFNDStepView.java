package com.afne.view;


import com.afne.data.AFNDAutomaton;
import com.afne.data.AFNDRule;
import com.afne.data.RuleDTO;
import com.afne.service.AFNDService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AFNDStepView extends JFrame{

    private final JTextField textField;

    private final JPanel panel = new JPanel();
    private final JButton beforeButton = new JButton("<<<");
    private final JButton nextButton = new JButton(">>>");
    private final JButton finishButton = new JButton("FINALIZAR");
    private final JButton sendButton = new JButton("ENVIAR");
    private final JButton changeAutomatonButton = new JButton("TROCAR AUTÔMATO");

    private final AFNDAutomaton automaton;
    private final AFNDService automatonService;

    String sequence;
    List<RuleDTO> coveredRules;
    int sequenceIndex = -1;
    boolean validSequenceFlag;

    public AFNDStepView(AFNDAutomaton automaton) {

        this.automatonService = new AFNDService();
        this.automaton = automaton;

        setVisible(true);
        setResizable(false);
        setSize(400, 335);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel text = new JLabel("CADEIA:");
        text.setBounds(20, 10, 55, 30);
        add(text);

        textField = new JTextField();
        textField.setBounds(78, 15, 205, 20);
        add(textField);

        panel.setBounds(20, 50, 360, 150);
        panel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 128)));
        panel.setBackground(new Color(255, 255, 255));
        add(panel);

        sendButton.setBounds(290, 15 , 90, 20);
        add(sendButton);

        changeAutomatonButton.setBounds(20, 265, 360, 20);
        add(changeAutomatonButton);

        beforeButton.setBorder(BorderFactory.createEtchedBorder());
        beforeButton.setBounds(20, 220, 60, 20);
        add(beforeButton);

        finishButton.setBorder(BorderFactory.createEtchedBorder());
        finishButton.setBounds(160, 210, 80, 40);
        add(finishButton);

        nextButton.setBorder(BorderFactory.createEtchedBorder());
        nextButton.setBounds(320, 220, 60, 20);
        add(nextButton);

        enableOutputButtons(false);

        this.beforeButtonAction();
        this.nextButtonAction();
        this.finishButtonAction();

        this.sendButtonAction();
        this.changeAutomatonButtonAction();
    }

    private void enableOutputButtons(boolean flag) {
        beforeButton.setEnabled(flag);
        finishButton.setEnabled(flag);
        nextButton.setEnabled(flag);
    }

    public void sendButtonAction() {
        sendButton.addActionListener(event -> {
            try {
                clean();
                sequence = textField.getText();
                processSequence();
                enableOutputButtons(true);

                sequenceIndex = 0;
                panel.add(resultPanel(coveredRules));
                panel.repaint();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void changeAutomatonButtonAction() {
        changeAutomatonButton.addActionListener(e -> {
            dispose();
            new InitialView();
        });
    }

    private void processSequence() {
        try {
            automatonService.belongsToLanguage(sequence, automaton);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        coveredRules = automatonService.getStackSequence();
        validSequenceFlag = automatonService.getSequenceValidate();

    }

    public void beforeButtonAction() {
        beforeButton.addActionListener(e -> {
            if(sequenceIndex != 0){
                sequenceIndex = sequenceIndex - 1;
                panel.remove(0);
                panel.add(resultPanel(coveredRules));
            }
            panel.repaint();
        });
    }

    public void nextButtonAction() {
        nextButton.addActionListener(e -> {
            if(sequenceIndex != (coveredRules.size() - 1)){
                sequenceIndex = sequenceIndex + 1;
                panel.remove(0);
                panel.add(resultPanel(coveredRules));
            }
            panel.repaint();
        });
    }

    public void finishButtonAction() {
        finishButton.addActionListener(e -> {
            if (validSequenceFlag){
                JOptionPane.showMessageDialog(null,
                        "Cadeia pertence à linguagem representada pelo automato!", "Pertence :D",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Cadeia não pertence à linguagem representada pelo automato!", "Não pertence :(",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private JPanel resultPanel(List<RuleDTO> rules) {

        JPanel panel = new JPanel();

        JTextField sequenceLabel = new JTextField();
        JLabel currentStateLabel = new JLabel();
        //JLabel emptyStateRuleLabel = new JLabel();
        JLabel ruleLabel = new JLabel();
        JLabel acceptableStatesLabel = new JLabel();

        sequenceLabel.setBounds(10, 10, 345, 25);
        //emptyStateRuleLabel.setBounds(10, 20, 345, 25);
        sequenceLabel.setBorder(BorderFactory.createLineBorder(Color.white));

        currentStateLabel.setBounds(10, 50, 345, 20);
        //acceptableStatesLabel.setBounds(10, 40, 345, 20);

        if (rules.isEmpty()) {
            sequenceLabel.setText("Cadeia vazia");
            sequenceLabel.setFont(new Font(null, Font.PLAIN, 15));
            currentStateLabel.setText("Estado inicial: " + automaton.getInitialState());
        } else {
            RuleDTO rule = rules.get(sequenceIndex);

            sequenceLabel.setText("CADEIA: " + sequence);
            currentStateLabel.setText("Estado atual: " + rule.getTargetStates());
            //emptyStateRuleLabel.setText("Usou transição vazia: " + rule.getEmptyStateRule());

            ruleLabel.setBounds(10, 110, 345, 20);
            ruleLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            ruleLabel.setText("{"
                    + rule.getSourceState() + ", "
                    + rule.getSymbol() + " ,"
                    + rule.getTargetStates() + "}");
            panel.add(ruleLabel);
        }
        acceptableStatesLabel.setText("Estados de aceitação: " + automaton.getFinalStates());

        panel.add(sequenceLabel);
        panel.add(currentStateLabel);
        //panel.add(emptyStateRuleLabel);
        panel.add(acceptableStatesLabel);

        panel.setBounds(1, 1, 345, 145);
        panel.setBackground(new Color(255, 255, 255));

        return panel;
    }

    private void clean(){

        if(sequenceIndex != -1){
            panel.remove(0);
            coveredRules = null;
            automatonService.getAfndRuleService().cleanCoveredRules();
            automatonService.setSequenceValidate(false);
        }
        coveredRules = null;
        automatonService.getAfndRuleService().cleanCoveredRules();
        automatonService.setSequenceValidate(false);
    }

}
