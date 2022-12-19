package advent.of.code.day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 {


    public void firstPart(String filePath) throws IOException {
        String file = "src/resources/" + filePath;

        String firstLine;
        String secondLine;

        int pairs = 0;
        int badOrder = 0;
        int rightOrder = 0;
        int pairIndex = 0;
        List<Integer> rightIndexes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            while (currentLine != null) {
                pairs++;
                pairIndex++;
                firstLine = currentLine;
                currentLine = reader.readLine();
                secondLine = currentLine;

                int i1 = 0;
                int i2 = 0;

                while (i1 < firstLine.length() && i2 < secondLine.length()) {
                    char c1 = firstLine.charAt(i1);
                    char c2 = secondLine.charAt(i2);

                    if (c1 == '[' && c2 == '[') {
                        i1++;
                        i2++;
                        continue;
                    }

                    if (c1 == ',' && c2 == ',') {
                        i1++;
                        i2++;
                        continue;
                    }

                    if (Character.isDigit(c1) && Character.isDigit(c2)) {
                        StringBuilder sd1 = new StringBuilder();
                        while (Character.isDigit(firstLine.charAt(i1))) {
                            sd1.append(firstLine.charAt(i1));
                            i1++;
                        }

                        StringBuilder sd2 = new StringBuilder();
                        while (Character.isDigit(secondLine.charAt(i2))) {
                            sd2.append(secondLine.charAt(i2));
                            i2++;
                        }

                        int d1 = Integer.parseInt(sd1.toString());
                        int d2 = Integer.parseInt(sd2.toString());
                        if (d1 < d2) {
                            rightOrder++;
                            rightIndexes.add(pairIndex);
                            break;
                        } else if (d1 > d2) {
                            badOrder++;
                            break;
                        } else {
                            continue;
                        }
                    }


                    if (c1 == '[' && Character.isDigit(c2)) {
                        int endOfDigit = i2;
                        while (Character.isDigit(secondLine.charAt(endOfDigit))) {
                            endOfDigit++;
                        }

                        StringBuilder sb = new StringBuilder(secondLine);
                        sb.insert(i2, '[');
                        sb.insert(endOfDigit+1, ']');
                        secondLine = sb.toString();
                        continue;
                    }

                    if (c2 == '[' && Character.isDigit(c1)) {
                        int endOfDigit = i1;
                        while (Character.isDigit(firstLine.charAt(endOfDigit))) {
                            endOfDigit++;
                        }

                        StringBuilder sb = new StringBuilder(firstLine);
                        sb.insert(i1, '[');
                        sb.insert(endOfDigit+1, ']');
                        firstLine = sb.toString();
                        continue;
                    }

                    if (c1 == ']' && c2 != ']') {
                        rightOrder++;
                        rightIndexes.add(pairIndex);
                        break;
                    }

                    if (c1 != ']' && c2 == ']') {
                        badOrder++;
                        break;
                    }

                    if (c1 == ']' && c2 == ']') {
                        i1++;
                        i2++;
                        continue;
                    }
                }

                currentLine = reader.readLine();
                if (currentLine != null && currentLine.isEmpty()) {
                    currentLine = reader.readLine();
                }
            }
        }

        System.out.println("pairs: " + pairs);
        System.out.println("in right order: " + rightOrder);
        System.out.println("in bad order: " + badOrder);
        System.out.println(rightIndexes);
        System.out.println(rightIndexes.stream().mapToInt(x -> x).sum());
    }

    public void secondPart(String filePath) throws IOException {
        List<String> lines = readInput(filePath);
        lines.add("[[2]]");
        lines.add("[[6]]");

        List<String> sortedLines = lines.stream().sorted(this::isSmaller).collect(Collectors.toList());
        int key1 = sortedLines.indexOf("[[2]]") + 1;
        int key2 = sortedLines.indexOf("[[6]]") + 1;
        System.out.println("[[2]]: " + key1);
        System.out.println("[[6]]: " + key2);
        System.out.println("result: " + (key1*key2));
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

        return input.stream().filter(l -> !l.isEmpty()).collect(Collectors.toList());
    }

    private int isSmaller(String firstLine, String secondLine) {
        int i1 = 0;
        int i2 = 0;

        while (i1 < firstLine.length() && i2 < secondLine.length()) {
            char c1 = firstLine.charAt(i1);
            char c2 = secondLine.charAt(i2);

            if (c1 == '[' && c2 == '[') {
                i1++;
                i2++;
                continue;
            }

            if (c1 == ',' && c2 == ',') {
                i1++;
                i2++;
                continue;
            }

            if (Character.isDigit(c1) && Character.isDigit(c2)) {
                StringBuilder sd1 = new StringBuilder();
                while (Character.isDigit(firstLine.charAt(i1))) {
                    sd1.append(firstLine.charAt(i1));
                    i1++;
                }

                StringBuilder sd2 = new StringBuilder();
                while (Character.isDigit(secondLine.charAt(i2))) {
                    sd2.append(secondLine.charAt(i2));
                    i2++;
                }

                int d1 = Integer.parseInt(sd1.toString());
                int d2 = Integer.parseInt(sd2.toString());
                if (d1 < d2) {
                    return -1;
                } else if (d1 > d2) {
                    return 1;
                } else {
                    continue;
                }
            }


            if (c1 == '[' && Character.isDigit(c2)) {
                int endOfDigit = i2;
                while (Character.isDigit(secondLine.charAt(endOfDigit))) {
                    endOfDigit++;
                }

                StringBuilder sb = new StringBuilder(secondLine);
                sb.insert(i2, '[');
                sb.insert(endOfDigit + 1, ']');
                secondLine = sb.toString();
                continue;
            }

            if (c2 == '[' && Character.isDigit(c1)) {
                int endOfDigit = i1;
                while (Character.isDigit(firstLine.charAt(endOfDigit))) {
                    endOfDigit++;
                }

                StringBuilder sb = new StringBuilder(firstLine);
                sb.insert(i1, '[');
                sb.insert(endOfDigit + 1, ']');
                firstLine = sb.toString();
                continue;
            }

            if (c1 == ']' && c2 != ']') {
                return -1;
            }

            if (c1 != ']' && c2 == ']') {
                return 1;
            }

            if (c1 == ']' && c2 == ']') {
                i1++;
                i2++;
                continue;
            }
        }

        throw new RuntimeException("can't compare");
    }
}
