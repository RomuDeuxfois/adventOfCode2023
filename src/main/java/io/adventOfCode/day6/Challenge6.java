package io.adventOfCode.day6;

import io.adventOfCode.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Challenge6 {

  public static void main(String[] args) {
    Utils utils = new Utils();
    List<String> lines = utils.parseCsvFilePath("day6/input.txt");

//    int result = partOne(String.join("\n", lines));
    int result = partTwo(String.join("\n", lines));

    System.out.println(result);
  }

  private static int partTwo(String line) {
    long time = Long.parseLong(String.join("", values(patternTime, line)));
    long maxScore = Long.parseLong(String.join("", values(patternScore, line)));

    return compute(time, maxScore);
  }

  private static int partOne(String line) {
    List<Long> times = values(patternTime, line).stream().map(Long::parseLong).toList();
    List<Long> scores = values(patternScore, line).stream().map(Long::parseLong).toList();
    Map<Long, Long> games = IntStream.range(0, times.size())
        .boxed()
        .collect(Collectors.toMap(times::get, scores::get));

    List<Integer> count = new ArrayList<>();
    games.forEach((time, maxScore) -> {
      count.add(compute(time, maxScore));
    });

    return count.stream().reduce(1, (acc, el) -> acc * el);
  }

  private static int compute(long time, long maxScore) {
    long hold = 0;
    Boolean flag = null;
    List<Long> possibilities = new ArrayList<>();
    while (flag == null || flag) { // Stop when result decrease
      long result = toyScore(time, hold);
      if (result > maxScore) {
        possibilities.add(hold);
        if (flag == null) {
          flag = true;
        }
      } else {
        if (Boolean.TRUE.equals(flag)) {
          flag = false;
        }
      }
      hold += 1;
    }
    return possibilities.size();
  }

  static Pattern patternTime = Pattern.compile("Time: ([\\d\\s]+)");
  static Pattern patternScore = Pattern.compile("Distance: ([\\d\\s]+)");

  private static List<String> values(Pattern pattern, String currentLine) {
    Matcher matcherTime = pattern.matcher(currentLine);
    while (matcherTime.find()) {
      String match = matcherTime.group(1);
      return Arrays.stream(match.split("\\s+")).map(String::trim).filter(s -> !s.isBlank()).toList();
    }
    return List.of();
  }

  private static long toyScore(Long score, Long hold) {
    return (score - hold) * hold;
  }

}
