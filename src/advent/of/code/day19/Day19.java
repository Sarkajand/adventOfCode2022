package advent.of.code.day19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19 {

    private final Pattern pattern = Pattern.compile("^Blueprint ([0-9]+): Each ore robot costs ([0-9]+) ore. Each clay robot costs ([0-9]+) ore. Each obsidian robot costs ([0-9]+) ore and ([0-9]+) clay. Each geode robot costs ([0-9]+) ore and ([0-9]+) obsidian.$");

    public void firstPart(String filePath) throws IOException {
        List<Blueprint> blueprints = getBlueprints(filePath);
        Map<Integer, Integer> bestScores = new HashMap<>();
        for (Blueprint blueprint : blueprints) {
            Set<Integer> scores = new HashSet<>();
            int[] robots = new int[4];
            robots[0] = 1;
            openGeodes(scores, blueprint, robots, new int[4], 24);
            System.out.println("blueprint: " + blueprint.number + " " + scores);
            bestScores.put(blueprint.number, scores.stream().max(Comparator.naturalOrder()).orElse(0));
        }

        int sum = 0;
        for (Map.Entry<Integer, Integer> bestScore : bestScores.entrySet()) {
            sum += bestScore.getKey() * bestScore.getValue();
        }

        System.out.println("result: " + sum);
        System.out.println();
    }

    public void secondPart(String filePath) throws IOException {
        List<Blueprint> blueprints = getBlueprints(filePath);
        Map<Integer, Integer> bestScores = new HashMap<>();
        for (int i = 0; i < 3 && i < blueprints.size(); i++) {
            Blueprint blueprint = blueprints.get(i);
            Set<Integer> scores = new HashSet<>();
            int[] robots = new int[4];
            robots[0] = 1;
            openGeodes2(scores, blueprint, robots, new int[4], 32);
            System.out.println("blueprint: " + blueprint.number + " " + scores);
            bestScores.put(blueprint.number, scores.stream().max(Comparator.naturalOrder()).orElse(0));
        }

        int result = 1;
        for (Map.Entry<Integer, Integer> bestScore : bestScores.entrySet()) {
            result = result * bestScore.getValue();
        }

        System.out.println("result: " + result);
        System.out.println();
    }

    private int buildTime(int cost, int available, int getPerMinute) {
        if (cost <= available) {
            return 1;
        }
        if (getPerMinute == 0) {
            return 99999;
        }
        return ((cost - available) + getPerMinute - 1) / getPerMinute + 1;
    }

    private void openGeodes2(Set<Integer> scores, Blueprint blueprint, int[] robots, int[] resources, int time) {
        scores.add(resources[3] + robots[3] * time);
        if (robots[0] < blueprint.maxOreRobots) {
            int buildTime = buildTime(blueprint.oreRobotOreCost, resources[0], robots[0]);
            if (time >= buildTime) {
                int[] newRobots = new int[4];
                System.arraycopy(robots, 0, newRobots, 0, 4);
                newRobots[0] = robots[0] + 1;

                int[] newResources = new int[4];
                for (int i = 0; i < 4; i++) {
                    newResources[i] = resources[i] + robots[i] * buildTime;
                }
                newResources[0] = newResources[0] - blueprint.oreRobotOreCost;

                openGeodes2(scores, blueprint, newRobots, newResources, time - buildTime);
            }
        }

        if (robots[1] < blueprint.maxClayRobots) {
            int buildTime = buildTime(blueprint.clayRobotOreCost, resources[0], robots[0]);
            if (time >= buildTime) {
                int[] newRobots = new int[4];
                System.arraycopy(robots, 0, newRobots, 0, 4);
                newRobots[1] = robots[1] + 1;
                int[] newResources = new int[4];
                for (int i = 0; i < 4; i++) {
                    newResources[i] = resources[i] + robots[i] * buildTime;
                }
                newResources[0] = newResources[0] - blueprint.clayRobotOreCost;

                openGeodes2(scores, blueprint, newRobots, newResources, time - buildTime);
            }
        }

        if (robots[2] < blueprint.maxObsidianRobots) {
            int buildTime = Math.max(buildTime(blueprint.obsidianRobotOreCost, resources[0], robots[0]), buildTime(blueprint.obsidianRobotClayCost, resources[1], robots[1]));
            if (time >= buildTime) {
                int[] newRobots = new int[4];
                System.arraycopy(robots, 0, newRobots, 0, 4);
                newRobots[2] = robots[2] + 1;
                int[] newResources = new int[4];
                for (int i = 0; i < 4; i++) {
                    newResources[i] = resources[i] + robots[i] * buildTime;
                }
                newResources[0] = newResources[0] - blueprint.obsidianRobotOreCost;
                newResources[1] = newResources[1] - blueprint.obsidianRobotClayCost;

                openGeodes2(scores, blueprint, newRobots, newResources, time - buildTime);
            }
        }

        int buildTime = Math.max(buildTime(blueprint.geodeRobotOreCost, resources[0], robots[0]), buildTime(blueprint.geodeRobotObsidianCost, resources[2], robots[2]));
        if (time >= buildTime) {
            int[] newRobots = new int[4];
            System.arraycopy(robots, 0, newRobots, 0, 4);
            newRobots[3] = robots[3] + 1;
            int[] newResources = new int[4];
            for (int i = 0; i < 4; i++) {
                newResources[i] = resources[i] + robots[i] * buildTime;
            }
            newResources[0] = newResources[0] - blueprint.geodeRobotOreCost;
            newResources[2] = newResources[2] - blueprint.geodeRobotObsidianCost;

            openGeodes2(scores, blueprint, newRobots, newResources, time - buildTime);
        }

    }

    private void openGeodes(Set<Integer> scores, Blueprint blueprint, int[] robots, int[] resources, int time) {
        if (time == 1) {
            scores.add(resources[3] + robots[3]);
            return;
        }

        int[] canBuildRobots = blueprint.canBuiltRobots(resources, robots);

        int[] newResources = new int[4];
        for (int i = 0; i < 4; i++) {
            newResources[i] = resources[i] + robots[i];
        }

        for (int i = 0; i < 4; i++) {
            if (canBuildRobots[i] == 1) {
                int[] createdRobots = new int[4];
                System.arraycopy(robots, 0, createdRobots, 0, 4);
                createdRobots[i] = createdRobots[i] + 1;
                openGeodes(scores, blueprint, createdRobots, blueprint.createRobot(i, newResources), time - 1);
            }
        }

        openGeodes(scores, blueprint, robots, newResources, time - 1);
    }

    private List<Blueprint> getBlueprints(String filePath) throws IOException {
        String file = "src/resources/" + filePath;
        List<Blueprint> blueprints = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            while (currentLine != null) {
                Blueprint blueprint = new Blueprint();
                Matcher matcher = pattern.matcher(currentLine);
                if (matcher.matches()) {
                    blueprint.number = Integer.parseInt(matcher.group(1));
                    blueprint.oreRobotOreCost = Integer.parseInt(matcher.group(2));
                    blueprint.clayRobotOreCost = Integer.parseInt(matcher.group(3));
                    blueprint.obsidianRobotOreCost = Integer.parseInt(matcher.group(4));
                    blueprint.obsidianRobotClayCost = Integer.parseInt(matcher.group(5));
                    blueprint.geodeRobotOreCost = Integer.parseInt(matcher.group(6));
                    blueprint.geodeRobotObsidianCost = Integer.parseInt(matcher.group(7));
                    blueprint.maxOreRobots = Math.max(Math.max(Math.max(blueprint.oreRobotOreCost, blueprint.clayRobotOreCost), blueprint.obsidianRobotOreCost), blueprint.geodeRobotOreCost);
                    blueprint.maxClayRobots = blueprint.obsidianRobotClayCost;
                    blueprint.maxObsidianRobots = blueprint.geodeRobotObsidianCost;
                    blueprints.add(blueprint);
                }
                currentLine = reader.readLine();
            }
        }
        return blueprints;
    }

    private class Blueprint {
        int number;
        int oreRobotOreCost;
        int clayRobotOreCost;
        int obsidianRobotOreCost;
        int obsidianRobotClayCost;
        int geodeRobotOreCost;
        int geodeRobotObsidianCost;
        int maxOreRobots;
        int maxClayRobots;
        int maxObsidianRobots;


        int[] canBuiltRobots(int[] resources, int[] robots) {
            int[] newRobots = new int[4];
            if (resources[0] >= geodeRobotOreCost && resources[2] >= geodeRobotObsidianCost) {
                newRobots[3] = 1;
                return newRobots;
            }

            if (resources[0] >= oreRobotOreCost && robots[0] < maxOreRobots) {
                newRobots[0] = 1;
            }
            if (resources[0] >= clayRobotOreCost && robots[1] < maxClayRobots) {
                newRobots[1] = 1;
            }
            if (resources[0] >= obsidianRobotOreCost && resources[1] >= obsidianRobotClayCost && robots[2] < maxObsidianRobots) {
                newRobots[2] = 1;
            }

            return newRobots;
        }

        int[] createRobot(int robotNumber, int[] resources) {
            int[] newResources = new int[4];
            System.arraycopy(resources, 0, newResources, 0, 4);
            if (robotNumber == 0) {
                newResources[0] = newResources[0] - oreRobotOreCost;
            }
            if (robotNumber == 1) {
                newResources[0] = newResources[0] - clayRobotOreCost;
            }
            if (robotNumber == 2) {
                newResources[0] = newResources[0] - obsidianRobotOreCost;
                newResources[1] = newResources[1] - obsidianRobotClayCost;
            }
            if (robotNumber == 3) {
                newResources[0] = newResources[0] - geodeRobotOreCost;
                newResources[2] = newResources[2] - geodeRobotObsidianCost;
            }
            return newResources;
        }
    }
}
