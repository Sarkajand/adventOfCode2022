package advent.of.code.day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day11 {

    public void firstPart(String filePath) throws IOException {
        List<Monkey> monkeys = readInput(filePath);

        for (int turn = 1; turn < 21; turn++) {
            for (Monkey monkey : monkeys) {
                while (!monkey.items.isEmpty()) {
                    Long item = monkey.items.remove();
                    item = monkey.inspect(item);
                    item = item / 3;
                    int throwTo = monkey.throwTo(item);
                    monkeys.get(throwTo).items.add(item);
                }
            }
        }

        List<Long> inspections = monkeys.stream().map(monkey -> monkey.inspections).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        System.out.println(inspections.get(0) * inspections.get(1));
    }

    public void secondPart(String filePath) throws IOException {
        List<Monkey> monkeys = readInput(filePath);

        long modulo = 1;
        for (Monkey monkey : monkeys) {
            modulo *= monkey.divisibleConst;
        }

        for (int turn = 1; turn < 10001; turn++) {
            for (Monkey monkey : monkeys) {
                while (!monkey.items.isEmpty()) {
                    Long item = monkey.items.remove();
                    item = monkey.inspect(item);
                    int throwTo = monkey.throwTo(item);
                    item = item % modulo;
                    monkeys.get(throwTo).items.add(item);
                }
            }
        }

        List<Long> inspections = monkeys.stream().map(monkey -> monkey.inspections).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        System.out.println(inspections.get(0) * inspections.get(1));
    }

    private List<Monkey> readInput(String filePath) throws IOException {
        String file = "src/resources/" + filePath;
        List<Monkey> monkeys = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            Monkey currentMonkey = new Monkey();
            while (currentLine != null) {
                if (currentLine.matches("Monkey [0-9]+:")) {
                    currentMonkey = new Monkey();
                    monkeys.add(currentMonkey);
                } else if (currentLine.trim().matches("Starting items.+")) {
                    if (currentLine.length()< 18) {
                        currentLine = reader.readLine();
                        continue;
                    }

                    String items = currentLine.substring(18);
                    String[] itemsArray = items.split(", ");
                    for (String item : itemsArray) {
                        currentMonkey.items.add(Long.parseLong(item));
                    }
                } else if (currentLine.trim().matches("Operation: new = old.+")) {
                    currentMonkey.multiplication = currentLine.charAt(23) == '*';
                    String operationConst = currentLine.substring(25);
                    if (operationConst.matches("[0-9]+")) {
                        currentMonkey.inspectionConst = Long.parseLong(operationConst);
                    }
                } else if (currentLine.trim().matches("Test: divisible by.+")) {
                    currentMonkey.divisibleConst = Integer.parseInt(currentLine.substring(21));
                    currentLine = reader.readLine();
                    currentMonkey.divisibleTrue = Integer.parseInt(currentLine.substring(29));
                    currentLine = reader.readLine();
                    currentMonkey.divisibleFalse = Integer.parseInt(currentLine.substring(30));
                }
                currentLine = reader.readLine();
            }
        }

        return monkeys;
    }

    private class Monkey {
        Queue<Long> items = new LinkedList<>();
        Long inspectionConst;
        boolean multiplication;
        int divisibleConst;
        int divisibleTrue;
        int divisibleFalse;

        long inspections = 0;

        public Long inspect(Long item) {
            inspections++;
            Long operationConst = inspectionConst;
            if (operationConst == null) {
                operationConst = item;
            }
            if (multiplication) {
                return item * operationConst;
            } else {
                return item + operationConst;
            }
        }

        public int throwTo(Long item) {
            if (item % divisibleConst == 0) {
                return divisibleTrue;
            } else {
                return divisibleFalse;
            }
        }

    }
}
