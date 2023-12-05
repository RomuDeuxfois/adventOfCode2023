package io.adventOfCode.day5;

import io.adventOfCode.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Challenge5 {

  public static void main(String[] args) {
    Utils utils = new Utils();
    List<String> lines = utils.parseCsvFilePath("day5/example.txt");

    int result = partOne(String.join("\n", lines));
//    int result = partTwo(lines);

    System.out.println(result);
  }

  private static int partOne(String line) {
    int result = 0;

    List<String> seeds = getSeeds(line);
    List<String> seedToSoil = getSeedToSoil(line);

    System.out.println(seeds);
    System.out.println(seedToSoil);

    return result;
  }

  private static int partTwo(List<String> lines) {
    int result = 0;
    return result;
  }

  static String regexSeeds = "seeds: ([\\d\\s]+)";
  static Pattern patternSeeds = Pattern.compile(regexSeeds);

  private static List<String> getSeeds(String currentLine) {
    Matcher matcherSeeds = patternSeeds.matcher(currentLine);
    while (matcherSeeds.find()) {
      String matchSeeds = matcherSeeds.group(1);
      return Arrays.stream(matchSeeds.split(" ")).map(String::trim).toList();
    }
    return List.of();
  }

  static String regexSeedToSoil = "seed-to-soil map:([\\d\\s]+)";
  static Pattern patternSeedToSoil = Pattern.compile(regexSeedToSoil);

  private static List<String> getSeedToSoil(String currentLine) {
    Matcher matcherSeedToSoil = patternSeedToSoil.matcher(currentLine);
    while (matcherSeedToSoil.find()) {
      String matchSeedToSoil = matcherSeedToSoil.group(1);
      return Arrays.stream(matchSeedToSoil.split("\\s+"))
          .map(String::trim)
          .filter(s -> !s.isBlank())
          .toList();
    }
    return List.of();
  }

}
