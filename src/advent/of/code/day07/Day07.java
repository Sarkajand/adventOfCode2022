package advent.of.code.day07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day07 {

    public void firstPart(String filePath) throws IOException {
        List<String> input = readInput(filePath);
        List<Directory> allDirectories = createDirectories(input);

        Long selectedDirectoriesSum = allDirectories.stream()
                .filter(x -> x.size <= 100000)
                .mapToLong(x -> x.size)
                .sum();
        System.out.println(selectedDirectoriesSum);
    }

    public void secondPart(String filePath) throws IOException {
        List<String> input = readInput(filePath);
        List<Directory> allDirectories = createDirectories(input);

        Directory root = allDirectories.stream().filter(x -> x.parent == null).findFirst().get();
        long unusedSpace = 70000000 - root.size;
        long neededSpace = 30000000 - unusedSpace;

        Long selectedDirectoriesSum = allDirectories.stream()
                .filter(x -> x.size >= neededSpace)
                .mapToLong(x -> x.size)
                .sorted()
                .findFirst().getAsLong();
        System.out.println(selectedDirectoriesSum);
    }

    private List<Directory> createDirectories(List<String> input) {
        Pattern changeDirectoryPattern = Pattern.compile("\\$ cd (.+)");
        Pattern upPattern = Pattern.compile("\\$ cd ..");
        Pattern filePattern = Pattern.compile("([0-9]+) (.+)");
        Pattern directoryPattern = Pattern.compile("dir (.+)");

        List<Directory> allDirectories = new ArrayList<>();
        Directory currentDirectory = null;
        for (String line : input) {
            Matcher matcher = upPattern.matcher(line);
            if (matcher.matches()) {
                currentDirectory = currentDirectory.parent;
                continue;
            }

            matcher = changeDirectoryPattern.matcher(line);
            if (matcher.matches()) {
                if (currentDirectory == null) {
                    Directory directory = new Directory();
                    allDirectories.add(directory);
                    directory.name = matcher.group(1);
                    currentDirectory = directory;
                    continue;
                } else {
                    currentDirectory = currentDirectory.directories.get(matcher.group(1));
                }
            }

            matcher = filePattern.matcher(line);
            if (matcher.matches()) {
                currentDirectory.files.put(matcher.group(2), Long.parseLong(matcher.group(1)));
                continue;
            }

            matcher = directoryPattern.matcher(line);
            if (matcher.matches()) {
                Directory directory = new Directory();
                allDirectories.add(directory);
                directory.name = matcher.group(1);
                directory.parent = currentDirectory;
                currentDirectory.directories.put(matcher.group(1), directory);
                continue;
            }
        }
        allDirectories.stream().filter(x -> x.parent == null).findFirst().get().setSize();
        return allDirectories;
    }

    private List<String> readInput(String filePath) throws IOException {
        String file = "src/resources/" + filePath;
        List<String> input = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            while (currentLine != null) {
                input.add(currentLine);
                currentLine = reader.readLine();
            }
        }

        return input;
    }

    private class Directory {
        String name;
        Directory parent;
        Map<String, Directory> directories = new HashMap<>();
        Map<String, Long> files = new HashMap<>();
        Long size;

        void setSize() {
            long filesSize = files.values().stream().mapToLong(x -> x).sum();
            directories.values().forEach(Directory::setSize);
            size = directories.values().stream().mapToLong(x -> x.size).sum() + filesSize;
        }
    }
}
