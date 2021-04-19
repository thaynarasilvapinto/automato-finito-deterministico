package com.afnd.view;

import com.afnd.data.AFDAutomaton;
import com.afnd.data.AFNDAutomaton;
import com.afnd.service.AFNDConverter;
import com.afnd.service.AFNDService;
import com.afnd.service.InputFileService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.SwingConstants.CENTER;

public class InitialView extends JFrame implements ActionListener {

    InputFileService inputFileService = new InputFileService();
    JLabel titleLabel = new JLabel("Autômato Finito Não Determinístico");
    JButton openFileButton = new JButton("Arquivo...");
    JButton aboutButton = new JButton("Sobre...");

    AFNDService afndService = new AFNDService();

    public InitialView(){
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
        setSize(450,200);
    }

    private void setupTitle() {
        titleLabel.setHorizontalAlignment(CENTER);
        titleLabel.setFont(new Font(null, Font.BOLD, 20));
        titleLabel.setBounds(0, 20, 450, 20);
        add(titleLabel);
    }

    private void setupFileButton() {
        openFileButton.setBounds(240,70 ,120,50);
        openFileButton.addActionListener(this);
        add(openFileButton);
    }

    private void setupAboutButton() {
        aboutButton.setBounds(90,70 ,120,50);
        aboutButton.addActionListener(this);
        add(aboutButton);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == openFileButton) {
            try {
                AFNDAutomaton afndAutomaton = inputFileService.parseAutomaton();
                afndService.validateAutomaton(afndAutomaton);
                AFDAutomaton afdAutomaton = new AFNDConverter(afndAutomaton).mapToAFD();
                System.out.println(afdAutomaton);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else if (event.getSource() == aboutButton) {
            JOptionPane.showMessageDialog(this, "\n\nO programa tem a finalidade de simular e comparar o " +
                            "funcionamento de Automatos Finitos \nNão Deterministicos (AFND) e Automatos Finistos Deterministicos (AFD) " +
                            "que representem\n a mesma linguagem de forma genérica.\nPara tal, deve-se inserir a quintupla que define o AFND e " +
                            "que contenha os campos como no exemplo:\n\n" +
                            "{\n" +
                            "   \"estados\": [\"q0\", ..., \"qn\"],\n" +
                            "   \"alfabeto\": \"01...\",\n" +
                            "   \"estadoInicial\": \"qx\",\n" +
                            "   \"estadosFinais\": [\"qx\", \"qy\"],\n" +
                            "   \"regras\": [\n" +
                            "       {\"estadoPartida\":\"qx\", \"simbolo\":\"1\", \"estadosDestino\": [\"q0\"]\"},\n" +
                            "       ...,\n" +
                            "       {\"estadoPartida\":\"qy\", \"simbolo\":\"0\", \"estadosDestino\": [\"q2\", \"qx\"]}\n" +
                            "   ]\n" +
                            "}\n\n" +
                            "Posteriormente, basta adicionar as cadeias que deseja testar, tendo a possibilidade de" +
                            " checar os passos\nque os automatos seguiram e tambem checar se ela pertence ou nao a linguagem " +
                            "representada por eles.\n\n",
                    "Sobre o programa", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
