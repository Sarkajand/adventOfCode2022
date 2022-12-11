package advent.of.code.day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 {

    private final Pattern noopPattern = Pattern.compile("noop");
    private final Pattern addPattern = Pattern.compile("addx (-?)([0-9]+)");

    public void firstPart(String filePath) throws IOException {
        List<String> input = readInput(filePath);
        int x = 1;
        int cycle = 1;
        int cycleSum = 0;

        for (String instruction : input) {

            Matcher matcher = noopPattern.matcher(instruction);
            if (matcher.matches()) {
                cycle++;
                if (cycle == 20 || cycle == 60 || cycle == 100 || cycle == 140 || cycle == 180 || cycle == 220) {
                    cycleSum = cycleSum + (cycle * x);
                }
                continue;
            }

            matcher = addPattern.matcher(instruction);
            if (matcher.matches()) {
                cycle++;
                if (cycle == 20 || cycle == 60 || cycle == 100 || cycle == 140 || cycle == 180 || cycle == 220) {
                    cycleSum = cycleSum + (cycle * x);
                }
                int v = Integer.parseInt(matcher.group(2));
                v = matcher.group(1).length() == 1 ? v * -1 : v;
                x += v;
                cycle++;
                if (cycle == 20 || cycle == 60 || cycle == 100 || cycle == 140 || cycle == 180 || cycle == 220) {
                    cycleSum = cycleSum + (cycle * x);
                }
            }
        }
        System.out.println(cycleSum);
    }

    public void secondPart(String filePath) throws IOException {
        List<String> input = readInput(filePath);
        int x = 1;
        int cycle = 1;
        char[][] image = new char[7][40];
        draw(image, cycle, x);

        for (String instruction : input) {
            Matcher matcher = noopPattern.matcher(instruction);
            if (matcher.matches()) {
                cycle++;
                draw(image, cycle, x);
                continue;
            }

            matcher = addPattern.matcher(instruction);
            if (matcher.matches()) {
                cycle++;
                draw(image, cycle, x);

                int v = Integer.parseInt(matcher.group(2));
                v = matcher.group(1).length() == 1 ? v * -1 : v;
                x += v;
                cycle++;
                draw(image, cycle, x);
            }
        }

        printImage(image);
    }

    private void draw(char[][] image, int cycle, int x) {
        int cycleX = (cycle - 1) % 40;
        int cycleY = (cycle - 1) / 40;
        char c = Math.abs(cycleX - (x % 40)) > 1 ? '.' : '#';
        image[cycleY][cycleX] = c;
    }

    private void printImage(char[][] image) {
        for (int i = 0; i < 6; i++) {
            System.out.println(image[i]);
        }
        System.out.println();
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
}
