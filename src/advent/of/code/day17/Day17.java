package advent.of.code.day17;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day17 {

    public void secondPart(String filePath) throws IOException {
        String input = readInput(filePath);
        char[][] image = new char[20000][7];
        Arrays.fill(image[0], '#');

        int rockNumber = 1;
        int inputIndex = 0;
        int rockFallen = 0;

        int highestRow = 0;
        int topOfRock;

        int rockFallenBeforeRepetition = -1;
        int scoreBeforeRepetition = -1;
        int repetitionRockFallen = -1;
        int repetitionScore = -1;
        Set<String> startingPositions = new HashSet<>();
        String firstRepetition = "";
        long remainingRocks = -1;
        long remainingScore = 0;
        long x = 0;

        while (rockFallen < 10000) {
            rockFallen++;
            boolean rockIsFalling = true;
            topOfRock = addRock(image, rockNumber, highestRow);
            int rockSteps = 0;

            if (x == remainingRocks) {
                remainingScore = highestRow - scoreBeforeRepetition - repetitionScore;
            }
            x++;

            while (rockIsFalling) {
                char push = input.charAt(inputIndex);

                if (inputIndex == 0) {
                    String startingPosition = String.format("%d_%d", rockNumber, rockSteps);
                    boolean canAdd = startingPositions.add(startingPosition);
                    if (!canAdd && rockFallenBeforeRepetition == -1) {
                        rockFallenBeforeRepetition = rockFallen - 1;
                        scoreBeforeRepetition = highestRow;
                        firstRepetition = startingPosition;
                    } else if (startingPosition.equals(firstRepetition) && repetitionRockFallen == -1) {
                        repetitionRockFallen = rockFallen - rockFallenBeforeRepetition - 1;
                        repetitionScore = highestRow - scoreBeforeRepetition;
                        remainingRocks = (1000000000000L - rockFallenBeforeRepetition) % repetitionRockFallen;
                        x = 1;
                    }
                }

                if (push == '<' && canPushLeft(image, topOfRock)) {
                    for (int i = Math.max(topOfRock - 5, 0); i < topOfRock; i++) {
                        for (int j = 0; j < 6; j++) {
                            if (image[i][j+1] == '@') {
                                image[i][j] = '@';
                                image[i][j+1] = '.';
                            }
                        }
                    }
                } else if (push == '>' && canPushRight(image, topOfRock)) {
                    for (int i = Math.max(topOfRock - 5, 0); i < topOfRock; i++) {
                        for (int j = 6; j > 0; j--) {
                            if (image[i][j-1] == '@') {
                                image[i][j] = '@';
                                image[i][j-1] = '.';
                            }
                        }
                    }
                }

                if (canFall(image, topOfRock)) {
                    for (int i = Math.max(topOfRock - 5, 0); i < topOfRock; i++) {
                        for (int j = 0; j < 7; j++) {
                            if (image[i+1][j] == '@') {
                                image[i][j] = '@';
                                image[i+1][j] = '.';
                            }
                        }
                    }
                    topOfRock -= 1;
                } else {
                    rockIsFalling = false;
                    for (int i = Math.max(topOfRock - 5, 0); i < topOfRock; i++) {
                        for (int j = 0; j < 7; j++) {
                            if (image[i][j] == '@') {
                                image[i][j] = '#';
                            }
                        }
                    }
                    highestRow = Math.max(highestRow, topOfRock - 1);
                }
                inputIndex = inputIndex < input.length() - 1 ? inputIndex + 1 : 0;
                rockSteps++;
            }
            rockNumber = rockNumber < 5 ? rockNumber + 1 : 1;
        }

        System.out.println("rockFallenBeforeRepetition: " + rockFallenBeforeRepetition);
        System.out.println("scoreBeforeRepetition: " + scoreBeforeRepetition);
        System.out.println("repetitionRockFallen: " + repetitionRockFallen);
        System.out.println("repetitionScore: " + repetitionScore);
        System.out.println("remainingRocks: " + remainingRocks);
        System.out.println("remainingScore: " + remainingScore);

        long repetitions = (1000000000000L - rockFallenBeforeRepetition) / repetitionRockFallen;
        System.out.println("repetitions: " + repetitions);

        long score = repetitions * repetitionScore;
        System.out.println("repetitions score:  " + score);
        System.out.println("final score: " + (score + scoreBeforeRepetition + remainingScore));
        System.out.println();
    }

    public void firstPart(String filePath) throws IOException {
        String input = readInput(filePath);
        char[][] image = new char[4000][7];
        Arrays.fill(image[0], '#');

        int rockNumber = 1;
        int inputIndex = 0;
        int rockFallen = 0;

        int highestRow = 0;
        int topOfRock;


        while (rockFallen < 2022) {
            rockFallen++;
            boolean rockIsFalling = true;
            topOfRock = addRock(image, rockNumber, highestRow);
            printImage(image, Math.max(0, topOfRock-20), topOfRock);

            while (rockIsFalling) {
//                push rock
                char push = input.charAt(inputIndex);
                inputIndex = inputIndex < input.length() - 1 ? inputIndex + 1 : 0;
                if (push == '<' && canPushLeft(image, topOfRock)) {
                    for (int i = Math.max(topOfRock - 5, 0); i < topOfRock; i++) {
                        for (int j = 0; j < 6; j++) {
                            if (image[i][j+1] == '@') {
                                image[i][j] = '@';
                                image[i][j+1] = '.';
                            }
                        }
                    }
                } else if (push == '>' && canPushRight(image, topOfRock)) {
                    for (int i = Math.max(topOfRock - 5, 0); i < topOfRock; i++) {
                        for (int j = 6; j > 0; j--) {
                            if (image[i][j-1] == '@') {
                                image[i][j] = '@';
                                image[i][j-1] = '.';
                            }
                        }
                    }
                }
//                fall
                if (canFall(image, topOfRock)) {
                    for (int i = Math.max(topOfRock - 5, 0); i < topOfRock; i++) {
                        for (int j = 0; j < 7; j++) {
                            if (image[i+1][j] == '@') {
                                image[i][j] = '@';
                                image[i+1][j] = '.';
                            }
                        }
                    }
                    topOfRock -= 1;
                } else {
                    rockIsFalling = false;
                    for (int i = Math.max(topOfRock - 5, 0); i < topOfRock; i++) {
                        for (int j = 0; j < 7; j++) {
                            if (image[i][j] == '@') {
                                image[i][j] = '#';
                            }
                        }
                    }
                    highestRow = Math.max(highestRow, topOfRock - 1);
                }
            }
            rockNumber = rockNumber < 5 ? rockNumber + 1 : 1;
        }

        printImage(image, highestRow + 2);
        System.out.println(highestRow);
    }

    private int addRock(char[][] image, int rockNumber, int highestRow) {
        int topOfRock;
        for (int i = highestRow + 1; i < highestRow + 8; i++) {
            Arrays.fill(image[i], '.');
        }

        if (rockNumber == 1) {
            image[highestRow + 4][2] = '@';
            image[highestRow + 4][3] = '@';
            image[highestRow + 4][4] = '@';
            image[highestRow + 4][5] = '@';
            topOfRock = highestRow + 5;
        } else if (rockNumber == 2) {
            image[highestRow + 4][3] = '@';
            image[highestRow + 5][2] = '@';
            image[highestRow + 5][3] = '@';
            image[highestRow + 5][4] = '@';
            image[highestRow + 6][3] = '@';
            topOfRock = highestRow + 7;
        } else if (rockNumber == 3) {
            image[highestRow + 4][2] = '@';
            image[highestRow + 4][3] = '@';
            image[highestRow + 4][4] = '@';
            image[highestRow + 5][4] = '@';
            image[highestRow + 6][4] = '@';
            topOfRock = highestRow + 7;
        } else if (rockNumber == 4) {
            image[highestRow + 4][2] = '@';
            image[highestRow + 5][2] = '@';
            image[highestRow + 6][2] = '@';
            image[highestRow + 7][2] = '@';
            topOfRock = highestRow + 8;
        } else {
            image[highestRow + 4][2] = '@';
            image[highestRow + 4][3] = '@';
            image[highestRow + 5][2] = '@';
            image[highestRow + 5][3] = '@';
            topOfRock = highestRow + 6;
        }
        return topOfRock;
    }


    private boolean canPushLeft(char[][] image, int topOfRock) {
        int start = topOfRock - 10;
        for (int i = Math.max(start, 0); i <= topOfRock; i++) {
            if (image[i][0] == '@') {
                return false;
            }
            for (int j = 0; j < 6; j++) {
                if (image[i][j] == '#' && image[i][j + 1] == '@') {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean canPushRight(char[][] image, int topOfRock) {
        int start = topOfRock - 10;
        for (int i = Math.max(start, 0); i <= topOfRock; i++) {
            if (image[i][6] == '@') {
                return false;
            }

            for (int j = 0; j < 6; j++) {
                if (image[i][j] == '@' && image[i][j + 1] == '#') {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean canFall(char[][] image, int topOfRock) {
        for (int i = Math.max(topOfRock - 5, 1); i < topOfRock; i++) {
            for (int j = 0; j < 7; j++) {
                if (image[i][j] == '@' && image[i - 1][j] == '#') {
                    return false;
                }
            }
        }
        return true;
    }

    private void printImage(char[][] image, int rows) {
        printImage(image, 0, rows);
    }

    private void printImage(char[][] image, int startRow, int rows) {
        for (int i = rows; i >= startRow; i--) {
            System.out.println(image[i]);
        }
        System.out.println();
    }

    private String readInput(String filePath) throws IOException {
        String file = "src/resources/" + filePath;
        String input = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            while (currentLine != null) {
                input = currentLine;
                currentLine = reader.readLine();
            }
        }

        return input;
    }
}
