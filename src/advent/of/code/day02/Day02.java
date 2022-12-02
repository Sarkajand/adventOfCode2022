package advent.of.code.day02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day02 {

    public void firstPart(String filePath) throws IOException {
        String file = "src/resources/" + filePath;
        int score = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            while (currentLine != null) {
                switch (currentLine) {
                    case "A X":
                        score += 4;
                        break;
                    case "B X":
                        score += 1;
                        break;
                    case "C X":
                        score += 7;
                        break;
                    case "A Y":
                        score += 8;
                        break;
                    case "B Y":
                        score += 5;
                        break;
                    case "C Y":
                        score += 2;
                        break;
                    case "A Z":
                        score += 3;
                        break;
                    case "B Z":
                        score += 9;
                        break;
                    case "C Z":
                        score += 6;
                        break;
                }
                currentLine = reader.readLine();
            }
        }
        System.out.println(score);
    }

    public void secondPart(String filePath) throws IOException {
        String file = "src/resources/" + filePath;
        int score = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            while (currentLine != null) {
                switch (currentLine) {
                    case "A X":
                        score += 3;
                        break;
                    case "B X":
                        score += 1;
                        break;
                    case "C X":
                        score += 2;
                        break;
                    case "A Y":
                        score += 4;
                        break;
                    case "B Y":
                        score += 5;
                        break;
                    case "C Y":
                        score += 6;
                        break;
                    case "A Z":
                        score += 8;
                        break;
                    case "B Z":
                        score += 9;
                        break;
                    case "C Z":
                        score += 7;
                        break;
                }
                currentLine = reader.readLine();
            }
        }
        System.out.println(score);
    }
}
