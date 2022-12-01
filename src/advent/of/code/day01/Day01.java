package advent.of.code.day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day01 {

    public void firstPart(String filePath) throws IOException {
        Map<Integer, List<Integer>> calories = readInput(filePath);
        int maxCalories = 0;
        for (Map.Entry<Integer, List<Integer>> elf : calories.entrySet()) {
            int sum = elf.getValue().stream().mapToInt(x -> x).sum();
            maxCalories = Math.max(sum, maxCalories);
        }

        System.out.println(maxCalories);
    }

    public void secondPart(String filePath) throws IOException {
        Map<Integer, List<Integer>> calories = readInput(filePath);
        List<Integer> caloriesSums = new ArrayList<>();

        for (Map.Entry<Integer, List<Integer>> elfe : calories.entrySet()) {
            int sum = elfe.getValue().stream().mapToInt(x -> x).sum();
            caloriesSums.add(sum);
        }

        caloriesSums = caloriesSums.stream().sorted().collect(Collectors.toList());
        Collections.reverse(caloriesSums);

        int sum = caloriesSums.get(0);
        sum = sum + caloriesSums.get(1);
        sum = sum + caloriesSums.get(2);

        System.out.println(sum);
    }

    private Map<Integer, List<Integer>> readInput(String filePath) throws IOException {
        String file = "src/resources/" + filePath;
        Map<Integer, List<Integer>> calories = new HashMap<>();
        int elf = 1;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            while (currentLine != null) {
                if (currentLine.isEmpty()) {
                    elf++;
                } else {
                    if (calories.containsKey(elf)) {
                        calories.get(elf).add(Integer.parseInt(currentLine));
                    } else {
                        List<Integer> newCalories = new ArrayList<>();
                        newCalories.add(Integer.parseInt(currentLine));
                        calories.put(elf, newCalories);
                    }
                }
                currentLine = reader.readLine();
            }
        }

        return calories;
    }
}
