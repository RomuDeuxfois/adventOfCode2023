package io.adventOfCode.day4;

import io.adventOfCode.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Challenge4 {

  public static void main(String[] args) {
    Utils utils = new Utils();
    List<String> lines = utils.parseCsvFilePath("day4/input.txt");

//    int result = partOne(lines);
    int result = partTwo(lines);

    System.out.println(result);
  }

  private static int partOne(List<String> lines) {
    int result = 0;
    for (String current : lines) {
      List<String> splitCard = Arrays.stream(current.split(": ")).toList();
      List<String> splitNumber = Arrays.stream(splitCard.get(1).split(" \\| ")).toList();
      List<String> winnings = Arrays.stream(splitNumber.get(0).split("\\s")).filter(s -> !s.isBlank()).toList();
      List<String> havings = Arrays.stream(splitNumber.get(1).split("\\s")).filter(s -> !s.isBlank()).toList();

      int number = 0;
      for (String v : havings) {
        if (winnings.contains(v)) {
          if (number == 0) {
            number = 1;
          } else {
            number = number * 2;
          }
        }
      }
      result += number;
    }
    return result;
  }

  private static int partTwo(List<String> lines) {
    Map<Integer, Integer> copy = new HashMap<>();
    for (int i = 0; i < lines.size(); i++) {
      String current = lines.get(i);
      List<String> splitCard = Arrays.stream(current.split(": ")).toList();
      List<String> splitNumber = Arrays.stream(splitCard.get(1).split(" \\| ")).toList();
      List<String> winnings = Arrays.stream(splitNumber.get(0).split("\\s")).filter(s -> !s.isBlank()).toList();
      List<String> havings = Arrays.stream(splitNumber.get(1).split("\\s")).filter(s -> !s.isBlank()).toList();

      int currentCard = i + 1;
      int copyNumber = copy.get(currentCard) != null ? (copy.get(currentCard) + 1) : 1;
      copy.put(currentCard, copyNumber);

      int having = 1;
      for (int j = 0; j < havings.size(); j++) {
        if (winnings.contains(havings.get(j))) {
          int card = currentCard + having;
          int copies = copy.get(currentCard) != null ? copy.get(currentCard) : 1;
          copyNumber = copy.get(card) != null ? (copy.get(card) + copies) : copies;
          copy.put(card, copyNumber);
          having += 1;
        }
      }
    }
    return copy.values().stream().reduce(0, Integer::sum);
  }
}
