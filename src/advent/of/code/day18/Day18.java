package advent.of.code.day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day18 {

    public void firstPart(String filePath) throws IOException {
        List<Cube> cubes = getCubes(filePath);
        int sum = 0;
        for (Cube cube : cubes) {
            int sides = 6;
            for (Cube nextCube : cubes) {
                if (Math.abs(cube.x - nextCube.x)==1 && cube.y == nextCube.y && cube.z == nextCube.z) {
                    sides--;
                }
                if (Math.abs(cube.y - nextCube.y)==1 && cube.x == nextCube.x && cube.z == nextCube.z) {
                    sides--;
                }
                if (Math.abs(cube.z - nextCube.z)==1 && cube.y == nextCube.y && cube.x == nextCube.x) {
                    sides--;
                }
            }
            sum += sides;
        }

        System.out.println(sum);
    }

    public void secondPart(String filePath) throws IOException {
        List<Cube> cubes = getCubes(filePath);
        int maxX = 0;
        int maxY = 0;
        int maxZ = 0;
        for (Cube cube : cubes) {
            maxX = Math.max(maxX, cube.x);
            maxY = Math.max(maxY, cube.y);
            maxZ = Math.max(maxZ, cube.z);
        }

        char[][][] grid = new char[maxZ+1][maxY+1][maxX+1];
        for (Cube cube : cubes) {
            grid[cube.z][cube.y][cube.x] = 'c';
        }

        Queue<Cube> fill = new LinkedList<>();
        fill.add(new Cube(0,0,0));
        while (!fill.isEmpty()) {
            Cube water = fill.remove();
            grid[water.z][water.y][water.x] = 'w';
            if (water.z > 0 && grid[water.z-1][water.y][water.x] != 'c' && grid[water.z-1][water.y][water.x] != 'w' && !fill.contains(new Cube(water.z-1, water.y, water.x))) {
                fill.add(new Cube(water.z-1, water.y, water.x));
            }
            if (water.z < maxZ && grid[water.z+1][water.y][water.x] != 'c' && grid[water.z+1][water.y][water.x] != 'w' && !fill.contains(new Cube(water.z+1, water.y, water.x))) {
                fill.add(new Cube(water.z+1, water.y, water.x));
            }
            if (water.y > 0 && grid[water.z][water.y-1][water.x] != 'c' && grid[water.z][water.y-1][water.x] != 'w' && !fill.contains(new Cube(water.z, water.y-1, water.x))) {
                fill.add(new Cube(water.z, water.y-1, water.x));
            }
            if (water.y < maxY && grid[water.z][water.y+1][water.x] != 'c' && grid[water.z][water.y+1][water.x] != 'w' && !fill.contains(new Cube(water.z, water.y+1, water.x))) {
                fill.add(new Cube(water.z, water.y+1, water.x));
            }
            if (water.x > 0 && grid[water.z][water.y][water.x-1] != 'c' && grid[water.z][water.y][water.x-1] != 'w' && !fill.contains(new Cube(water.z, water.y, water.x-1))) {
                fill.add(new Cube(water.z, water.y, water.x-1));
            }
            if (water.x < maxX && grid[water.z][water.y][water.x+1] != 'c' && grid[water.z][water.y][water.x+1] != 'w' && !fill.contains(new Cube(water.z, water.y, water.x+1))) {
                fill.add(new Cube(water.z, water.y, water.x+1));
            }
        }

        int sum = 0;
        for (int z = 0; z <= maxZ; z++) {
            for (int y = 0; y <= maxY; y++) {
                for (int x = 0; x <= maxX; x++) {
                    if (grid[z][y][x] == 'c') {
                        if (z == 0 || grid[z-1][y][x] == 'w') {
                            sum++;
                        }
                        if (z == maxZ || grid[z+1][y][x] == 'w') {
                            sum++;
                        }
                        if (y == 0 || grid[z][y-1][x] == 'w') {
                            sum++;
                        }
                        if (y == maxY || grid[z][y+1][x] == 'w') {
                            sum++;
                        }
                        if (x == 0 || grid[z][y][x-1] == 'w') {
                            sum++;
                        }
                        if (x == maxX || grid[z][y][x+1] == 'w') {
                            sum++;
                        }
                    }
                }
            }
        }

        System.out.println(sum);
    }

    private List<Cube> getCubes(String filePath) throws IOException {
        String file = "src/resources/" + filePath;
        List<Cube> input = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            while (currentLine != null) {
                String[] coordinates = currentLine.split(",");
                input.add(new Cube(Integer.parseInt(coordinates[2]), Integer.parseInt(coordinates[1]), Integer.parseInt(coordinates[0])));
                currentLine = reader.readLine();
            }
        }

        return input;
    }

    private class Cube {
        int x;
        int y;
        int z;

        public Cube(int z, int y, int x) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }

            if (!(o instanceof Cube)) {
                return false;
            }

            Cube c = (Cube) o;
            return x == c.x && y == c.y && z == c.z;
        }
    }
}
