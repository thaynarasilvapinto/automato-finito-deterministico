package com.afnd.view;

import com.afnd.data.AFDAutomaton;
import com.afnd.data.AFNDAutomaton;

import javax.swing.*;
import java.awt.*;


public class ComparisonView extends JFrame {

    JButton validateButton = new JButton("VALIDAR");
    JButton changeButton = new JButton("TROCAR AUTOMATO");
    JTextField plvField = new JTextField();
    JLabel plv = new JLabel("PALAVRA:");
    JLabel afd = new JLabel("AFD");
    JLabel afnd = new JLabel("AFND");


    public ComparisonView(AFDAutomaton afdAutomaton, AFNDAutomaton afndAutomaton) {
        setTitle("AFD x AFND");
        setSize(800, 580);
        setVisible(true);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        afd.setBounds(180, 10, 230, 30);
        afd.setForeground(Color.blue);
        add(afd);

        afnd.setBounds(580, 10, 220, 30);
        afnd.setForeground(Color.blue);
        add(afnd);

        plvField.setBounds(95, 470, 590, 20);
        plvField.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        add(plvField);

        plv.setBounds(20, 470, 70, 20);
        add(plv);

        validateButton.setBounds(700, 470, 80, 20);
        validateButton.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        add(validateButton);

        changeButton.setBounds(20, 500, 760, 20);
        changeButton.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        add(changeButton);

        JScrollPane painelAFD = new JScrollPane(buildTextArea(afdAutomaton.toString()));
        painelAFD.setBounds(20, 40, 360, 400);
        add(painelAFD);

        JScrollPane painelAFND = new JScrollPane(buildTextArea(afndAutomaton.toString()));
        painelAFND.setBounds(420, 40, 360, 400);
        add(painelAFND);

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

}
