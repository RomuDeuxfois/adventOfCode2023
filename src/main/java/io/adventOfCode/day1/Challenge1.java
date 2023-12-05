package io.adventOfCode.day1;

import io.adventOfCode.Utils;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Challenge1 {

  public static void main(String[] args) {
    Utils utils = new Utils();
    List<String> lines = utils.parseCsvFilePath("day1/input.txt");

//    int result = partOne(lines);
    int result = partTwo(lines);

    System.out.println(result);
  }

  private static int partOne(List<String> lines) {
    int result = 0;
    String regexNumber = "(\\d)";
    Pattern patternNumber = Pattern.compile(regexNumber);
    for (String line : lines) {
      Matcher matcherNumber = patternNumber.matcher(line);
      List<String> results = new ArrayList<>(matcherNumber.results().map(MatchResult::group).toList());
      String first = results.get(0);
      Collections.reverse(results);
      String end = results.get(0);
      int value = Integer.parseInt(first + end);
      result += value;
    }
    return result;
  }

  private static int partTwo(List<String> lines) {
    int result = 0;
    for (String line : lines) {
      Map<String, String> possibilities = new HashMap<>();
      possibilities.put("one", "1");
      possibilities.put("two", "2");
      possibilities.put("three", "3");
      possibilities.put("four", "4");
      possibilities.put("five", "5");
      possibilities.put("six", "6");
      possibilities.put("seven", "7");
      possibilities.put("eight", "8");
      possibilities.put("nine", "9");
      possibilities.put("1", "1");
      possibilities.put("2", "2");
      possibilities.put("3", "3");
      possibilities.put("4", "4");
      possibilities.put("5", "5");
      possibilities.put("6", "6");
      possibilities.put("7", "7");
      possibilities.put("8", "8");
      possibilities.put("9", "9");

      int firstIdx = line.length();
      String firstDigit = "";
      int lastIdx = -1;
      String lastDigit = "";
      for (Map.Entry<String, String> entry : possibilities.entrySet()) {
        // Search first element
        if (line.contains(entry.getKey()) && line.indexOf(entry.getKey()) < firstIdx) {
          firstIdx = line.indexOf(entry.getKey());
          firstDigit = entry.getValue();
        }
        // Search last element
        if (line.lastIndexOf(entry.getKey()) > lastIdx) {
          lastIdx = line.lastIndexOf(entry.getKey());
          lastDigit = entry.getValue();
        }
      }

      String concat = firstDigit + lastDigit;
      if (!concat.isEmpty()) {
        int value = Integer.parseInt(concat);
        result += value;
      }
    }
    return result;
  }
}
