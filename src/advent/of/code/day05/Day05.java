package advent.of.code.day05;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day05 {

    private Map<Integer, Stack<Character>> cargo = new HashMap<>();
    private List<String> instructions = new ArrayList<>();
    private final Pattern instructionPattern = Pattern.compile("move ([0-9]+) from ([0-9]+) to ([0-9]+)");

    public void firstPart(String filePath) throws IOException {
        readInput(filePath);
        for (String instruction : instructions) {
            Matcher matcher = instructionPattern.matcher(instruction);
            if (matcher.matches()) {
                int count = Integer.parseInt(matcher.group(1));
                int stackNumberFrom = Integer.parseInt(matcher.group(2));
                int stackNumberTo = Integer.parseInt(matcher.group(3));
                while (count > 0) {
                    char create = cargo.get(stackNumberFrom).pop();
                    cargo.get(stackNumberTo).push(create);
                    count--;
                }
            }
        }

        StringBuilder result = new StringBuilder();
        cargo.values().forEach(stack -> result.append(stack.pop()));
        System.out.println(result);
    }

    public void secondPart(String filePath) throws IOException {
        readInput(filePath);
        for (String instruction : instructions) {
            Matcher matcher = instructionPattern.matcher(instruction);
            if (matcher.matches()) {
                int count = Integer.parseInt(matcher.group(1));
                int stackNumberFrom = Integer.parseInt(matcher.group(2));
                int stackNumberTo = Integer.parseInt(matcher.group(3));

                Stack<Character> moved = new Stack<>();
                while (count > 0) {
                    char create = cargo.get(stackNumberFrom).pop();
                    moved.push(create);
                    count--;
                }

                while (!moved.isEmpty()) {
                    cargo.get(stackNumberTo).push(moved.pop());
                }
            }
        }

        StringBuilder result = new StringBuilder();
        cargo.values().forEach(stack -> result.append(stack.pop()));
        System.out.println(result);
    }

    private void readInput(String filePath) throws IOException {
        String file = "src/resources/" + filePath;
        Map<Integer, Stack<Character>> cargo = new HashMap<>();
        List<String> instructions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            while (currentLine != null) {
                if (currentLine.matches("move [0-9]+ from [0-9]+ to [0-9]+")) {
                    instructions.add(currentLine);
                } else {
                    for (int i = 1; i < currentLine.length(); i += 4) {
                        char create = currentLine.charAt(i);
                        if (create > 65) {
                            int stackNumber = (i / 4) + 1;
                            if (cargo.containsKey(stackNumber)) {
                                cargo.get(stackNumber).push(create);
                            } else {
                                Stack<Character> stack = new Stack<>();
                                stack.push(create);
                                cargo.put(stackNumber, stack);
                            }
                        }
                    }
                }
                currentLine = reader.readLine();
            }
        }
        cargo.values().forEach(Collections::reverse);

        this.cargo = cargo;
        this.instructions = instructions;
    }
}
