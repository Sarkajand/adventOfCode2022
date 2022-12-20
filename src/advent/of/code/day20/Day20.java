package advent.of.code.day20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day20 {
    public void firstPart(String filePath) throws IOException {
        List<Integer> input = readInput(filePath);
        List<int[]> file = getFile(input);
        for (int[] value : file) {
            int inputValue = value[0];
            int actualPosition = value[1];
            int newPosition = (actualPosition + inputValue) % (file.size()-1);
            if (newPosition <= 0) {
                newPosition = file.size()-1+newPosition;
            }

            for (int[] value2 : file) {
                if (actualPosition < newPosition) {
                    if (value2[1] > actualPosition && value2[1] <= newPosition) {
                        value2[1] = value2[1] - 1;
                    }
                }

                if (actualPosition > newPosition) {
                    if (value2[1] >= newPosition && value2[1] < actualPosition) {
                        value2[1] = value2[1] + 1;
                    }
                }
            }

            value[1] = newPosition;
        }

        int[] result = new int[file.size()];
        int zeroIndex = 0;
        for (int[] value : file) {
            result[value[1]] = value[0];
            if (value[0] == 0) {
                zeroIndex = value[1];
            }
        }

        int a,b,c;
        a = result[(zeroIndex+1000)%result.length];
        b = result[(zeroIndex+2000)%result.length];
        c = result[(zeroIndex+3000)%result.length];
        System.out.println((a+b+c));
    }

    public void secondPart(String filePath) throws IOException {
        List<Integer> input = readInput(filePath);
        List<long[]> file = getFile(input, 811589153);

        for (int i = 0; i < 10; i++) {
            for (long[] value : file) {
                long inputValue = value[0];
                long actualPosition = value[1];
                long newPosition = (actualPosition + inputValue) % (file.size() - 1);
                if (newPosition <= 0) {
                    newPosition = file.size() - 1 + newPosition;
                }

                for (long[] value2 : file) {
                    if (actualPosition < newPosition) {
                        if (value2[1] > actualPosition && value2[1] <= newPosition) {
                            value2[1] = value2[1] - 1;
                        }
                    }

                    if (actualPosition > newPosition) {
                        if (value2[1] >= newPosition && value2[1] < actualPosition) {
                            value2[1] = value2[1] + 1;
                        }
                    }
                }

                value[1] = newPosition;
            }
        }

        long[] result = new long[file.size()];
        long zeroIndex = 0;
        for (long[] value : file) {
            result[(int)value[1]] = value[0];
            if (value[0] == 0) {
                zeroIndex = value[1];
            }
        }

        long a,b,c;
        a = result[(int)(zeroIndex+1000)%result.length];
        b = result[(int)(zeroIndex+2000)%result.length];
        c = result[(int)(zeroIndex+3000)%result.length];
        System.out.println((a+b+c));

    }

    private List<int[]> getFile(List<Integer> input) {
        List<int[]> file = new ArrayList<>();
        for (int i = 0; i< input.size(); i++) {
            int[] value = new int[2];
            value[0] = input.get(i);
            value[1] = i;
            file.add(value);
        }
        return file;
    }

    private List<long[]> getFile(List<Integer> input, long decryptionKey) {
        List<long[]> file = new ArrayList<>();
        for (int i = 0; i< input.size(); i++) {
            long[] value = new long[2];
            value[0] = input.get(i) * decryptionKey;
            value[1] = i;
            file.add(value);
        }
        return file;
    }

    private List<Integer> readInput(String filePath) throws IOException {
        String file = "src/resources/" + filePath;
        List<Integer> input = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine = reader.readLine();
            while (currentLine != null) {
                input.add(Integer.parseInt(currentLine));
                currentLine = reader.readLine();
            }
        }

        return input;
    }
}
