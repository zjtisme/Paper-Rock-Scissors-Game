package AdvancedVersion;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.IOException;
import common.*;

public class RPSGameWindow extends JFrame{
    private RPSGameState state;

    private JPanel board;

    private JPanel title;
    private JPanel choices;
    private JPanel statusBar;
    private JPanel buttons;

    private JRadioButton rock;
    private JRadioButton paper;
    private JRadioButton scissors;

    private JButton play;
    private JButton history;
    private JButton rank;

    private ActionListener actionListener;

    private ButtonGroup radioGroup;
    private JTextArea status;

    private Player player;
    private int myID;

    private JLabel message;

    private RPSGameClient connection;

    private String lastGameRes = "Your last game result: ";
    private String currentChoice = "";

    private class RPSGameClient extends Client {
        public RPSGameClient(String hubHostName, int hubPort) throws IOException{
            super(hubHostName, hubPort);
        }

        protected void messageReceived(final Object message) {
            if(message instanceof RPSGameState) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        newState((RPSGameState)message);
                    }
                });
            }
        }

        protected void serverShutdown(String message) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JOptionPane.showMessageDialog(RPSGameWindow.this, "Your opponent has disconnected.\nThe game is ended.");
                    System.exit(0);
                }
            });
        }

    }


    public RPSGameWindow(String hostName, int serverPortNumber, String userName) throws IOException {
        super("Net Rock-Paper-Scissors");
        connection = new RPSGameClient(hostName, serverPortNumber);
        myID = connection.getID();
        board = new JPanel();
        player = new Player(userName);
        message = new JLabel("Welcome to Rock-Paper-scissors " + userName, JLabel.CENTER);
        message.setFont(new Font("Serif", Font.BOLD, 28));
        board.setBackground(Color.WHITE);
        board.setPreferredSize(new Dimension(600, 600));
        board.setLayout(new GridLayout(0,1,5,5));
        board.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(6,6,6,6) ));

        message.setOpaque(true);

        title = new JPanel();
        title.setLayout(new BorderLayout(2,2));
        title.add(message, BorderLayout.CENTER);

        choices = new JPanel();
        choices.setLayout(new GridLayout(1, 3, 10,2));
        rock = new JRadioButton("rock");
        paper = new JRadioButton("paper");
        scissors = new JRadioButton("scissors");

        radioGroup = new ButtonGroup();
        radioGroup.add(rock);
        radioGroup.add(paper);
        radioGroup.add(scissors);

        choices.add(rock);
        choices.add(paper);
        choices.add(scissors);

        status = new JTextArea();
        status.setEnabled(false);
        status.setText("Hello!");
        statusBar = new JPanel();
        statusBar.setLayout(new BorderLayout(2,2));
        statusBar.setPreferredSize(new Dimension(580, 150));
        statusBar.add(status, BorderLayout.CENTER);

        buttons = new JPanel();
        buttons.setLayout(new BorderLayout(2,2));
        play = new JButton("Play");
        play.setPreferredSize(new Dimension(192, 60));
        play.setBackground(Color.GREEN);
        play.setOpaque(true);
        play.setBorderPainted(false);
        history = new JButton("History");
        history.setPreferredSize(new Dimension(192, 60));
        history.setBackground(Color.YELLOW);
        history.setOpaque(true);
        history.setBorderPainted(false);
        rank = new JButton("Rank");
        rank.setPreferredSize(new Dimension(192, 60));
        rank.setBackground(Color.RED);
        rank.setOpaque(true);
        rank.setBorderPainted(false);

        actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == play) {
                    startPlay();
                } else if(e.getSource() == history) {
                    startShowHistory();
                } else {
                    startShowRanks();
                }
            }
        };

        play.addActionListener(actionListener);
        history.addActionListener(actionListener);
        rank.addActionListener(actionListener);

        buttons.add(play, BorderLayout.WEST);
        buttons.add(history);
        buttons.add(rank, BorderLayout.EAST);

        board.add(title);
        board.add(choices);
        board.add(statusBar);
        board.add(buttons);

        setContentPane(board);
        pack();
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            // When the user clicks the window's close box, this listener will
            // send a disconnect message to the Hub and will end the program.
            // The other player will then be notified that this player has disconnected.
            public void windowClosing(WindowEvent evt) {
                dispose();
                connection.disconnect();  // Send a disconnect message to the hub.
                try {
                    Thread.sleep(333); // Wait one-half second to allow the message to be sent.
                }
                catch (InterruptedException e) {
                }
                System.exit(0);
            }
        });
        setLocation(200,100);
        setVisible(true);
    }

    private void startPlay() {
        if(!rock.isSelected() && !paper.isSelected() && !scissors.isSelected()) {
            status.setText("Please pick one choice before clicking play");
            return;
        }
        Object[] items = new Object[2];
        items[0] = player;
        if(rock.isSelected()) {
            items[1] = "rock";
            currentChoice = "ROCK";
        } else if(paper.isSelected()) {
            items[1] = "paper";
            currentChoice = "PAPER";
        } else {
            items[1] = "scissors";
            currentChoice = "SCISSORS";
        }
        connection.send(items);
    }

    private void startShowHistory() {
        String history = FileHandler.displayHistories(player.getName());
        new DisplayBoard("History Board for " + player.getName(), history);
    }

    private void startShowRanks() {
        String ranks = FileHandler.displayRanks();
        new DisplayBoard("Rank Board", ranks);
    }

    private void newState(RPSGameState state) {
        if(state.playerDisconnected) {
            System.exit(0);
        }
        this.state = state;
        if(state.player1 == null || state.player2 == null) {
            if(currentChoice.length() == 0)
                status.setText("Please play a game!");
            else
                status.setText("Your current choice is: "+ currentChoice + ".\nPlease wait for another player...");
            return;
        }


        if(state.gameFinished) {
            Player opponent = state.findOpponent(player);
            if(opponent.getRes().equals("Tie")) {
                lastGameRes = "Tie! \nyour choice: " + state.findOpponent(opponent).getChoice() + "\n" + opponent.getName() + " choice: " + opponent.getChoice();
            } else if(opponent.getRes().equals("Win")) {
                lastGameRes = "Loss! \nyour choice: " + state.findOpponent(opponent).getChoice() + "\n" + opponent.getName() + " choice: " + opponent.getChoice();
            } else {
                lastGameRes = "Win! \nyour choice: " + state.findOpponent(opponent).getChoice() + "\n" + opponent.getName() + " choice: " + opponent.getChoice();
            }
            currentChoice = "";
            connection.send("newgame");
        } else {
            if(lastGameRes.length() > 0) {
                if(currentChoice.length() > 0) {
                    status.setText("Your last game result:\n" + lastGameRes + "\n========\n" + "Your current choice is: "+ currentChoice + ".\nPlease wait for another player...");
                } else{
                    status.setText("Your last game result:\n" + lastGameRes);
                }
            } else {
                status.setText("Still in process, please wait for another player or make your choice!");
            }
        }
    }
}
