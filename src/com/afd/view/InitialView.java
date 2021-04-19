package com.afd.view;

import com.afd.data.Automaton;
import com.afd.repository.RuleRepository;
import com.afd.service.AutomatonService;
import com.afd.service.InputFileService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.SwingConstants.CENTER;

public class InitialView extends JFrame implements ActionListener {

    InputFileService inputFileService = new InputFileService();
    JLabel titleLabel = new JLabel("Autômato Finito Determinístico");
    JButton openFileButton = new JButton("Arquivo...");
    JButton aboutButton = new JButton("Sobre...");

    RuleRepository ruleRepository;
    AutomatonService automatonService;

    public InitialView(RuleRepository ruleRepository){
        this.automatonService = new AutomatonService(ruleRepository);
        this.ruleRepository = ruleRepository;
        setupFrame();
        setupTitle();
        setupFileButton();
        setupAboutButton();
    }

    void setupFrame() {
        setLayout(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,200);
    }

    private void setupTitle() {
        titleLabel.setHorizontalAlignment(CENTER);
        titleLabel.setFont(new Font(null, Font.BOLD, 20));
        titleLabel.setBounds(0, 20, 400, 20);
        add(titleLabel);
    }

    private void setupFileButton() {
        openFileButton.setBounds(220,70 ,120,50);
        openFileButton.addActionListener(this);
        add(openFileButton);
    }

    private void setupAboutButton() {
        aboutButton.setBounds(60,70 ,120,50);
        aboutButton.addActionListener(this);
        add(aboutButton);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == openFileButton) {
            try {
                Automaton automaton = inputFileService.translateAutomaton();
                automatonService.validateAutomaton(automaton);
                new StepView(ruleRepository, automaton);
                dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else if (event.getSource() == aboutButton) {
            JOptionPane.showMessageDialog(this, "\n\nO programa tem a finalidade de simular o " +
                            "funcionamento de um Automato Finito Deterministico de forma generica.\nPara tal, deve-se " +
                            "inserir a quintupla que o define em um arquivo json que contenha os campos como no exemplo:\n\n" +
                            "{\n" +
                            "   \"estados\": [\"q0\", ..., \"qn\"],\n" +
                            "   \"alfabeto\": \"01...\",\n" +
                            "   \"estadoInicial\": \"qx\",\n" +
                            "   \"estadosFinais\": [\"qx\", \"qy\"],\n" +
                            "   \"regras\": [\n" +
                            "       {\"estadoPartida\":\"qx\", \"simbolo\":\"1\", \"estadoDestomp\":\"q1\"},\n" +
                            "       ...,\n" +
                            "       {\"estadoPartida\":\"qy\", \"simbolo\":\"0\", \"estadoDestomp\":\"q0\"}\n" +
                            "   ]\n" +
                            "}\n\n" +
                            "Posteriormente, basta adicionar as cadeias que deseja testar, tendo a possibilidade de" +
                            " checar os passos\nque o automato seguiu e tambem checar se ela pertence ou nao a linguagem " +
                            "representada por ele.\n\n",
                    "Sobre o programa", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
