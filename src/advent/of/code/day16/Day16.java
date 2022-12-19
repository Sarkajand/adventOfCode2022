package advent.of.code.day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day16 {

    private final Pattern pattern = Pattern.compile("^Valve (..) has flow rate=([0-9]+); tunnels? leads? to valves? (.+)$");

    public void firstPart(String filePath) throws IOException {
        List<String> input = readInput(filePath);
        Map<String, Valve> valves = getValves(input);
        setDistances(valves);
        Map<String, Valve> notNullValves = valves.values().stream()
                .filter(valve -> valve.flowRate != 0)
                .collect(Collectors.toMap(valve -> valve.name, valve -> valve));
        List<Integer> scores = new ArrayList<>();
        List<String> opened = new ArrayList<>();
        getScore(notNullValves, scores, valves.get("AA"), opened, 30, 0);
        Collections.sort(scores);
        System.out.println(scores.get(scores.size() - 1));
    }

    public void secondPart(String filePath) throws IOException {
        List<String> input = readInput(filePath);
        Map<String, Valve> valves = getValves(input);
        setDistances(valves);
        List<Valve> notNullValves = valves.values().stream()
                .filter(valve -> valve.flowRate != 0)
                .collect(Collectors.toList());
        Map<String, Integer> opened = new HashMap<>();
        Set<Integer> scores = new HashSet<>();

        getScore(notNullValves, scores, valves.get("AA"), -1, valves.get("AA"), -1, opened, 26, 0);

        List<Integer> sortedScores = scores.stream().sorted().collect(Collectors.toList());
        System.out.println(sortedScores.get(sortedScores.size() - 1));
    }

    private void getScore(List<Valve> remainingValves, Set<Integer> scores, Valve valve1, int timeToValve1, Valve valve2, int timeToValve2, Map<String, Integer> opened, Integer remainingTime, Integer score) {
        int openedSum = opened.values().stream().mapToInt(x -> x).sum();
        int newScore = score + openedSum;


        if (remainingTime == 0) {
            scores.add(score);
            return;
        }
        if (remainingValves.isEmpty() && timeToValve1 <= -1 && timeToValve2 <= -1) {
            scores.add(score + (openedSum * remainingTime));
            return;
        }

        Map<String, Integer> newOpened = new HashMap<>(opened);
        if (timeToValve1 == 0) {
            newOpened.put(valve1.name, valve1.flowRate);
        }

        if (timeToValve2 == 0) {
            newOpened.put(valve2.name, valve2.flowRate);
        }

        if (timeToValve1 == -1 && timeToValve2 != -1) {
            for (Valve valveTo : remainingValves) {
                int distance = valve1.distances.get(valveTo.name);
                if (distance > remainingTime) {
                    continue;
                }
                List<Valve> newRemainingValves = new ArrayList<>(remainingValves);
                newRemainingValves.remove(valveTo);

                getScore(newRemainingValves, scores, valveTo, distance-1, valve2, timeToValve2-1, newOpened, remainingTime-1, newScore);
            }
        } else if (timeToValve1 != -1 && timeToValve2 == -1) {
            for (Valve valveTo : remainingValves) {
                int distance = valve2.distances.get(valveTo.name);
                if (distance > remainingTime) {
                    continue;
                }
                List<Valve> newRemainingValves = new ArrayList<>(remainingValves);
                newRemainingValves.remove(valveTo);

                getScore(newRemainingValves, scores, valve1, timeToValve1-1, valveTo, distance-1, newOpened, remainingTime-1, newScore);
            }
        } else if ((timeToValve1 == -1 && timeToValve2 == -1)) {
            for (Valve valveTo1 : remainingValves) {
                if (valveTo1.name.equals(valve1.name)) {
                    continue;
                }
                int distance1 = valve1.distances.get(valveTo1.name);
                if (distance1 > remainingTime) {
                    continue;
                }
                for (Valve valveTo2 : remainingValves) {
                    if (valveTo2.name.equals(valve2.name) || valveTo2.name.equals(valveTo1.name)) {
                        continue;
                    }

                    int distance2 = valve2.distances.get(valveTo2.name);
                    if (distance2 > remainingTime) {
                        continue;
                    }

                    List<Valve> newRemainingValves = new ArrayList<>(remainingValves);
                    newRemainingValves.remove(valveTo1);
                    newRemainingValves.remove(valveTo2);

                    getScore(newRemainingValves, scores, valveTo1, distance1-1, valveTo2, distance2-1, newOpened, remainingTime-1, newScore);
                }
            }
        }
        getScore(remainingValves, scores, valve1, timeToValve1-1, valve2, timeToValve2-1, newOpened, remainingTime-1, newScore);
    }

    private void getScore(Map<String, Valve> notNullValves, List<Integer> scores, Valve valve, List<String> opened, Integer remainingTime, Integer score) {
        for (Valve valveTo : notNullValves.values()) {
            if (opened.contains(valveTo.name)) {
                continue;
            }
            int distance = valve.distances.get(valveTo.name);
            if (distance > remainingTime) {
                continue;
            }
            List<String> newOpened = new ArrayList<>(opened);
            newOpened.add(valveTo.name);
            getScore(notNullValves, scores, valveTo, newOpened, remainingTime - distance - 1, score + valve.flowRate * remainingTime);
        }

        scores.add(score + valve.flowRate * remainingTime);
    }

    private void setDistances(Map<String, Valve> valves) {
        // Floyd Warshall algorithm
        List<Valve> valveList = new ArrayList<>(valves.values());
        int[][] matrix = new int[valveList.size()][valveList.size()];
        for (int[] ints : matrix) {
            Arrays.fill(ints, 9999);
        }

        int i, j, k;

        for (i = 0; i < valveList.size(); i++) {
            Valve from = valveList.get(i);
            for (j = 0; j < valveList.size(); j++) {
                if (i == j) {
                    matrix[i][j] = 0;
                }
                Valve to = valveList.get(j);
                if (from.nextValves.contains(to.name)) {
                    matrix[i][j] = 1;
                }
            }
        }

        for (k = 0; k < valveList.size(); k++) {
            for (i = 0; i < valveList.size(); i++) {
                for (j = 0; j < valveList.size(); j++) {
                    if (matrix[i][k] + matrix[k][j] < matrix[i][j])
                        matrix[i][j] = matrix[i][k] + matrix[k][j];
                }
            }
        }

        for (i = 0; i < valveList.size(); i++) {
            Valve from = valveList.get(i);
            for (j = 0; j < valveList.size(); j++) {
                if (i == j) {
                    continue;
                }
                from.distances.put(valveList.get(j).name, matrix[i][j]);
            }
        }
    }

    private Map<String, Valve> getValves(List<String> input) {
        Map<String, Valve> valveMap = new HashMap<>();
        for (String valveInput : input) {
            Valve valve = new Valve();
            Matcher matcher = pattern.matcher(valveInput);
            if (matcher.matches()) {
                valve.name = matcher.group(1);
                valve.flowRate = Integer.parseInt(matcher.group(2));
                valve.nextValves = Arrays.asList(matcher.group(3).split(", ").clone());
                valveMap.put(valve.name, valve);
            }
        }

        return valveMap;
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

    private class Valve {
        String name;
        Integer flowRate = 0;
        List<String> nextValves = new ArrayList<>();
        Map<String, Integer> distances = new HashMap<>();
    }
}
