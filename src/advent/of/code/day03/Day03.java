package advent.of.code.day03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day03 {

    public void firstPart(String filePath) throws IOException {
        List<String> backpacks = readInput(filePath);
        List<Character> items = new ArrayList<>();
        for (String backpack : backpacks) {
            String firstPart = backpack.substring(0, backpack.length() / 2);
            String secondPart = backpack.substring(backpack.length() / 2);
            for (char item : firstPart.toCharArray()) {
                if (secondPart.indexOf(item) > -1) {
                    items.add(item);
                    break;
                }
            }
        }

        String priorities = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int sum = items.stream().mapToInt(x -> priorities.indexOf(x) + 1).sum();
        System.out.println(sum);
    }

    public void secondPart(String filePath) throws IOException {
        List<String> backpacks = readInput(filePath);
        String priorities = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        List<Character> items = new ArrayList<>();

        for (int index = 0; index < backpacks.size(); index += 3) {
            String backpack1 = backpacks.get(index);
            String backpack2 = backpacks.get(index + 1);
            String backpack3 = backpacks.get(index + 2);
            for (char item : priorities.toCharArray()) {
                if (backpack1.indexOf(item) > -1 && backpack2.indexOf(item) > -1 && backpack3.indexOf(item) > -1) {
                    items.add(item);
                    break;
                }
            }
        }
        int sum = items.stream().mapToInt(x -> priorities.indexOf(x) + 1).sum();
        System.out.println(sum);
    }

    private List<String> readInput(String filePath) throws IOException {
        String file = "src/resources/" + filePath;
        List<String> backpacks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            while (currentLine != null) {
                backpacks.add(currentLine);
                currentLine = reader.readLine();
            }
        }

        return backpacks;
    }
}
