package AdvancedVersion;

import java.io.Serializable;

public class RPSGameState implements Serializable {
    public boolean playerDisconnected;

//    public boolean gameInProgress;

    public boolean gameFinished;

    public Player player1;
    public Player player2;

    public static final int player1ID = 1;
    public static final int player2ID = 2;

    public String player1Choice;
    public String player2Choice;

    public void applyMessage(int sender, Object message) {
        if(!gameFinished && message instanceof Object[]) {
            Object[] items = (Object[]) message;

            if (sender == player1ID) {
                if(player1 == null) {
                    player1 = (Player)items[0];
                }
                player1Choice = (String)items[1];
            } else {
                if(player2 == null) {
                    player2 = (Player)items[0];
                }
                player2Choice = (String)items[1];
            }

            if((player1 != null && player2 != null) && (player1Choice.length() > 0 && player2Choice.length() > 0)) {
                handleRes(player1Choice, player2Choice);
//                gameInProgress = false;
                gameFinished = true;
            }
        } else if(gameFinished && message.equals("newgame")) {
            startVSPlayerGame();
        }
    }

    private void handleRes(String p1c, String p2c) {
        player1.setChoice(p1c);
        player2.setChoice(p2c);
        if(p1c.equals("rock")) {
            if(p2c.equals("rock")) {
                player1.setRes("Tie", player2.getName(), player2.getChoice());
                player2.setRes("Tie", player1.getName(), player1.getChoice());
            } else if (p2c.equals("paper")) {
                player1.setRes("Loss",player2.getName(), player2.getChoice());
                player2.setRes("Win", player1.getName(), player1.getChoice());
            } else {
                player1.setRes("Win", player2.getName(), player2.getChoice());
                player2.setRes("Loss", player1.getName(), player1.getChoice());
            }
        } else if(p1c.equals("paper")) {
            if(p2c.equals("paper")) {
                player1.setRes("Tie", player2.getName(), player2.getChoice());
                player2.setRes("Tie", player1.getName(), player1.getChoice());
            } else if (p2c.equals("scissors")) {
                player1.setRes("Loss", player2.getName(), player2.getChoice());
                player2.setRes("Win", player1.getName(), player1.getChoice());
            } else {
                player1.setRes("Win", player2.getName(), player2.getChoice());
                player2.setRes("Loss", player1.getName(), player1.getChoice());
            }
        } else {
            if(p2c.equals("scissors")) {
                player1.setRes("Tie", player2.getName(), player2.getChoice());
                player2.setRes("Tie", player1.getName(), player1.getChoice());
            } else if (p2c.equals("rock")) {
                player1.setRes("Loss", player2.getName(), player2.getChoice());
                player2.setRes("Win", player1.getName(), player1.getChoice());
            } else {
                player1.setRes("Win", player2.getName(), player2.getChoice());
                player2.setRes("Loss", player1.getName(), player1.getChoice());
            }
        }
    }

    public void startVSPlayerGame() {
        gameFinished = false;
        player1Choice = "";
        player2Choice = "";
    }

    public Player findOpponent(Player cur) {
        if(cur.equals(player1)) {
            return player2;
        } else {
            return player1;
        }
    }

    public String showHistoryOfPlayer(int id) {
        if(id == player1ID) {
            if(player1 == null)
                return "";
            return player1.displayRecords();
        } else {
            if(player2 == null)
                return "";
            return player2.displayRecords();
        }
    }

}
