package advent.of.code.day06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day06 {

    public void firstPart(String filePath) throws IOException {
        String file = "src/resources/" + filePath;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            while (currentLine != null) {
                for (int i = 0; i < currentLine.length(); i++) {
                    char a = currentLine.charAt(i);
                    char b = currentLine.charAt(i + 1);
                    char c = currentLine.charAt(i + 2);
                    char d = currentLine.charAt(i + 3);
                    if (a != b && a != c && a != d &&
                            b != c && b != d &&
                            c != d) {
                        System.out.println(i + 4);
                        break;
                    }
                }
                currentLine = reader.readLine();
            }
        }
    }

    public void secondPart(String filePath) throws IOException {
        String file = "src/resources/" + filePath;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            while (currentLine != null) {
                for (int i = 0; i < currentLine.length(); i++) {
                    if (allCharactersAreUnique(currentLine.substring(i, Math.min(i+14, currentLine.length())))) {
                        System.out.println(i+14);
                        break;
                    }
                }
                currentLine = reader.readLine();
            }
        }
    }

    private boolean allCharactersAreUnique(String str) {
        for (int j = 0; j < str.length(); j++) {
            for (int k = j+1; k < str.length(); k++) {
                if (str.charAt(j) == str.charAt(k)) {
                    return false;
                }
            }
        }
        return true;
    }
}
