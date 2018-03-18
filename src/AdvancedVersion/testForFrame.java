package AdvancedVersion;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;
import common.*;

public class testForFrame{

    public static void main(String[] args) {
//       JFrame jf = new JFrame("Test");
//       JLabel message = new JLabel("Welcome to RPS!", JLabel.CENTER);
//       message.setFont(new Font("Serif", Font.BOLD, 16));
//
//       JRadioButton rock = new JRadioButton("rock");
//       JRadioButton paper = new JRadioButton("paper");
//       JRadioButton scissors = new JRadioButton("scissors");
//
//       JButton play = new JButton("play");
//       JButton history = new JButton("history");
//       JButton rank = new JButton("rank");
//
//       ButtonGroup group = new ButtonGroup();
//       group.add(rock);
//       group.add(paper);
//       group.add(scissors);
//
//       ActionListener radioListener = new ActionListener() {
//           @Override
//           public void actionPerformed(ActionEvent e) {
//               if(e.getSource() == rock) {
//                   System.out.println("rock");
//               }
//           }
//       };
//       history.addActionListener(radioListener);
//       rock.addActionListener(radioListener);
//       JTextArea info = new JTextArea();
//       info.setEnabled(false);
//       info.setText("Hello");
//        JPanel inputPanel = new JPanel();
//        inputPanel.setPreferredSize(new Dimension(400, 400));
//        inputPanel.setLayout(new GridLayout(0,1,5,5));
//        inputPanel.setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createLineBorder(Color.BLACK, 2),
//                BorderFactory.createEmptyBorder(6,6,6,6) ));
//
//        inputPanel.add(message);
//
//        inputPanel.add(rock);
//        inputPanel.add(paper);
//        inputPanel.add(scissors);
//
//        inputPanel.add(info);
//
//        inputPanel.add(play);
//        inputPanel.add(history);
//        inputPanel.add(rank);
//        jf.setContentPane(inputPanel);
//        jf.pack();
//        jf.setResizable(false);
//        jf.setVisible(true);

        try {
            RPSGameWindow window = new RPSGameWindow("loo", 0, "Jintai");
        } catch (IOException e) {
            System.out.println("error");
        }
    }

}
