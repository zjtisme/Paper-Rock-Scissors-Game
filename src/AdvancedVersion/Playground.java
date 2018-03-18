package AdvancedVersion;


import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
/*
    This is just a playground file that allows me to test some features of Java
*/
public class Playground {

    public static void main(String[] args) {

//        List<String> lines = Arrays.asList("Jintai Zhang", "Hello");
//        Path file = Paths.get("files/test.txt");
//        System.out.println(file.getParent());
//        try {
//            Files.write(file, lines, Charset.forName("UTF-8"));
//        } catch (IOException e) {
//            System.out.println("No such file " + e.getMessage());
//        }
//        FileHandler.writeLoginLogs("jintai");

//        System.out.println(FileHandler.checkFileExist(FileHandler.logFileName));
//
//        List<String> res = FileHandler.readLoginLogs();
//        if(res != null) {
//            for(String s: res) {
//                System.out.println(s);
//            }
//        }

//        FileHandler.deleteLoginLogs();

//        File dir = new File("records");
//
//        File[] myfiles = dir.listFiles(new FilenameFilter() {
//            @Override
//            public boolean accept(File dir, String name) {
//                return name.endsWith(".txt") && name.contains("-scores");
//            }
//        });
//
//        for(File f: myfiles) {
//            System.out.println(f.getName());
//        }

        FileHandler.createRecordsFolder();
    }

}

