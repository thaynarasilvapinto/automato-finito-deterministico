package com.afnd.view;


import com.afnd.data.AFNDAutomaton;
import com.afnd.data.AFNDRule;
import com.afnd.service.AFNDService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AFNDStepView extends JFrame{

    private final JPanel panel = new JPanel();
    private final JButton beforeButton = new JButton("<<<");
    private final JButton nextButton = new JButton(">>>");
    private final JButton finishButton = new JButton("VALIDAR");

    private final AFNDAutomaton automaton;
    private final AFNDService automatonService;

    String sequence;
    List<AFNDRule> coveredRules;
    int sequenceIndex = 0;
    boolean validSequenceFlag;

    public AFNDStepView(AFNDAutomaton automaton, String sequence, int x, int y) {

        this.sequence = sequence;
        this.automatonService = new AFNDService();
        this.automaton = automaton;

        processSequence();

        setLocation(x, y);
        setVisible(true);
        setResizable(false);
        setSize(400, 300);
        setLayout(null);

        JLabel text = new JLabel("AUTÔMATO FINITO NÃO DETERMINÍSTICO");
        text.setBounds(70, 10, 280, 30);
        add(text);

        panel.setBounds(20, 50, 360, 150);
        panel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 128)));
        panel.setBackground(new Color(255, 255, 255));
        JPanel aux = new JPanel();
        aux.setVisible(false);
        panel.add(aux);
        add(panel);

        beforeButton.setBorder(BorderFactory.createEtchedBorder());
        beforeButton.setBounds(20, 220, 60, 20);
        add(beforeButton);

        finishButton.setBorder(BorderFactory.createEtchedBorder());
        finishButton.setBounds(160, 210, 80, 40);
        add(finishButton);

        nextButton.setBorder(BorderFactory.createEtchedBorder());
        nextButton.setBounds(320, 220, 60, 20);
        add(nextButton);

        this.beforeButtonAction();
        this.nextButtonAction();
        this.finishButtonAction();
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

    private JPanel resultPanel(List<AFNDRule> rules) {

        JPanel panel = new JPanel();

        JTextField sequenceLabel = new JTextField();
        JLabel currentStateLabel = new JLabel();
        JLabel ruleLabel = new JLabel();
        JLabel acceptableStatesLabel = new JLabel();

        sequenceLabel.setBounds(10, 10, 345, 25);
        sequenceLabel.setBorder(BorderFactory.createLineBorder(Color.white));

        currentStateLabel.setBounds(10, 50, 345, 20);
        acceptableStatesLabel.setBounds(10, 70, 345, 20);

        if (rules.isEmpty()) {
            sequenceLabel.setText("Cadeia vazia");
            sequenceLabel.setFont(new Font(null, Font.PLAIN, 15));
            currentStateLabel.setText("Estado inicial: " + automaton.getInitialState());
        } else {
            AFNDRule rule = rules.get(sequenceIndex);

            currentStateLabel.setText("Estado atual: " + rule.getTargetStates());

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
        panel.add(acceptableStatesLabel);

        panel.setBounds(1, 1, 345, 145);
        panel.setBackground(new Color(255, 255, 255));

        return panel;
    }

}
