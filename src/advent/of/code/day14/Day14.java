package advent.of.code.day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day14 {
    public void firstPart(String filePath) throws IOException {
        List<String> input = readInput(filePath);
        char[][] map = drawMap(input);

        int units = 0;
        boolean unitStopped = true;

        while (unitStopped) {
            units++;
            int ux = 500;
            int uy = 0;
            boolean falling = true;

            while (falling) {
                if (map[uy+1][ux] == '.') {
                    uy++;
                    if (uy == 199) {
                        unitStopped = false;
                        break;
                    }
                } else if (map[uy+1][ux-1] == '.') {
                    uy++;
                    ux--;
                    if (uy == 199) {
                        unitStopped = false;
                        break;
                    }
                } else if (map[uy+1][ux+1] == '.') {
                    uy++;
                    ux++;
                    if (uy == 199) {
                        unitStopped = false;
                        break;
                    }
                } else {
                    map[uy][ux] = 'O';
                    falling = false;
                }
            }
        }

        printMap(map);
        System.out.println(units-1);
    }

    public void secondPart(String filePath) throws IOException {
        List<String> input = readInput(filePath);
        char[][] map = drawMap(input);
        addFloor(map);

        int units = 0;
        boolean unitStopped = true;

        while (unitStopped) {
            units++;
            int ux = 500;
            int uy = 0;
            boolean falling = true;

            while (falling) {
                if (map[uy+1][ux] == '.') {
                    uy++;
                } else if (map[uy+1][ux-1] == '.') {
                    uy++;
                    ux--;
                } else if (map[uy+1][ux+1] == '.') {
                    uy++;
                    ux++;
                } else {
                    map[uy][ux] = 'O';
                    falling = false;
                    if (uy == 0 && ux == 500) {
                        unitStopped = false;
                    }
                }
            }
        }
        printMap(map);
        System.out.println(units);
    }

    private void addFloor(char[][] map) {
        int maxY = 0;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == '#') {
                    maxY = Math.max(maxY, y);
                }
            }
        }

        Arrays.fill(map[maxY + 2], '#');
    }



    private char[][] drawMap(List<String> input) {
        char[][] map = new char[200][1000];
        for (char[] chars : map) {
            Arrays.fill(chars, '.');
        }


        for (String line : input) {
            String[] coordinates = line.split(" -> ");
            for (int i = 0; i < coordinates.length -1; i++) {
                String[] c1 = coordinates[i].split(",");
                String[] c2 = coordinates[i+1].split(",");
                int x1 = Integer.parseInt(c1[0]);
                int y1 = Integer.parseInt(c1[1]);
                int x2 = Integer.parseInt(c2[0]);
                int y2 = Integer.parseInt(c2[1]);

                if (x1 == x2) {
                    for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                        map[y][x1] = '#';
                    }
                }

                if (y1 == y2) {
                    for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
                        map[y1][x] = '#';
                    }
                }
            }
        }

        return map;
    }

    private void printMap(char[][] map) {
        for (char[] chars : map) {
            System.out.println(chars);
        }
        System.out.println();
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
