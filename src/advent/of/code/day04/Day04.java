package advent.of.code.day04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day04 {

    public void firstPart(String filePath) throws IOException {
        List<List<Integer>> input = readInput(filePath);
        int i = 0;
        for (List<Integer> row : input) {
            if ((row.get(0) <= row.get(2) && row.get(1) >= row.get(3)) || ((row.get(0) >= row.get(2) && row.get(1) <= row.get(3)))) {
                i++;
            }
        }
        System.out.println(i);
    }

    public void secondPart(String filePath) throws IOException {
        List<List<Integer>> input = readInput(filePath);
        int i = input.size();
        for (List<Integer> row : input) {
            if (row.get(1) < row.get(2) || row.get(0) > row.get(3)) {
                i--;
            }
        }
        System.out.println(i);
    }

    private List<List<Integer>> readInput(String filePath) throws IOException {
        String file = "src/resources/" + filePath;
        List<List<Integer>> input = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            while (currentLine != null) {
                List<Integer> row = new ArrayList<>();
                Arrays.stream(currentLine.split("[-,]")).forEach(x -> row.add(Integer.parseInt(x)));
                input.add(row);
                currentLine = reader.readLine();
            }
        }

        return input;
    }
}
