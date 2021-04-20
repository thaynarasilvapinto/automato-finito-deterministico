package com.afnd.view;

import javax.swing.*;
import java.awt.*;


public class AFNDview extends JFrame {

     JFrame frame = new JFrame("AFND x AFD");
     JPanel painelAFD = new JPanel();
     JPanel painelAFND = new JPanel();
     JButton validateButton = new JButton("VALIDAR");
     JTextField plvField = new JTextField();
     JLabel plv = new JLabel("PALAVRA:");
     JLabel afd = new JLabel("AFD");
     JLabel afnd = new JLabel("AFND");


     public AFNDview(){

         frame.setSize(505,380);
         frame.setVisible(true);
         frame.setLayout(null);

         painelAFD.setBounds(10,40,230,220);
         painelAFD.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));
         painelAFD.setBackground(new Color(255,255,255));
         afd.setBounds(120,10,230,30);
         afd.setForeground(Color.blue);


         painelAFND.setBounds(250,40,230,220);
         painelAFND.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));
         painelAFND.setBackground(new Color(255,255,255));
         afnd.setBounds(355,10,220,30);
         afnd.setForeground(Color.blue);

         frame.add(painelAFD);
         frame.add(afd);
         frame.add(afnd);
         frame.add(painelAFND);

         plvField.setBounds(100,285,180,20);
         plvField.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));
         frame.add(plvField);

         plv.setBounds(35,285,60,20);
         frame.add(plv);

         validateButton.setBounds(320,275,140,40);
         validateButton.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));
         frame.add(validateButton);

         setValidateButton();


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

}
