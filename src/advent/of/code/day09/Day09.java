package advent.of.code.day09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day09 {

    private final Pattern instructionPattern = Pattern.compile("(.) ([0-9]+)");

    public void firstPart(String filePath) throws IOException {
        List<String> instructions = readInput(filePath);

        int hx = 100;
        int hy = 100;
        int tx = 100;
        int ty = 100;

        Set<Position> visitedPositions = new HashSet<>();
        visitedPositions.add(new Position(tx, ty));
        for (String instruction : instructions) {
            Matcher matcher = instructionPattern.matcher(instruction);
            if (matcher.matches()) {
                String direction = matcher.group(1);
                int steps = Integer.parseInt(matcher.group(2));

                switch (direction) {
                    case "R":
                        hx += steps;
                        break;
                    case "L":
                        hx -= steps;
                        break;
                    case "U":
                        hy -= steps;
                        break;
                    case "D":
                        hy += steps;
                        break;
                }

                while (Math.abs(hx - tx) > 1 || Math.abs(hy - ty) > 1) {
//                    same row
                    if (hy == ty) {
                        if (hx > tx) {
                            tx++;
                        } else {
                            tx--;
                        }
//                     same column
                    } else if (hx == tx) {
                        if (hy > ty) {
                            ty++;
                        } else {
                            ty--;
                        }
//                        diagonal
                    } else {
                        if (hx > tx && hy > ty) {
                            tx++;
                            ty++;
                        } else if (hx > tx && hy < ty) {
                            tx++;
                            ty--;
                        } else if (hx < tx && hy > ty) {
                            tx--;
                            ty++;
                        } else if (hx < tx && hy < ty) {
                            tx--;
                            ty--;
                        }
                    }
                    visitedPositions.add(new Position(tx, ty));
                }

            }
        }

        System.out.println(visitedPositions.size());
    }

    public void secondPart(String filePath) throws IOException {
        List<String> instructions = readInput(filePath);

        List<Position> rope = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Position p = new Position(15, 20);
            rope.add(p);
        }

        List<Position> visitedPositions = new ArrayList<>();
        visitedPositions.add(new Position(15, 20));
        for (String instruction : instructions) {
            Matcher matcher = instructionPattern.matcher(instruction);
            if (matcher.matches()) {
                String direction = matcher.group(1);
                int steps = Integer.parseInt(matcher.group(2));
                int hx = 15;
                int hy = 20;

                for (int step = 0; step < steps; step++) {
                    for (int i = 0; i < 10; i++) {
                        Position p = rope.get(i);
                        if (i == 0) {
                            switch (direction) {
                                case "R":
                                    p.x += 1;
                                    break;
                                case "L":
                                    p.x -= 1;
                                    break;
                                case "U":
                                    p.y -= 1;
                                    break;
                                case "D":
                                    p.y += 1;
                                    break;
                            }
                            hx = p.x;
                            hy = p.y;
                            continue;
                        }

                        while (Math.abs(hx - p.x) > 1 || Math.abs(hy - p.y) > 1) {
                            if (hy == p.y) {
                                if (hx > p.x) {
                                    p.x++;
                                } else {
                                    p.x--;
                                }
                            } else if (hx == p.x) {
                                if (hy > p.y) {
                                    p.y++;
                                } else {
                                    p.y--;
                                }
                            } else {
                                if (hx > p.x && hy > p.y) {
                                    p.x++;
                                    p.y++;
                                } else if (hx > p.x && hy < p.y) {
                                    p.x++;
                                    p.y--;
                                } else if (hx < p.x && hy > p.y) {
                                    p.x--;
                                    p.y++;
                                } else if (hx < p.x && hy < p.y) {
                                    p.x--;
                                    p.y--;
                                }
                            }
                            if (i == 9) {
                                visitedPositions.add(new Position(p.x, p.y));
                            }
                        }
                        hx = p.x;
                        hy = p.y;

                    }
                }
            }
        }

        long unique = visitedPositions.stream().distinct().count();
        System.out.println(unique);
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
