package AdvancedVersion;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable{
    private String name;
    private String choice;
    private int score;
    private String res;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Player(String name) {
        this.name = name;
        this.choice = "";
        this.score = 0;
        this.res = "";

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res, String opponentName, String opponentChoice) {
        this.res = res;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String currentTimestamp = dtf.format(now);

        String cur = currentTimestamp + " " + res + " : " + name + "-" + choice + " " + opponentName + "-" + opponentChoice;
        if(!opponentName.equals("computer")) {
            if (res.equals("Tie")) {
                score = 5;
            } else if (res.equals("Win")) {
                score = 10;
            } else {
                score = 0;
            }
        }

        FileHandler.writeHistories(cur, this.name);
        if(score > 0)
            FileHandler.writeScores(score, this.name);
    }

    public String displayRecords() {
        List<String> histories = FileHandler.readHistories(name);
        if(histories == null || histories.size() == 0)
            System.out.println("No any available histories");

        StringBuilder sb = new StringBuilder();
        sb.append("History Board\n==========\n");
        for(String h: histories) {
            sb.append(h + "\n");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
       if(obj == this)
           return true;

       if(!(obj instanceof Player))
           return false;

       Player p = (Player) obj;
       return this.name.equals(p.name);
    }
}
