package advent.of.code.day08;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day08 {

    public void firstPart(String filePath) throws IOException {
        int[][] treeMap = readInput(filePath);

        long visible = 0;
        for (int i = 0; i < treeMap.length; i++) {
            for (int j = 0; j < treeMap.length; j++) {
                if (i == 0 || j == 0 || i == treeMap.length - 1 || j == treeMap.length - 1) {
                    visible++;
                } else if (isVisibleFromLeft(treeMap, i, j)) {
                    visible++;
                } else if (isVisibleFromRight(treeMap, i, j)) {
                    visible++;
                } else if (isVisibleFromBottom(treeMap, i, j)) {
                    visible++;
                } else if (isVisibleFromTop(treeMap, i, j)) {
                    visible++;
                }
            }
        }

        System.out.println(visible);
    }

    private boolean isVisibleFromLeft(int[][] treeMap, int i, int j) {
        int tree = treeMap[i][j];
        for (int k = 0; k < j; k++) {
            if (tree <= treeMap[i][k]) {
                return false;
            }
        }
        return true;
    }

    private boolean isVisibleFromRight(int[][] treeMap, int i, int j) {
        int tree = treeMap[i][j];
        for (int k = treeMap.length - 1; k > j; k--) {
            if (tree <= treeMap[i][k]) {
                return false;
            }
        }
        return true;
    }

    private boolean isVisibleFromTop(int[][] treeMap, int i, int j) {
        int tree = treeMap[i][j];
        for (int k = 0; k < i; k++) {
            if (tree <= treeMap[k][j]) {
                return false;
            }
        }
        return true;
    }

    private boolean isVisibleFromBottom(int[][] treeMap, int i, int j) {
        int tree = treeMap[i][j];
        for (int k = treeMap.length - 1; k > i; k--) {
            if (tree <= treeMap[k][j]) {
                return false;
            }
        }
        return true;
    }

    public void secondPart(String filePath) throws IOException {
        int[][] treeMap = readInput(filePath);
        int maxScore = 0;

        for (int i = 0; i < treeMap.length; i++) {
            for (int j = 0; j < treeMap.length; j++) {
                int score;
                if (i == 0 || j == 0 || i == treeMap.length - 1 || j == treeMap.length - 1) {
                    score = 0;
                } else {
                    score = countScore(treeMap, i, j);
                }
                maxScore = Math.max(score, maxScore);
            }
        }
        System.out.println(maxScore);
    }

    private int countScore(int[][] treeMap, int i, int j) {
        int left = visibleToLeft(treeMap, i, j);
        int right = visibleToRight(treeMap, i, j);
        int top = visibleToTop(treeMap, i, j);
        int bottom = visibleToBottom(treeMap, i, j);

        return left * right * top * bottom;
    }

    private int visibleToLeft(int[][] treeMap, int i, int j) {
        int tree = treeMap[i][j];
        int visible = 1;
        int k = j-1;
        while (tree > treeMap[i][k] && k > 0) {
            visible++;
            k--;
        }
        return visible;
    }

    private int visibleToRight(int[][] treeMap, int i, int j) {
        int tree = treeMap[i][j];
        int visible = 1;
        int k = j+1;
        while (tree > treeMap[i][k] && k < treeMap.length-1) {
            visible++;
            k++;
        }
        return visible;
    }

    private int visibleToTop(int[][] treeMap, int i, int j) {
        int tree = treeMap[i][j];
        int visible = 1;
        int k = i-1;
        while (tree > treeMap[k][j] && k > 0) {
            visible++;
            k--;
        }
        return visible;
    }

    private int visibleToBottom(int[][] treeMap, int i, int j) {
        int tree = treeMap[i][j];
        int visible = 1;
        int k = i+1;
        while (tree > treeMap[k][j] && k < treeMap.length -1) {
            visible++;
            k++;
        }
        return visible;
    }

    private int[][] readInput(String filePath) throws IOException {
        String file = "src/resources/" + filePath;
        int[][] trees;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            int size = currentLine.length();
            trees = new int[size][size];

            for (int i = 0; i < size; i++) {
                int[] row = currentLine.chars().map(Character::getNumericValue).toArray();
                trees[i] = row;
                currentLine = reader.readLine();
            }
        }

        return trees;
    }
}
