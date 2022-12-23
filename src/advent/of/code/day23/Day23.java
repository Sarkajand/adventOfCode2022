package advent.of.code.day23;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day23 {

    public void firstPart(String filePath) throws IOException {
        char[][] map = getMap(filePath);
        List<Elf> elves = getElves(map);
        Queue<Character> directions = new LinkedList<>();
        directions.add('N');
        directions.add('S');
        directions.add('W');
        directions.add('E');

        for (int round = 1; round < 11; round++) {
            map = padMap(map, elves);
            elvesConsiderMovement(elves, map, directions);
            elvesCantMoveToSamePositions(elves);
            elvesMove(elves, map);

            char direction = directions.remove();
            directions.add(direction);
        }

        map = getSmallestRectangle(map, elves);
        int freeSpace = 0;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == '.') {
                    freeSpace++;
                }
            }
        }

        System.out.println(freeSpace);
    }

    public void secondPart(String filePath) throws IOException {
        char[][] map = getMap(filePath);
        List<Elf> elves = getElves(map);
        Queue<Character> directions = new LinkedList<>();
        directions.add('N');
        directions.add('S');
        directions.add('W');
        directions.add('E');

        int round = 0;
        long elfMoveCount = -1;
        while(elfMoveCount != 0) {
            map = padMap(map, elves);
            elvesConsiderMovement(elves, map, directions);
            elvesCantMoveToSamePositions(elves);
            elfMoveCount = elves.stream().filter(elf -> elf.moveToX != -1).count();
            elvesMove(elves, map);

            char direction = directions.remove();
            directions.add(direction);
            round++;
        }

        System.out.println(round);
    }

    private void elvesCantMoveToSamePositions(List<Elf> elves){
        for (Elf elf : elves) {
            if (elf.moveToX != -1) {
                List<Elf> moveToSamePosition = elves.stream().filter(anotherElf -> elf.moveToX == anotherElf.moveToX && elf.moveToY == anotherElf.moveToY)
                        .collect(Collectors.toList());
                if (moveToSamePosition.size() > 1) {
                    elf.moveToX = -1;
                    elf.moveToY = -1;
                    moveToSamePosition.forEach(anotherElf -> {
                        anotherElf.moveToX = -1;
                        anotherElf.moveToY = -1;
                    });
                }
            }
        }
    }

    private void elvesMove(List<Elf> elves, char[][] map) {
        for (Elf elf : elves) {
            if (elf.moveToX != -1) {
                map[elf.y][elf.x] = '.';
                map[elf.moveToY][elf.moveToX] = '#';
                elf.x= elf.moveToX;
                elf.y = elf.moveToY;
                elf.moveToX = -1;
                elf.moveToY = -1;
            }
        }
    }

    private void elvesConsiderMovement(List<Elf> elves, char[][] map, Queue<Character> directions){
        for (Elf elf : elves) {
            int elvesCount = 0;
            for (int y = elf.y - 1; y <= elf.y + 1; y++) {
                for (int x = elf.x - 1; x <= elf.x + 1; x++) {
                    if (map[y][x] == '#') {
                        elvesCount++;
                    }
                }
            }
            if (elvesCount == 1) {
                continue;
            }

            Queue<Character> elfDirections = new LinkedList<>(directions);
            while (!elfDirections.isEmpty()) {
                char direction = elfDirections.remove();
                if (direction == 'N') {
                    if (map[elf.y - 1][elf.x - 1] == '.' && map[elf.y - 1][elf.x] == '.' && map[elf.y - 1][elf.x + 1] == '.') {
                        elf.moveToX = elf.x;
                        elf.moveToY = elf.y - 1;
                        break;
                    }
                } else if (direction == 'S') {
                    if (map[elf.y + 1][elf.x - 1] == '.' && map[elf.y + 1][elf.x] == '.' && map[elf.y + 1][elf.x + 1] == '.') {
                        elf.moveToX = elf.x;
                        elf.moveToY = elf.y + 1;
                        break;
                    }
                } else if (direction == 'W') {
                    if (map[elf.y - 1][elf.x - 1] == '.' && map[elf.y][elf.x - 1] == '.' && map[elf.y + 1][elf.x - 1] == '.') {
                        elf.moveToX = elf.x - 1;
                        elf.moveToY = elf.y;
                        break;
                    }
                } else if (direction == 'E') {
                    if (map[elf.y - 1][elf.x + 1] == '.' && map[elf.y][elf.x + 1] == '.' && map[elf.y + 1][elf.x + 1] == '.') {
                        elf.moveToX = elf.x + 1;
                        elf.moveToY = elf.y;
                        break;
                    }
                }
            }
        }
    }

    private char[][] getSmallestRectangle(char[][] map, List<Elf> elves) {
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;

        for (Elf elf : elves) {
            minY = Math.min(minY, elf.y);
            maxY = Math.max(maxY, elf.y);
            minX = Math.min(minX, elf.x);
            maxX = Math.max(maxX, elf.x);
        }

        char[][] newMap = new char[maxY - minY + 1][maxX - minX + 1];
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                newMap[y - minY][x - minX] = map[y][x];
            }
        }

        return newMap;
    }


    private char[][] padMap(char[][] map, List<Elf> elves) {
        char[][] paddedMap = new char[map.length + 2][map.length + 2];
        char[] firstRow = new char[map.length + 2];
        Arrays.fill(firstRow, '.');
        paddedMap[0] = firstRow;
        char[] lastRow = new char[map.length + 2];
        Arrays.fill(lastRow, '.');
        paddedMap[map.length + 1] = lastRow;


        for (int i = 1; i < map.length + 1; i++) {
            char[] newRow = new char[map.length + 2];
            Arrays.fill(newRow, '.');
            char[] row = map[i - 1];
            System.arraycopy(row, 0, newRow, 1, map.length);
            paddedMap[i] = newRow;
        }

        for (Elf elf : elves) {
            elf.x += 1;
            elf.y += 1;
        }

        return paddedMap;
    }

    private List<Elf> getElves(char[][] map) {
        List<Elf> elves = new ArrayList<>();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == '#') {
                    Elf elf = new Elf();
                    elf.x = x;
                    elf.y = y;
                    elves.add(elf);
                }
            }
        }
        return elves;
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

    private class Elf {
        int x;
        int y;
        int moveToX = -1;
        int moveToY = -1;
    }
}
