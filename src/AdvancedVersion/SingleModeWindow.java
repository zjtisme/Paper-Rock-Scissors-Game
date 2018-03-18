package AdvancedVersion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public class SingleModeWindow extends JFrame{

    private Player player;

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

    private ActionListener actionListener;

    private ButtonGroup radioGroup;
    private JTextArea status;

    private JLabel message;
    private String gameRes = "Game result: \n";

    public SingleModeWindow(String userName) {
        super("SingleMode Rock-Paper-Scissors");
        board = new JPanel();
        player = new Player(userName);
        message = new JLabel("Welcome to Rock-Paper-scissors " + userName, JLabel.CENTER);
        message.setFont(new Font("Serif", Font.BOLD, 18));
        board.setBackground(Color.WHITE);
        board.setPreferredSize(new Dimension(400, 600));
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
        history.setBackground(Color.RED);
        history.setOpaque(true);
        history.setBorderPainted(false);

        actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == play) {
                    startPlay();
                } else {
                    startShowHistory();
                }
            }
        };

        play.addActionListener(actionListener);
        history.addActionListener(actionListener);

        buttons.add(play, BorderLayout.WEST);
        buttons.add(history, BorderLayout.EAST);

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

        Random rand = new Random();
        int computerChoice = rand.nextInt(3); // 0 - rock 1 - paper 2 - scissors

        if(rock.isSelected()) {
            player.setChoice("rock");
            if(computerChoice == 0) {
                player.setRes("Tie", "computer", "rock");
                status.setText(gameRes + "Tie\n" + "Your choice: rock  computer's choice: rock");
            } else if (computerChoice == 1) {
                player.setRes("Loss", "computer", "paper");
                status.setText(gameRes + "Loss\n" + "Your choice: rock  computer's choice: paper");
            } else {
                player.setRes("Win", "computer", "scissors");
                status.setText(gameRes + "Win\n" + "Your choice: rock  computer's choice: scissors");
            }
        } else if(paper.isSelected()) {
            player.setChoice("paper");
            if(computerChoice == 1) {
                player.setRes("Tie", "computer", "paper");
                status.setText(gameRes + "Tie\n" + "Your choice: paper  computer's choice: paper");
            } else if (computerChoice == 2) {
                player.setRes("Loss", "computer", "scissors");
                status.setText(gameRes + "Loss\n" + "Your choice: paper  computer's choice: scissors");
            } else {
                player.setRes("Win", "computer", "rock");
                status.setText(gameRes + "Win\n" + "Your choice: paper  computer's choice: rock");
            }
        } else {
            player.setChoice("scissors");
            if(computerChoice == 2) {
                player.setRes("Tie", "computer", "scissors");
                status.setText(gameRes + "Tie\n" + "Your choice: scissors  computer's choice: scissors");
            } else if (computerChoice == 0) {
                player.setRes("Loss", "computer", "rock");
                status.setText(gameRes + "Loss\n" + "Your choice: scissors  computer's choice: rock");
            } else {
                player.setRes("Win", "computer", "paper");
                status.setText(gameRes + "Win\n" + "Your choice: scissors  computer's choice: paper");
            }
        }
    }

    private void startShowHistory() {
        String histroy = FileHandler.displayHistories(player.getName());
        new DisplayBoard("History Board for " + player.getName(), histroy);
    }
}
