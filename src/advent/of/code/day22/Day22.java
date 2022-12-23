package advent.of.code.day22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day22 {

    public void firstPart(String filePath) throws IOException {
        char[][] map = getMap(filePath);
        List<String> instructions = getInstructions(filePath);
        int px = 0;
        int py = 0;
        int direction = 0;

        for (int x = 0; x < map[0].length; x++) {
            if (map[0][x] == '.') {
                px = x;
                break;
            }
        }

        Map<Integer, Integer> yMin = new HashMap<>();
        Map<Integer, Integer> yMax = new HashMap<>();
        Map<Integer, Integer> xMin = new HashMap<>();
        Map<Integer, Integer> xMax = new HashMap<>();

        for (int y = 0; y < map.length; y++) {
            int xmin = Integer.MAX_VALUE;
            int xmax = Integer.MIN_VALUE;
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == '.' || map[y][x] == '#') {
                    xmin = Math.min(xmin, x);
                    xmax = Math.max(xmax, x);
                }
            }
            xMin.put(y, xmin);
            xMax.put(y, xmax);
        }

        for (int x = 0; x < map[0].length; x++) {
            int ymin = Integer.MAX_VALUE;
            int ymax = Integer.MIN_VALUE;
            for (int y = 0; y < map.length; y++) {
                if (map[y].length <= x) {
                    break;
                }
                if (map[y][x] == '.' || map[y][x] == '#') {
                    ymin = Math.min(ymin, y);
                    ymax = Math.max(ymax, y);
                }
            }
            yMin.put(x, ymin);
            yMax.put(x, ymax);
        }

        for (String instruction : instructions) {
            if (instruction.equals("R")) {
                direction++;
                if (direction == 4) {
                    direction = 0;
                }
            } else if (instruction.equals("L")) {
                direction--;
                if (direction == -1) {
                    direction = 3;
                }
            } else {
                int steps = Integer.parseInt(instruction);
                for (int step = 0; step < steps; step++) {
                    int x = 0;
                    int y = 0;
                    switch (direction) {
                        case 0:
                            x = px + 1;
                            y = py;
                            break;
                        case 1:
                            x = px;
                            y = py + 1;
                            break;
                        case 2:
                            x = px - 1;
                            y = py;
                            break;
                        case 3:
                            x = px;
                            y = py - 1;
                            break;
                    }

                    if (px != x && x > xMax.get(y)) {
                        x = xMin.get(y);
                    }
                    if (px != x && x < xMin.get(y)) {
                        x = xMax.get(y);
                    }
                    if (py != y && y > yMax.get(x)) {
                        y = yMin.get(x);
                    }
                    if (py != y && y < yMin.get(x)) {
                        y = yMax.get(x);
                    }

                    if (map[y][x] == '.') {
                        px = x;
                        py = y;
                    } else {
                        break;
                    }
                }
            }
        }
        System.out.println("row: " + (py + 1));
        System.out.println("column: " + (px + 1));
        System.out.println("direction: " + direction);
        long result = 1000L * (py + 1) + 4L * (px + 1) + direction;
        System.out.println("result: " + result);
    }

    public void secondPart(String filePath) throws IOException {
        char[][] map = getMap(filePath);
        List<String> instructions = getInstructions(filePath);
        int px = 0;
        int py = 0;
        int direction = 0;
        int sideNumber = 1;

        int x1 = 50;
        int y1 = 0;
        int x2 = 100;
        int y2 = 0;
        int x3 = 50;
        int y3 = 50;
        int x4 = 0;
        int y4 = 100;
        int x5 = 50;
        int y5 = 100;
        int x6 = 0;
        int y6 = 150;

        Map<Integer, char[][]> sides = new HashMap<>();
        for (int i = 1; i < 7; i++) {
            char[][] side = new char[50][50];
            int x = 0, y = 0;
            switch (i) {
                case 1:
                    x = x1;
                    y = y1;
                    break;
                case 2:
                    x = x2;
                    y = y2;
                    break;
                case 3:
                    x = x3;
                    y = y3;
                    break;
                case 4:
                    x = x4;
                    y = y4;
                    break;
                case 5:
                    x = x5;
                    y = y5;
                    break;
                case 6:
                    x = x6;
                    y = y6;
                    break;
            }
            for (int ym = y; ym < y + 50; ym++) {
                for (int xm = x; xm < x + 50; xm++) {
                    side[ym%50][xm%50] = map[ym][xm];
                }
            }
            sides.put(i, side);
        }

        for (String instruction : instructions) {
            if (instruction.equals("R")) {
                direction++;
                if (direction == 4) {
                    direction = 0;
                }
            } else if (instruction.equals("L")) {
                direction--;
                if (direction == -1) {
                    direction = 3;
                }
            } else {
                int steps = Integer.parseInt(instruction);
                for (int step = 0; step < steps; step++) {
                    int x = 0;
                    int y = 0;
                    int sideNumber2 = sideNumber;
                    int direction2 = direction;
                    switch (direction) {
                        case 0:
                            x = px + 1;
                            y = py;
                            break;
                        case 1:
                            x = px;
                            y = py + 1;
                            break;
                        case 2:
                            x = px - 1;
                            y = py;
                            break;
                        case 3:
                            x = px;
                            y = py - 1;
                            break;
                    }

                    if (x > 49) {
                        switch (sideNumber) {
                            case 1:
                                sideNumber2 = 2;
                                x = 0;
                                y = y;
                                direction2 = 0;
                                break;
                            case 2:
                                sideNumber2 = 5;
                                x = 49;
                                y = 49-y;
                                direction2 = 2;
                                break;
                            case 3:
                                sideNumber2 = 2;
                                x = y;
                                y = 49;
                                direction2 = 3;
                                break;
                            case 4:
                                sideNumber2 = 5;
                                x = 0;
                                y = y;
                                direction2 = 0;
                                break;
                            case 5:
                                sideNumber2 = 2;
                                x = 49;
                                y = 49-y;
                                direction2 = 2;
                                break;
                            case 6:
                                sideNumber2 = 5;
                                x = y;
                                y = 49;
                                direction2 = 3;
                                break;
                        }
                    }
                    if (x < 0) {
                        switch (sideNumber) {
                            case 1:
                                sideNumber2 = 4;
                                x = 0;
                                y = 49 - y;
                                direction2 = 0;
                                break;
                            case 2:
                                sideNumber2 = 1;
                                x = 49;
                                y = y;
                                direction2 = 2;
                                break;
                            case 3:
                                sideNumber2 = 4;
                                x = y;
                                y = 0;
                                direction2 = 1;
                                break;
                            case 4:
                                sideNumber2 = 1;
                                x = 0;
                                y = 49 - y;
                                direction2 = 0;
                                break;
                            case 5:
                                sideNumber2 = 4;
                                x = 49;
                                y = y;
                                direction2 = 2;
                                break;
                            case 6:
                                sideNumber2 = 1;
                                x = y;
                                y = 0;
                                direction2 = 1;
                                break;
                        }
                    }
                    if (y > 49) {
                        switch (sideNumber) {
                            case 1:
                                sideNumber2 = 3;
                                x = x;
                                y = 0;
                                direction2 = 1;
                                break;
                            case 2:
                                sideNumber2 = 3;
                                y = x;
                                x = 49;
                                direction2 = 2;
                                break;
                            case 3:
                                sideNumber2 = 5;
                                x = x;
                                y = 0;
                                direction2 = 1;
                                break;
                            case 4:
                                sideNumber2 = 6;
                                x = x;
                                y = 0;
                                direction2 = 1;
                                break;
                            case 5:
                                sideNumber2 = 6;
                                y = x;
                                x = 49;
                                direction2 = 2;
                                break;
                            case 6:
                                sideNumber2 = 2;
                                x = x;
                                y = 0;
                                direction2 = 1;
                                break;
                        }
                    }
                    if (y < 0) {
                        switch (sideNumber) {
                            case 1:
                                sideNumber2 = 6;
                                y = x;
                                x = 0;
                                direction2 = 0;
                                break;
                            case 2:
                                sideNumber2 = 6;
                                x = x;
                                y = 49;
                                direction2 = 3;
                                break;
                            case 3:
                                sideNumber2 = 1;
                                x = x;
                                y = 49;
                                direction2 = 3;
                                break;
                            case 4:
                                sideNumber2 = 3;
                                y = x;
                                x = 0;
                                direction2 = 0;
                                break;
                            case 5:
                                sideNumber2 = 3;
                                x = x;
                                y = 49;
                                direction2 = 3;
                                break;
                            case 6:
                                sideNumber2 = 4;
                                x = x;
                                y = 49;
                                direction2 = 3;
                                break;
                        }
                    }


                    char[][] side = sides.get(sideNumber2);

                    if (side[y][x] == '.') {
                        px = x;
                        py = y;
                        sideNumber =sideNumber2;
                        direction = direction2;
                    } else {
                        break;
                    }
                }
            }
        }

        switch (sideNumber) {
            case 1:
                px += x1;
                py += y1;
                break;
            case 2:
                px += x2;
                py += y2;
                break;
            case 3:
                px += x3;
                py += y3;
                break;
            case 4:
                px += x4;
                py += y4;
                break;
            case 5:
                px += x5;
                py += y5;
                break;
            case 6:
                px += x6;
                py += y6;
                break;
        }

        System.out.println("row: " + (py + 1));
        System.out.println("column: " + (px + 1));
        System.out.println("direction: " + direction);
        long result = 1000L * (py + 1) + 4L * (px + 1) + direction;
        System.out.println("result: " + result);
    }

    private char[][] getMap(String filePath) throws IOException {
        String file = "src/resources/" + filePath;
        int mapRows = 0;
        int mapColumns = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            while (currentLine != null && !currentLine.isEmpty()) {
                mapRows++;
                mapColumns = Math.max(currentLine.length(), mapColumns);
                currentLine = reader.readLine();
            }
        }

        char[][] map = new char[mapRows][mapColumns];
        int i = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            while (currentLine != null && !currentLine.isEmpty()) {
                map[i] = currentLine.toCharArray();
                i++;
                currentLine = reader.readLine();
            }
        }

        return map;
    }

    private List<String> getInstructions(String filePath) throws IOException {
        String file = "src/resources/" + filePath;
        String instruction = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            while (currentLine != null) {
                if (currentLine.contains("R")) {
                    instruction = currentLine;
                }
                currentLine = reader.readLine();
            }
        }

        List<String> instructions = new ArrayList<>();

        while (instruction.length() > 0) {
            int ir = instruction.indexOf('R');
            int il = instruction.indexOf('L');
            int i = Math.min(ir, il);

            if (ir == -1 && il == -1) {
                i = instruction.length();
            } else if (ir == -1) {
                i = il;
            } else if (il == -1) {
                i = ir;
            }

            if (i == 0) {
                i = 1;
            }

            instructions.add(instruction.substring(0, i));
            instruction = instruction.substring(i);
        }

        return instructions;
    }
}
