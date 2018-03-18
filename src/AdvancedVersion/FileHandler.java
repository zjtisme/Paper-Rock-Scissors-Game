package AdvancedVersion;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class FileHandler {

    public static final String logFileName = "userLog.txt";
    public static final File RecordsDir = new File("records");

    public static boolean writeLoginLogs(String username) {
        Path file = Paths.get(logFileName);
        List<String> lines = Arrays.asList(username);
        if(checkFileExist(logFileName)) {
            String prevName = readLoginLogs().get(0);
            if(prevName.equals(username))
                return false;
            else
                return true;
        }
        try {
            if(!checkFileExist(logFileName)) {
                Files.createFile(file);
            }
            Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
        return true;
    }

    public static void writeHistories(String record, String userName) {
        createRecordsFolder();
        String relativePos = "records/" + userName + "-history.txt";
        Path file = Paths.get(relativePos);
        List<String> line = Arrays.asList(record);
        try {
            if(!checkFileExist(relativePos)) {
                Files.createFile(file);
            }
            Files.write(file, line, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
        }catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }

    public static List<String> readHistories(String userName) {
        String relativePos = "records/" + userName + "-history.txt";
        Path file = Paths.get(relativePos);
        List<String> allHistories = new ArrayList<>();
        try {
            if(!checkFileExist(relativePos)) {
                return allHistories;
            }

            allHistories = Files.readAllLines(file, Charset.forName("UTF-8"));
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }

        return allHistories;
    }

    public static void writeScores(int score, String userName) {
        createRecordsFolder();
        String relativePos = "records/" + userName + "-scores.txt";
        Path file = Paths.get(relativePos);

        int prevScores = readScores(userName);
        List<String> line = Arrays.asList(String.valueOf(prevScores + score));
        try {
            Files.write(file, line, Charset.forName("UTF-8"));
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }

    public static int readScores(String userName) {
        String relativePos = "records/" + userName + "-scores.txt";
        Path file = Paths.get(relativePos);
        if(!checkFileExist(relativePos)) {
            return 0;
        }
        int curScore = 0;
        try {
            curScore = Integer.parseInt(Files.readAllLines(file, Charset.forName("UTF-8")).get(0));
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }

        return curScore;
    }

    public static boolean checkFileExist(String pathName) {
        File f = new File(pathName);

        return f.exists();
    }

    public static List<String> readLoginLogs() {
        if(!checkFileExist(logFileName))
            return null;

        List<String> allLines = new ArrayList<>();
        Path file = Paths.get(logFileName);
        try{
            allLines = Files.readAllLines(file, Charset.forName("UTF-8"));
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }

        return allLines;
    }

    public static void deleteLoginLogs() {
        if(!checkFileExist(logFileName))
            return;

        Path file = Paths.get(logFileName);

        try {
            Files.delete(file);
        } catch (IOException e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    public static String displayRanks() {

        File[] scoreFiles = RecordsDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt") && name.contains("-scores");
            }
        });

        List<Entry> list = new ArrayList<>();
        for(File file: scoreFiles) {
            String userName = file.getName().substring(0, file.getName().lastIndexOf("-scores"));
            int scores = 0;
            try {
                Path path = Paths.get("records/"+file.getName());
                scores = Integer.parseInt(Files.readAllLines(path, Charset.forName("UTF-8")).get(0));
            } catch (IOException e) {
                System.out.println(e.getStackTrace());
            }
            list.add(new Entry(userName, scores));
        }

        Collections.sort(list, new Comparator<Entry>() {
            @Override
            public int compare(Entry o1, Entry o2) {
                if(o1.scores > o2.scores) {
                    return -1;
                } else if(o1.scores < o2.scores) {
                    return 1;
                } else {
                    return o1.name.compareTo(o2.name);
                }
            }
        });

        StringBuilder sb = new StringBuilder();
        sb.append("Rank Board\n==========\n");
        int index = 1;
        for(Entry entry: list) {
            sb.append(index + ". " + "Username: " + entry.name + "  Scores: " + entry.scores + "\n");
            index++;
        }

        return sb.toString();
    }

    public static String displayHistories(String name) {
        String fileName = "records/" + name + "-history.txt";
        if(!checkFileExist(fileName)) {
            return "No History Currently!";
        }

        Path path = Paths.get(fileName);
        StringBuilder sb = new StringBuilder();
        sb.append("History Board for " + name + "\n============\n");
        try {
            List<String> historis = Files.readAllLines(path, Charset.forName("UTF-8"));
            for(String s: historis) {
                sb.append(s + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }

        return sb.toString();
    }

    public static void createRecordsFolder() {
        if(!checkFileExist("records")) {
            Path dir = Paths.get("records");
            try {
                Files.createDirectories(dir);
            } catch (IOException e) {
                System.out.println(e.getStackTrace());
            }
        }
    }

    private static class Entry {
        String name;
        int scores;

        public Entry(String name, int scores){
            this.name = name;
            this.scores = scores;
        }
    }
}
