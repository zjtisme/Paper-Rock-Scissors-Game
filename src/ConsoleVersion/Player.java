package ConsoleVersion;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
    private String name;
    private List<String> history;

    public Player(String name) {
        this.name = name;
        history = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<String> getHistory() {
        return history;
    }

    public String playWithComputer(String choice) {
        Random rand = new Random();
        // 0 - rock 1 - paper 2 - scissors
        int computerChoice = rand.nextInt(3);
        if(choice == null || choice.length() == 0) {
            return "Input cannot be empty, please try again\n";
        } else if(choice.equals("quit")) {
            return "Back to menu\n";
        } else if(choice.equals("rock")) {
            if(computerChoice == 0) {
                return formatString("computer", choice, "rock", "Tie");
            } else if(computerChoice == 1) {
                return formatString("computer", choice, "paper", "Loss");
            } else {
                return formatString("computer", choice, "scissors", "Win");
            }
        } else if(choice.equals("paper")) {
            if(computerChoice == 1) {
                return formatString("computer", choice, "paper", "Tie");
            } else if(computerChoice == 2) {
                return formatString("computer", choice, "scissors", "Loss");
            } else {
                return formatString("computer", choice, "rock", "Win");
            }
        } else if(choice.equals("scissors")) {
            if(computerChoice == 2) {
                return formatString("computer", choice, "scissors", "Tie");
            } else if(computerChoice == 0) {
                return formatString("computer", choice, "rock", "Loss");
            } else {
                return formatString("computer", choice, "paper", "Win");
            }
        } else {
            return "Invalid input, please refer to instructions and try again!\n";
        }
    }

    public String playWithPlayer(Player p2, String yourChoice, String p2Choice) {
        if(yourChoice.equals("rock")) {
            if(p2Choice.equals("rock")) {
                return formatString(p2.getName(), yourChoice, p2Choice, "Tie");
            } else if(p2Choice.equals("paper")) {
                return formatString(p2.getName(), yourChoice, p2Choice, "Loss");
            } else if(p2Choice.equals("scissors")) {
                return formatString(p2.getName(), yourChoice, p2Choice, "Win");
            } else {
                return "Invalid input, please refer to instructions and try again!\n";
            }
        } else if(yourChoice.equals("paper")) {
            if(p2Choice.equals("paper")) {
                return formatString(p2.getName(), yourChoice, p2Choice, "Tie");
            } else if(p2Choice.equals("scissors")) {
                return formatString(p2.getName(), yourChoice, p2Choice, "Loss");
            } else if(p2Choice.equals("rock")) {
                return formatString(p2.getName(), yourChoice, p2Choice, "Win");
            } else {
                return "Invalid input, please refer to instructions and try again!\n";
            }
        } else if(yourChoice.equals("scissors")) {
            if(p2Choice.equals("scissors")) {
                return formatString(p2.getName(), yourChoice, p2Choice, "Tie");
            } else if(p2Choice.equals("rock")) {
                return formatString(p2.getName(), yourChoice, p2Choice, "Loss");
            } else if(p2Choice.equals("paper")) {
                return formatString(p2.getName(), yourChoice, p2Choice, "Win");
            } else {
                return "Invalid input, please refer to instructions and try again!\n";
            }
        } else {
            return "Invalid input, please refer to instructions and try again!\n";
        }
    }

    private String formatString(String opponent, String yourChoice, String opponentChoice, String res) {
        addHistory(opponent, yourChoice, opponentChoice, res);
        String formattedString = opponent + " picks: " + opponentChoice + "\n" + "You pick: " + yourChoice + "\n";
        if(res.equals("Win")) {
            return formattedString + "You win!\n";
        } else if(res.equals("Loss")) {
            return formattedString + "You loss!\n";
        } else {
            return formattedString + "Tie!\n";
        }
    }

    private void addHistory(String opponent, String yourChoice, String opponentChoice, String res) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String currentTimestamp = dtf.format(now);

        String record = currentTimestamp + " " + res + ": " +  getName() + "-" + yourChoice + " " + opponent + "-" + opponentChoice + "\n";
        this.history.add(record);
    }

    public void viewHistory() {
        System.out.println("===GAME HISTORY===");
        StringBuilder sb = new StringBuilder();
        for(String record: history) {
            sb.append(record);
        }

        System.out.println(sb.toString());
    }
}
