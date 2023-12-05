package io.adventOfCode.day3;

import io.adventOfCode.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Challenge3 {

  public static void main(String[] args) {
    Utils utils = new Utils();
    List<String> lines = utils.parseCsvFilePath("day3/input.txt");

//    int sum = partOne(lines);
    int sum = partTwo(lines);

    System.out.println(sum);
  }

  private static int partOne(List<String> lines) {
    String regexNumber = "(\\d+)";
    Pattern patternNumber = Pattern.compile(regexNumber);

    String regexSymbol = "([^\\p{Alnum}\\s.])";
    Pattern patternSymbol = Pattern.compile(regexSymbol);

    int sum = 0;

    for (int i = 0; i < lines.size(); i++) {
      String currentLine = lines.get(i);
      String previousLine = null;
      String nextLine = null;
      if (i > 0) {
        previousLine = lines.get(i - 1);
      }
      if (i < lines.size() - 1) {
        nextLine = lines.get(i + 1);
      }

      Matcher matcherNumber = patternNumber.matcher(currentLine);
      while (matcherNumber.find()) {
        String matchNumber = matcherNumber.group();
        int index = matcherNumber.start();
        int indexMin = index == 0 ? index : index - 1;
        int indexMax = (matchNumber.length() + index) == currentLine.length()
            ? matchNumber.length() + index
            : matchNumber.length() + index + 1;
        boolean flag = false;
        String current = currentLine.substring(indexMin, indexMax);
        Matcher matcherSymbol = patternSymbol.matcher(current);
        if (matcherSymbol.find()) {
          flag = true;
        }
        if (!flag && previousLine != null) {
          String previous = previousLine.substring(indexMin, indexMax);
          matcherSymbol = patternSymbol.matcher(previous);
          if (matcherSymbol.find()) {
            flag = true;
          }
        }
        if (!flag && nextLine != null) {
          String next = nextLine.substring(indexMin, indexMax);
          matcherSymbol = patternSymbol.matcher(next);
          if (matcherSymbol.find()) {
            flag = true;
          }
        }
        if (flag) {
          System.out.println(matchNumber);
          sum += Integer.parseInt(matchNumber);
        }
      }
    }
    return sum;
  }

  private static int partTwo(List<String> lines) {
    Map<Integer, Map<Integer, String>> map = new HashMap<>();
    String regexNumber = "(\\d+)";
    Pattern patternNumber = Pattern.compile(regexNumber);

    // Carto
    for (int i = 0; i < lines.size(); i++) {
      String current = lines.get(i);
      Matcher matcherSymbol = patternNumber.matcher(current);
      Map<Integer, String> mapLine = new HashMap<>();
      while (matcherSymbol.find()) {
        String match = matcherSymbol.group();
        mapLine.put(matcherSymbol.start(), match);
        mapLine.put(matcherSymbol.end() - 1, match);
      }
      map.put(i, mapLine);
    }

    String regexGear = "(\\*)";
    Pattern patterGear = Pattern.compile(regexGear);
    int sum = 0;
    for (int i = 0; i < lines.size(); i++) {
      Map<Integer, String> mapLine = map.get(i);
      Map<Integer, String> mapPrevious = null;
      Map<Integer, String> mapNext = null;
      if (i > 0) {
        mapPrevious = map.get(i - 1);
      }
      if (i < lines.size()) {
        mapNext = map.get(i + 1);
      }
      String current = lines.get(i);
      Matcher matcherSymbol = patterGear.matcher(current);
      while (matcherSymbol.find()) {
        List<String> groups = new ArrayList<>();
        String group = mapLine.get(matcherSymbol.start() - 1);
        if (group != null) {
          groups.add(group);
        }
        group = mapLine.get(matcherSymbol.start() + 1);
        if (group != null) {
          groups.add(group);
        }
        if (mapPrevious != null) {
          group = mapPrevious.get(matcherSymbol.start());
          if (group != null) {
            groups.add(group);
          } else {
            String groupLeft = mapPrevious.get(matcherSymbol.start() - 1);
            if (groupLeft != null) {
              groups.add(groupLeft);
            }
            String groupRight = mapPrevious.get(matcherSymbol.start() + 1);
            if (groupRight != null && !groupRight.equals(groupLeft)) {
              groups.add(groupRight);
            }
          }
        }
        if (mapNext != null) {
          group = mapNext.get(matcherSymbol.start());
          if (group != null) {
            groups.add(group);
          } else {
            String groupLeft = mapNext.get(matcherSymbol.start() - 1);
            if (groupLeft != null) {
              groups.add(groupLeft);
            }
            String groupRight = mapNext.get(matcherSymbol.start() + 1);
            if (groupRight != null && !groupRight.equals(groupLeft)) {
              groups.add(groupRight);
            }
          }
        }
        if (groups.size() > 1) {
          Integer total = groups.stream().map(Integer::parseInt).reduce(1, (acc, el) -> acc * el);
          sum += total;
        }
      }
    }

    return sum;
  }

}
