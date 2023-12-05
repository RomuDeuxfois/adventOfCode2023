package io.adventOfCode.day2;

import io.adventOfCode.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Challenge2 {

  public static void main(String[] args) {
    Utils utils = new Utils();
    List<String> lines = utils.parseCsvFilePath("day2/input.txt");

//    int result = partOne(lines);
    int result = partTwo(lines);

    System.out.println(result);
  }

  private static int partOne(List<String> lines) {
    int result = 0;
    String regexGameNb = "Game\\s*(\\d+)";
    Pattern patternGameNb = Pattern.compile(regexGameNb);

    String regexBlue = "(\\d+)\\sblue";
    Pattern patternBlue = Pattern.compile(regexBlue);

    String regexRed = "(\\d+)\\sred";
    Pattern patternRed = Pattern.compile(regexRed);

    String regexGreen = "(\\d+)\\sgreen";
    Pattern patternGreen = Pattern.compile(regexGreen);

    for (String line : lines) {
      List<String> rounds = Arrays.stream(line.split(";")).toList();
      boolean flag = false;
      for (String round : rounds) {
        Matcher matcherBlue = patternBlue.matcher(round);
        Matcher matcherRed = patternRed.matcher(round);
        Matcher matcherGreen = patternGreen.matcher(round);

        while (matcherBlue.find()) {
          String blue = matcherBlue.group(1);
          if (Integer.parseInt(blue) > 14) {
            flag = true;
          }
        }
        while (matcherRed.find()) {
          String red = matcherRed.group(1);
          if (Integer.parseInt(red) > 12) {
            flag = true;
          }
        }
        while (matcherGreen.find()) {
          String green = matcherGreen.group(1);
          if (Integer.parseInt(green) > 13) {
            flag = true;
          }
        }
      }
      if (!flag) {
        Matcher matcherGameNb = patternGameNb.matcher(line);
        while (matcherGameNb.find()) {
          String game = matcherGameNb.group(1);
          result += Integer.parseInt(game);
        }
      }
    }
    return result;
  }

  private static int partTwo(List<String> lines) {
    int result = 0;
    String regexGameNb = "Game\\s*(\\d+)";
    Pattern patternGameNb = Pattern.compile(regexGameNb);

    String regexBlue = "(\\d+)\\sblue";
    Pattern patternBlue = Pattern.compile(regexBlue);

    String regexRed = "(\\d+)\\sred";
    Pattern patternRed = Pattern.compile(regexRed);

    String regexGreen = "(\\d+)\\sgreen";
    Pattern patternGreen = Pattern.compile(regexGreen);

    for (String line : lines) {
      List<String> rounds = Arrays.stream(line.split(";")).toList();
      AtomicInteger maxBlue = new AtomicInteger(0);
      AtomicInteger maxRed = new AtomicInteger(0);
      AtomicInteger maxGreen = new AtomicInteger(0);
      rounds.forEach((r) -> {
        int totalBlue = 0;
        int totalRed = 0;
        int totalGreen = 0;
        Matcher matcherBlue = patternBlue.matcher(r);
        Matcher matcherRed = patternRed.matcher(r);
        Matcher matcherGreen = patternGreen.matcher(r);

        while (matcherBlue.find()) {
          String blue = matcherBlue.group(1);
          totalBlue += Integer.parseInt(blue);
        }
        while (matcherRed.find()) {
          String red = matcherRed.group(1);
          totalRed += Integer.parseInt(red);
        }
        while (matcherGreen.find()) {
          String green = matcherGreen.group(1);
          totalGreen += Integer.parseInt(green);
        }

        if (maxBlue.get() < totalBlue) {
          maxBlue.set(totalBlue);
        }
        if (maxRed.get() < totalRed) {
          maxRed.set(totalRed);
        }
        if (maxGreen.get() < totalGreen) {
          maxGreen.set(totalGreen);
        }
      });

      Matcher matcherGameNb = patternGameNb.matcher(line);
      while (matcherGameNb.find()) {
        int total = maxBlue.get() * maxRed.get() * maxGreen.get();
        result += total;
      }
    }
    return result;
  }

}
