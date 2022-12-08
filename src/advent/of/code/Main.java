package advent.of.code;

import advent.of.code.day01.Day01;
import advent.of.code.day02.Day02;
import advent.of.code.day03.Day03;
import advent.of.code.day04.Day04;
import advent.of.code.day05.Day05;
import advent.of.code.day06.Day06;
import advent.of.code.day07.Day07;
import advent.of.code.day08.Day08;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
//        day01();
//        day02();
//        day03();
//        day04();
//        day05();
//        day06();
//        day07();
        day08();
    }

    private static void day08() throws IOException {
        Day08 day08 = new Day08();
        day08.firstPart("day08test.txt");
        day08.firstPart("day08input.txt");
        day08.secondPart("day08test.txt");
        day08.secondPart("day08input.txt");
    }

    private static void day07() throws IOException {
        Day07 day07 = new Day07();
        day07.firstPart("day07test.txt");
        day07.firstPart("day07input.txt");
        day07.secondPart("day07test.txt");
        day07.secondPart("day07input.txt");
    }

    private static void day06() throws IOException {
        Day06 day06 = new Day06();
        day06.firstPart("day06test.txt");
        day06.firstPart("day06input.txt");
        day06.secondPart("day06test.txt");
        day06.secondPart("day06input.txt");
    }

    private static void day05() throws IOException {
        Day05 day05 = new Day05();
        day05.firstPart("day05test.txt");
        day05.firstPart("day05input.txt");
        day05.secondPart("day05test.txt");
        day05.secondPart("day05input.txt");
    }

    private static void day04() throws IOException {
        Day04 day04 = new Day04();
        day04.firstPart("day04test.txt");
        day04.firstPart("day04input.txt");
        day04.secondPart("day04test.txt");
        day04.secondPart("day04input.txt");
    }

    private static void day03() throws IOException {
        Day03 day03 = new Day03();
        day03.firstPart("day03test.txt");
        day03.firstPart("day03input.txt");
        day03.secondPart("day03test.txt");
        day03.secondPart("day03input.txt");
    }

    private static void day02() throws IOException {
        Day02 day02 = new Day02();
        day02.firstPart("day02test.txt");
        day02.firstPart("day02input.txt");
        day02.secondPart("day02test.txt");
        day02.secondPart("day02input.txt");
    }


    private static void day01() throws IOException {
        Day01 day01 = new Day01();
        day01.firstPart("day01test.txt");
        day01.firstPart("day01input.txt");
        day01.secondPart("day01test.txt");
        day01.secondPart("day01input.txt");
    }
}
