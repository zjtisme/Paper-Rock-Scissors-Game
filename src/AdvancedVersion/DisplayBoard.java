package AdvancedVersion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DisplayBoard extends JFrame{

    private JTextArea text;
    private JScrollPane container;

    public DisplayBoard(String title,  String content) {
        super(title);
        text = new JTextArea();
        text.setPreferredSize(new Dimension(380, 380));
        text.setText(content);
        text.setEnabled(false);
        container = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        container.setBackground(Color.LIGHT_GRAY);
        container.setPreferredSize(new Dimension(400, 400));
        container.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(6,6,6,6) ));


        setContentPane(container);
        pack();
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent evt) {
                dispose();
            }
        });
        setLocation(200,100);
        setVisible(true);
    }
}
