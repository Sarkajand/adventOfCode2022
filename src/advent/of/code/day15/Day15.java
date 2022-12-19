package advent.of.code.day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day15 {

    private final Pattern pattern = Pattern.compile("Sensor at x=(-?[0-9]+), y=(-?[0-9]+): closest beacon is at x=(-?[0-9]+), y=(-?[0-9]+)");

    public void firstPart(String filePath, int y) throws IOException {
        List<String> input = readInput(filePath);
        List<Sensor> sensors = createSensors(input);
        Set<Integer> ySet = new HashSet<>();
        Set<Integer> YSensorsAndBeacons = new HashSet<>();
        for (Sensor sensor : sensors) {
            if (sensor.y - sensor.distance <= y && y <= sensor.y + sensor.distance) {
                int minX = sensor.x - (sensor.distance - Math.abs(sensor.y - y));
                int maxX = sensor.x + (sensor.distance - Math.abs(sensor.y - y));
                for (int i = minX; i <= maxX; i++) {
                    ySet.add(i);
                }
                if (sensor.y == y) {
                    YSensorsAndBeacons.add(sensor.x);
                }

                if (sensor.by == y) {
                    YSensorsAndBeacons.add(sensor.bx);
                }
            }
        }
        List<Integer> sorted = ySet.stream().filter(x -> !YSensorsAndBeacons.contains(x)).sorted().collect(Collectors.toList());
        System.out.println(sorted.size());
    }

    public void secondPart(String filePath, int mapSize) throws IOException {
        List<String> input = readInput(filePath);
        List<Sensor> sensors = createSensors(input);

        int resultY = -1;
        int resultX = -1;

        for (Sensor sensor : sensors) {
            if (resultX > -1) {
                break;
            }

            int distance = sensor.distance + 1;

            for (int i = -distance; i <= distance; i++) {
                int y = sensor.y + i;
                int distX = distance - Math.abs(i);
                int xl = sensor.x - distX;
                int xr = sensor.x + distX;

                if (xl >= 0 && xl <= mapSize && y >= 0 && y <= mapSize) {
                    int visibleToSensors = 0;
                    for (Sensor sensor2 : sensors) {
                        if (Math.abs(sensor2.x - xl) + Math.abs(sensor2.y - y) <= sensor2.distance) {
                            visibleToSensors++;
                        }
                    }
                    if (visibleToSensors == 0) {
                        resultY = y;
                        resultX = xl;
                        break;
                    }
                }

                if (xr >= 0 && xr <= mapSize && y >= 0 && y <= mapSize) {
                    int visibleToSensors = 0;
                    for (Sensor sensor2 : sensors) {
                        if (Math.abs(sensor2.x - xr) + Math.abs(sensor2.y - y) <= sensor2.distance) {
                            visibleToSensors++;
                        }
                    }
                    if (visibleToSensors == 0) {
                        resultY = y;
                        resultX = xr;
                        break;
                    }
                }
            }
        }

        System.out.println("x: " + resultX);
        System.out.println("y: " + resultY);
        long result = resultX * 4000000L + resultY;
        System.out.println("result:" + result);
    }

    private List<Sensor> createSensors(List<String> input) {
        List<Sensor> sensors = new ArrayList<>();
        for (String line : input) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.matches()) {
                Sensor sensor = new Sensor();
                sensor.x = Integer.parseInt(matcher.group(1));
                sensor.y = Integer.parseInt(matcher.group(2));
                sensor.bx = Integer.parseInt(matcher.group(3));
                sensor.by = Integer.parseInt(matcher.group(4));
                sensor.distance = Math.abs(sensor.x - sensor.bx) + Math.abs(sensor.y - sensor.by);
                sensors.add(sensor);
            }
        }
        return sensors;
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

    private class Sensor {
        int x;
        int y;
        int bx;
        int by;
        int distance;
    }
}
