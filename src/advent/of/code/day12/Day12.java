package advent.of.code.day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Day12 {

    public void firstPart(String filePath) throws IOException {
        char[][] map = readInput(filePath);

        int sx = 0;
        int sy = 0;
        int ex = 0;
        int ey = 0;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'S') {
                    sx = j;
                    sy = i;
                }
                if (map[i][j] == 'E') {
                    ex = j;
                    ey = i;
                }
            }
        }


        int[][] distancesMap = new int[map.length][map[0].length];
        for (int[] ints : distancesMap) {
            Arrays.fill(ints, -1);
        }
        distancesMap[sy][sx] = 0;

        while (distancesMap[ey][ex] == -1) {
            for (int y = 0; y < map.length; y++) {
                for (int x = 0; x < map[y].length; x++) {
                    if (distancesMap[y][x] > -1) {
                        continue;
                    }
                    if (y > 0 && distancesMap[y - 1][x] > -1 && canGoToPosition(map[y][x], map[y - 1][x])) {
                        distancesMap[y][x] = distancesMap[y - 1][x] + 1;
                    } else if (y < distancesMap.length - 1 && distancesMap[y + 1][x] > -1 && canGoToPosition(map[y][x], map[y + 1][x])) {
                        distancesMap[y][x] = distancesMap[y + 1][x] + 1;
                    } else if (x > 0 && distancesMap[y][x - 1] > -1 && canGoToPosition(map[y][x], map[y][x - 1])) {
                        distancesMap[y][x] = distancesMap[y][x - 1] + 1;
                    } else if (x < distancesMap[y].length - 1 && distancesMap[y][x + 1] > -1 && canGoToPosition(map[y][x], map[y][x + 1])) {
                        distancesMap[y][x] = distancesMap[y][x + 1] + 1;
                    }
                }
            }
        }
        System.out.println(distancesMap[ey][ex]);
    }


    public void secondPart(String filePath) throws IOException {
        char[][] map = readInput(filePath);

        int ex = 0;
        int ey = 0;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'E') {
                    ex = j;
                    ey = i;
                }
            }
        }

        int[][] distancesMap = new int[map.length][map[0].length];
        for (int[] ints : distancesMap) {
            Arrays.fill(ints, -1);
        }
        distancesMap[ey][ex] = 0;
        int minA = Integer.MAX_VALUE;
        int added = -1;

        while (added != 0) {
            added = 0;
            for (int y = 0; y < map.length; y++) {
                for (int x = 0; x < map[y].length; x++) {
                    if (distancesMap[y][x] > -1) {
                        continue;
                    }
                    if (y > 0 && distancesMap[y - 1][x] > -1 && canGoToPosition(map[y - 1][x], map[y][x])) {
                        distancesMap[y][x] = distancesMap[y - 1][x] + 1;
                        added++;
                        if (map[y][x] == 'a') {
                            minA = Math.min(distancesMap[y][x], minA);
                        }
                    } else if (y < distancesMap.length - 1 && distancesMap[y + 1][x] > -1 && canGoToPosition(map[y + 1][x], map[y][x])) {
                        distancesMap[y][x] = distancesMap[y + 1][x] + 1;
                        added++;
                        if (map[y][x] == 'a') {
                            minA = Math.min(distancesMap[y][x], minA);
                        }
                    } else if (x > 0 && distancesMap[y][x - 1] > -1 && canGoToPosition(map[y][x - 1], map[y][x])) {
                        distancesMap[y][x] = distancesMap[y][x - 1] + 1;
                        added++;
                        if (map[y][x] == 'a') {
                            minA = Math.min(distancesMap[y][x], minA);
                        }
                    } else if (x < distancesMap[y].length - 1 && distancesMap[y][x + 1] > -1 && canGoToPosition(map[y][x + 1], map[y][x])) {
                        distancesMap[y][x] = distancesMap[y][x + 1] + 1;
                        added++;
                        if (map[y][x] == 'a') {
                            minA = Math.min(distancesMap[y][x], minA);
                        }
                    }
                }
            }
        }
        System.out.println(minA);
    }

    private boolean canGoToPosition(char cTo, char cFrom) {
        if (cFrom == 'S' && cTo == 'a') {
            return true;
        } else if (cFrom == 'z' && cTo == 'E') {
            return true;
        } else if (Math.abs(cFrom - cTo) < 2) {
            return true;
        } else return cFrom - cTo == 2;
    }

    private char[][] readInput(String filePath) throws IOException {
        String file = "src/resources/" + filePath;
        int xSize = 0;
        int ySize = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            while (currentLine != null) {
                ySize++;
                xSize = Math.max(xSize, currentLine.length());
                currentLine = reader.readLine();
            }
        }

        char[][] map = new char[ySize][xSize];

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            for (int i = 0; i < ySize; i++) {
                char[] row = currentLine.toCharArray();
                map[i] = row;
                currentLine = reader.readLine();
            }
        }

        return map;
    }
}
