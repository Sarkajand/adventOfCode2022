package advent.of.code.day21;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day21 {
    public void firstPart(String filePath) throws IOException {
        Map<String, String> input = readInput(filePath);
        Map<String, Long> monkeyNumbers = resolveMonkeys(input);
        System.out.println(monkeyNumbers.get("root"));
    }

    public void secondPart(String filePath) throws IOException {
        Map<String, String> input = readInput(filePath);
        Map<String, Long> monkeyNumbers = resolveMonkeys(input);

        input.put("humn", "10");
        Map<String, Long> monkeyNumbers2 = resolveMonkeys(input);

        List<String> humnMonkeys = monkeyNumbers2.entrySet().stream()
                .filter(entry -> !entry.getValue().equals(monkeyNumbers.get(entry.getKey())))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        monkeyNumbers2.entrySet().stream()
                .filter(x -> humnMonkeys.contains(x.getKey()))
                .forEach(x -> x.setValue(null));

        String roota = input.get("root").substring(0, 4);
        String rootb = input.get("root").substring(7, 11);
        Long expectedValue = humnMonkeys.contains(roota) ? monkeyNumbers.get(rootb) : monkeyNumbers.get(roota);
        monkeyNumbers2.put(roota, expectedValue);
        monkeyNumbers2.put(rootb, expectedValue);
        monkeyNumbers2.remove("root");

        Queue<String> unsolvedMonkeys = new LinkedList<>();
        monkeyNumbers2.entrySet().stream()
                .filter(x -> x.getValue() == null)
                .map(Map.Entry::getKey)
                .forEach(unsolvedMonkeys::add);

        Map<String, String> input2 = readInput2(filePath);

        while (!unsolvedMonkeys.isEmpty()) {
            String monkeyName = unsolvedMonkeys.remove();
            String operation = input2.get(monkeyName);
            Long result = getResult(operation, monkeyNumbers2, monkeyName);
            if (result != null) {
                monkeyNumbers2.put(monkeyName, result);
            } else {
                unsolvedMonkeys.add(monkeyName);
            }
        }

        System.out.println(monkeyNumbers2.get("humn"));
    }

    private Map<String, Long> resolveMonkeys(Map<String, String> input) {
        Map<String, Long> monkeyNumbers = new HashMap<>();

        for (Map.Entry<String, String> entry : input.entrySet()) {
            Long monkeyNumber = null;
            if (entry.getValue().matches("-?[0-9]+")) {
                monkeyNumber = Long.parseLong(entry.getValue());
            }
            monkeyNumbers.put(entry.getKey(), monkeyNumber);
        }

        Queue<String> unsolvedMonkeys = new LinkedList<>();
        monkeyNumbers.entrySet().stream()
                .filter(x -> x.getValue() == null)
                .map(Map.Entry::getKey)
                .forEach(unsolvedMonkeys::add);

        while (!unsolvedMonkeys.isEmpty()) {
            String monkeyName = unsolvedMonkeys.remove();
            String operation = input.get(monkeyName);
            Long a = monkeyNumbers.get(operation.substring(0, 4));
            Long b = monkeyNumbers.get(operation.substring(7, 11));
            if (a != null && b != null) {
                monkeyNumbers.put(monkeyName, getResult(a, b, operation.charAt(5)));
            } else {
                unsolvedMonkeys.add(monkeyName);
            }
        }

        return monkeyNumbers;
    }

    private Long getResult(String input, Map<String, Long> monkeyNumbers2, String monkey) {
        Long a = monkeyNumbers2.get(input.substring(0, 4));
        Long b = monkeyNumbers2.get(input.substring(6, 10));
        Long c = monkeyNumbers2.get(input.substring(13, 17));
        char symbol = input.charAt(11);
        char xMonkey = input.substring(6, 10).equals(monkey) ? 'b' :'c';

        if ((a != null && b != null)  || (a != null && c != null)) {

            switch (symbol) {
                case '+':
                    if (xMonkey == 'b') {
                        return a - c;
                    } else {
                        return a - b;
                    }
                case '-':
                    if (xMonkey == 'b') {
                        return a + c;
                    } else {
                        return b - a;
                    }
                case '*':
                    if (xMonkey == 'b') {
                        return a / c;
                    } else {
                        return a / b;
                    }
                case '/':
                    if (xMonkey == 'b') {
                        return a * c;
                    } else {
                        return b / a;
                    }
                default:
                    return null;
            }
        } else return null;
    }

    private Long getResult(Long a, Long b, char symbol) {
        switch (symbol) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
            default:
                return null;
        }
    }

    private Map<String, String> readInput(String filePath) throws IOException {
        String file = "src/resources/" + filePath;
        Map<String, String> input = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            while (currentLine != null) {
                String[] line = currentLine.split(": ");
                input.put(line[0], line[1]);
                currentLine = reader.readLine();
            }
        }
        return input;
    }

    private Map<String, String> readInput2(String filePath) throws IOException {
        String file = "src/resources/" + filePath;
        Map<String, String> input = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            while (currentLine != null) {
                if (currentLine.length() > 10) {
                    input.put(currentLine.substring(6, 10), currentLine);
                    input.put(currentLine.substring(13, 17), currentLine);
                }
                currentLine = reader.readLine();
            }
        }
        return input;
    }

}
