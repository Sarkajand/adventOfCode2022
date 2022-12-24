package advent.of.code.day24;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Day24 {

    private int finalY = 0;
    private int finalX = 0;

    private int maxX = 0;
    private int minX = 1;
    private int maxY = 0;
    private int minY = 1;

    private int lcm = 0;

    public void firstPart(String filePath) throws IOException {
        char[][] map = getMap(filePath);
        Set<Position> mapBorder = new HashSet<>();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == '#') {
                    mapBorder.add(new Position(x, y));
                }
            }
        }

        List<Blizzard> blizzards = getBlizzards(filePath);
        Map<Integer, Set<Position>> badPositions = getBadPositions(blizzards, mapBorder);
        Set<Position> possiblePositions = new HashSet<>();
        possiblePositions.add(new Position(1, 0));

        int time = 0;
        while (true) {
            time++;
            Set<Position> bad = badPositions.get((time%lcm)+1);
            Set<Position> newPositions = new HashSet<>();
            for (Position position : possiblePositions) {
                if (position.x-1 >= 0 && !bad.contains(new Position(position.x-1, position.y))) {
                    newPositions.add(new Position(position.x-1, position.y));
                }

                if (position.x+1 <= maxX+1 && !bad.contains(new Position(position.x+1, position.y))) {
                    newPositions.add(new Position(position.x+1, position.y));
                }

                if (position.y-1 >= 0 && !bad.contains(new Position(position.x, position.y-1))) {
                    newPositions.add(new Position(position.x, position.y-1));
                }

                if (position.y+1 <= maxY+1 && !bad.contains(new Position(position.x, position.y+1))) {
                    newPositions.add(new Position(position.x, position.y+1));
                }

                if (!bad.contains(new Position(position.x, position.y))) {
                    newPositions.add(new Position(position.x, position.y));
                }
            }
            possiblePositions = newPositions;

            if (possiblePositions.contains(new Position(finalX, finalY))) {
                System.out.println(time);
                break;
            }

        }
    }

    public void secondPart(String filePath) throws IOException {
        char[][] map = getMap(filePath);
        Set<Position> mapBorder = new HashSet<>();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == '#') {
                    mapBorder.add(new Position(x, y));
                }
            }
        }

        List<Blizzard> blizzards = getBlizzards(filePath);
        Map<Integer, Set<Position>> badPositions = getBadPositions(blizzards, mapBorder);
        Set<Position> possiblePositions = new HashSet<>();
        possiblePositions.add(new Position(1, 0));

        int time = 0;
        boolean end = false;
        boolean start = false;
        boolean secondEnd = false;

        while (!secondEnd) {
            time++;
            Set<Position> bad = badPositions.get((time%lcm)+1);
            Set<Position> newPositions = new HashSet<>();
            for (Position position : possiblePositions) {
                if (position.x-1 >= 0 && !bad.contains(new Position(position.x-1, position.y))) {
                    newPositions.add(new Position(position.x-1, position.y));
                }

                if (position.x+1 <= maxX+1 && !bad.contains(new Position(position.x+1, position.y))) {
                    newPositions.add(new Position(position.x+1, position.y));
                }

                if (position.y-1 >= 0 && !bad.contains(new Position(position.x, position.y-1))) {
                    newPositions.add(new Position(position.x, position.y-1));
                }

                if (position.y+1 <= maxY+1 && !bad.contains(new Position(position.x, position.y+1))) {
                    newPositions.add(new Position(position.x, position.y+1));
                }

                if (!bad.contains(new Position(position.x, position.y))) {
                    newPositions.add(new Position(position.x, position.y));
                }
            }
            possiblePositions = newPositions;

            if (possiblePositions.contains(new Position(finalX, finalY)) && !end) {
                end = true;
                possiblePositions = new HashSet<>();
                possiblePositions.add(new Position(finalX, finalY));
                continue;
            }

            if (possiblePositions.contains(new Position(1, 0)) && end && !start) {
                start = true;
                possiblePositions = new HashSet<>();
                possiblePositions.add(new Position(1, 0));
                continue;
            }

            if (possiblePositions.contains(new Position(finalX, finalY)) && end && start) {
                secondEnd = true;
                System.out.println(time);
            }
        }
    }

    private Map<Integer, Set<Position>> getBadPositions(List<Blizzard> blizzards, Set<Position> mapBorder) {
        BigInteger x = BigInteger.valueOf(maxX);
        BigInteger y = BigInteger.valueOf(maxY);
        BigInteger gcd = x.gcd(y);
        BigInteger absProduct = y.multiply(x).abs();
        lcm = absProduct.divide(gcd).intValue();

        Map<Integer, Set<Position>> badPositions = new HashMap<>();
        for (int time = 1; time <= lcm; time++) {
            Set<Position> positions = blizzards.stream()
                    .map(blizzard -> new Position(blizzard.x, blizzard.y))
                    .collect(Collectors.toSet());
            positions.addAll(mapBorder);
            badPositions.put(time, positions);

            blizzards.forEach(blizzard -> {
                switch (blizzard.direction) {
                    case '>':
                        blizzard.x = blizzard.x + 1 > maxX ? minX : blizzard.x + 1;
                        break;
                    case '<':
                        blizzard.x = blizzard.x - 1 < minX ? maxX : blizzard.x - 1;
                        break;
                    case 'v':
                        blizzard.y = blizzard.y + 1 > maxY ? minY : blizzard.y + 1;
                        break;
                    case '^':
                        blizzard.y = blizzard.y - 1 < minY ? maxY : blizzard.y - 1;
                        break;
                }
            });
        }

        return badPositions;
    }

    private List<Blizzard> getBlizzards(String filePath) throws IOException {
        List<Blizzard> blizzards = new ArrayList<>();
        String file = "src/resources/" + filePath;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            this.finalX = currentLine.length() - 2;
            this.maxX = currentLine.length() - 2;
            int y = 0;
            while (currentLine != null) {
                char[] chars = currentLine.toCharArray();
                for (int x = 0; x < chars.length; x++) {
                    if (chars[x] == '>' || chars[x] == '<' || chars[x] == 'v' || chars[x] == '^') {
                        Blizzard blizzard = new Blizzard();
                        blizzard.x = x;
                        blizzard.y = y;
                        blizzard.direction = chars[x];
                        blizzards.add(blizzard);
                    }
                }
                y++;
                currentLine = reader.readLine();
            }
            this.finalY = y-1;
            this.maxY = y - 2;
        }

        return blizzards;
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

    private class Blizzard {
        int x;
        int y;
        char direction;
    }

    private class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }

            if (!(o instanceof Position)) {
                return false;
            }

            Position p = (Position) o;
            return x == p.x && y == p.y;
        }
    }
}
