package advent.of.code;

import advent.of.code.day01.Day01;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
//        day01();
    }

    private static void day01() throws IOException {
        Day01 day01 = new Day01();
        day01.firstPart("day01test.txt");
        day01.firstPart("day01input.txt");
        day01.secondPart("day01test.txt");
        day01.secondPart("day01input.txt");
    }
}
