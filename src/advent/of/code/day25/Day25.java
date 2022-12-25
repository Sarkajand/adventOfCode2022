package advent.of.code.day25;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day25 {

    public void firstPart(String filePath) throws IOException {
        List<String> input = readInput(filePath);
        long sum = 0;
        for (String number : input) {
            sum = sum + getNumber(number);
            System.out.println(getNumber(number));
        }
        String snafu = getSnafu(sum);
        System.out.println(snafu);
    }

    private String getSnafu(long number) {
        StringBuilder snafu = new StringBuilder();
        while (number != 0) {
            int mod = (int) (number % 5);
            number = (number / 5);
            if (mod >= 3) {
                mod = mod - 5;
                number++;
            }
            snafu.insert(0, getCharacter(mod));
        }
        return snafu.toString();
    }

    private long getNumber(String str) {
        long number = 0;
        for (char c : str.toCharArray()) {
            number = number * 5 + getDigit(c);
        }
        return number;
    }

    private int getDigit(char c) {
        switch (c) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '-':
                return -1;
            case '=':
                return -2;
            default:
                return 5;
        }
    }

    private String getCharacter(int i) {
        switch (i) {
            case 0:
                return "0";
            case 1:
                return "1";
            case 2:
                return "2";
            case -1:
                return "-";
            case -2:
                return "=";
            default:
                return null;
        }
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
