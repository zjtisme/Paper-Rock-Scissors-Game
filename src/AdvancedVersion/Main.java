package AdvancedVersion;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;
import common.*;

public class Main {

    private static final int DEFAULT_PORT = 45017;

    public static void main(String[] args) {

        // First, construct a panel that will be placed into a JOptionPane confirm dialog.

        JLabel message = new JLabel("Welcome to Networked RPS!", JLabel.CENTER);
        message.setFont(new Font("Serif", Font.BOLD, 16));

        final JTextField listeningPortInput = new JTextField("" + DEFAULT_PORT, 5);
        final JTextField hostInput = new JTextField(30);
        final JTextField connectPortInput = new JTextField("" + DEFAULT_PORT, 5);
        final JTextField userName = new JTextField(30);
        final JTextField userNameSingle = new JTextField(30);

        final JRadioButton selectServerMode = new JRadioButton("Start server on this computer");
        final JRadioButton selectSingleMode = new JRadioButton("Start a game with computer");
        final JRadioButton selectClientMode = new JRadioButton("Connect to existing game or create a new 1 vs 1 game");

        ButtonGroup group = new ButtonGroup();
        group.add(selectServerMode);
        group.add(selectClientMode);
        group.add(selectSingleMode);
        ActionListener radioListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == selectServerMode) {
                    listeningPortInput.setEnabled(true);
                    hostInput.setEnabled(false);
                    connectPortInput.setEnabled(false);
                    listeningPortInput.setEditable(true);
                    hostInput.setEditable(false);
                    connectPortInput.setEditable(false);
                    userName.setEnabled(false);
                    userName.setEditable(false);
                    userNameSingle.setEnabled(false);
                    userNameSingle.setEditable(false);
                }
                else if(e.getSource() == selectClientMode){
                    listeningPortInput.setEnabled(false);
                    hostInput.setEnabled(true);
                    connectPortInput.setEnabled(true);
                    listeningPortInput.setEditable(false);
                    hostInput.setEditable(true);
                    connectPortInput.setEditable(true);
                    userName.setEditable(true);
                    userName.setEnabled(true);
                    userNameSingle.setEnabled(false);
                    userNameSingle.setEditable(false);
                } else {
                    listeningPortInput.setEnabled(false);
                    hostInput.setEnabled(false);
                    connectPortInput.setEnabled(false);
                    listeningPortInput.setEditable(false);
                    hostInput.setEditable(false);
                    connectPortInput.setEditable(false);
                    userName.setEnabled(false);
                    userName.setEditable(false);
                    userNameSingle.setEnabled(true);
                    userNameSingle.setEditable(true);
                }
            }
        };
        selectServerMode.addActionListener(radioListener);
        selectClientMode.addActionListener(radioListener);
        selectSingleMode.addActionListener(radioListener);
        selectServerMode.setSelected(true);
        hostInput.setEnabled(false);
        connectPortInput.setEnabled(false);
        hostInput.setEditable(false);
        connectPortInput.setEditable(false);
        userName.setEditable(false);
        userName.setEnabled(false);
        userNameSingle.setEnabled(false);
        userNameSingle.setEditable(false);


        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0,1,5,5));
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(6,6,6,6) ));

        inputPanel.add(message);

        JPanel row;

        inputPanel.add(selectServerMode);

        row = new JPanel();
        row.setLayout(new FlowLayout(FlowLayout.LEFT));
        row.add(Box.createHorizontalStrut(40));
        row.add(new JLabel("Listen on port: "));
        row.add(listeningPortInput);
        inputPanel.add(row);

        inputPanel.add(selectSingleMode);

        row = new JPanel();
        row.setLayout(new FlowLayout(FlowLayout.LEFT));
        row.add(Box.createHorizontalStrut(40));
        row.add(new JLabel("Your name: "));
        row.add(userNameSingle);
        inputPanel.add(row);

        inputPanel.add(selectClientMode);

        row = new JPanel();
        row.setLayout(new FlowLayout(FlowLayout.LEFT));
        row.add(Box.createHorizontalStrut(40));
        row.add(new JLabel("Computer: "));
        row.add(hostInput);
        inputPanel.add(row);

        row = new JPanel();
        row.setLayout(new FlowLayout(FlowLayout.LEFT));
        row.add(Box.createHorizontalStrut(40));
        row.add(new JLabel("Your name: "));
        row.add(userName);
        inputPanel.add(row);

        row = new JPanel();
        row.setLayout(new FlowLayout(FlowLayout.LEFT));
        row.add(Box.createHorizontalStrut(40));
        row.add(new JLabel("Port Number: "));
        row.add(connectPortInput);
        inputPanel.add(row);

        // Show the dialog, get the user's response and -- if the user doesn't
        // cancel -- start a game.  If the user chooses to run as the server
        // then a TicTacToeGameHub (server) is created and after that a TicTacToeWindow
        // is created that connects to the server running on  localhost, which was
        // just created.  In that case, the game will wait for a second connection.
        // If the user chooses to connect to an existing server, then only
        // a TicTacToeWindow is created, that will connect to the specified
        // host where the server is running.

        while (true) {  // Repeats until a game is started or the user cancels.

            int action = JOptionPane.showConfirmDialog(null, inputPanel, "Net Game",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (action != JOptionPane.OK_OPTION)
                return;

            if (selectServerMode.isSelected()) {
                int port;
                try {
                    port = Integer.parseInt(listeningPortInput.getText().trim());
                    if (port <= 0)
                        throw new Exception();
                }
                catch (Exception e) {
                    message.setText("Illegal port number!");
                    listeningPortInput.selectAll();
                    listeningPortInput.requestFocus();
                    continue;
                }
                Hub hub;
                try {
                    hub = new RPSGameHub(port);
                }
                catch (Exception e) {
                    message.setText("Error: Can't listen on port " + port);
                    listeningPortInput.selectAll();
                    listeningPortInput.requestFocus();
                    continue;
                }
                break;
            }
            else if(selectClientMode.isSelected()){
                String host;
                String username;
                int port;
                host = hostInput.getText().trim();
                if (host.length() == 0) {
                    message.setText("You must enter a computer name!");
                    hostInput.requestFocus();
                    continue;
                }
                username = userName.getText().toLowerCase().trim();
                if (username.length() == 0) {
                    message.setText("Username is required!");
                    userName.requestFocus();
                    continue;
                }
                try {
                    port = Integer.parseInt(connectPortInput.getText().trim());
                    if (port <= 0)
                        throw new Exception();
                }
                catch (Exception e) {
                    message.setText("Illegal port number!");
                    connectPortInput.selectAll();
                    connectPortInput.requestFocus();
                    continue;
                }
                try {
                    new RPSGameWindow(host,port,username);
                }
                catch (IOException e) {
                    message.setText("Could not connect to specified host and port.");
                    hostInput.selectAll();
                    hostInput.requestFocus();
                    continue;
                }
                break;
            }else {
                String username;
                username = userNameSingle.getText().toLowerCase().trim();
                if (username.length() == 0) {
                    message.setText("Username is required!");
                    userNameSingle.requestFocus();
                    continue;
                }

                new SingleModeWindow(username);
                break;
            }
        }

    }
}

