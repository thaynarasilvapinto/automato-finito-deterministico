package com.afne.view;

import com.afne.data.AFNDAutomaton;
import com.afne.service.AFNDService;
import com.afne.service.InputFileService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.SwingConstants.CENTER;

public class InitialView extends JFrame implements ActionListener {

    InputFileService inputFileService = new InputFileService();
    JLabel titleLabel = new JLabel("Autômato Finito NÃO Determinístico");
    JButton openFileButton = new JButton("Arquivo...");
    JButton aboutButton = new JButton("Sobre...");

    AFNDService automatonService;

    public InitialView(){
        this.automatonService = new AFNDService();
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
                AFNDAutomaton automaton = inputFileService.parseAutomaton();
                automatonService.validateAutomaton(automaton);
                new AFNDStepView(automaton);
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
                            "       {\"estadoPartida\":\"qx\", \"simbolo\":\"1\", \"estadoDestino\":\"q1\"},\n" +
                            "       ...,\n" +
                            "       {\"estadoPartida\":\"qy\", \"simbolo\":\"0\", \"estadoDestino\":\"q0\"}\n" +
                            "   ]\n" +
                            "}\n\n" +
                            "Posteriormente, basta adicionar as cadeias que deseja testar, tendo a possibilidade de" +
                            " checar os passos\nque o automato seguiu e tambem checar se ela pertence ou nao a linguagem " +
                            "representada por ele.\n\n",
                    "Sobre o programa", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
