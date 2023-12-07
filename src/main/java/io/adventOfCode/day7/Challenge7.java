package io.adventOfCode.day7;

import io.adventOfCode.Utils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Challenge7 {

  public static void main(String[] args) {
    Utils utils = new Utils();
    List<String> lines = utils.parseCsvFilePath("day7/input.txt");

//    int result = partOne(lines);
    int result = partTwo(lines);

    System.out.println(result);
  }

  public static class Hand {

    public List<String> cards;
    public Integer bid;

    public Hand(List<String> cards, Integer bid) {
      this.cards = cards;
      this.bid = bid;
    }
  }

  // -- PART ONE --

  private static int partOne(List<String> lines) {
    int result = 0;
    Map<Integer, List<Hand>> map = new HashMap<>();
    for (String current : lines) {
      String[] list = current.split(" ");
      List<String> cards = Arrays.stream(list[0].split("")).toList();
      String bid = list[1];
      Hand hand = new Hand(cards, Integer.parseInt(bid));

      List<Hand> mapList = map.get(handTypePartOne(cards));
      if (mapList == null) {
        mapList = new ArrayList<>();
      }
      mapList.add(hand);
      map.put(handTypePartOne(cards), mapList);
    }
    List<Map.Entry<Integer, List<Hand>>> sorted = map.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByKey())
        .toList();
    int rank = 1;
    for (Map.Entry<Integer, List<Hand>> v : sorted) {
      List<Hand> hands = v.getValue();
      sortHandPartOne(hands);
      for (Hand hand : v.getValue()) {
        result = result + rank * hand.bid;
        rank += 1;
      }
    }
    return result;
  }

  private static int handTypePartOne(List<String> cards) {
    Map<String, Long> identityCount = cards.stream()
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    if (identityCount.size() == 1 && identityCount.containsValue(5L)) {
      // Five of a kind
      return 7;
    } else if (identityCount.size() == 2 && identityCount.containsValue(4L)) {
      // Four of a kind
      return 6;
    } else if (identityCount.size() == 2 && identityCount.containsValue(3L) && identityCount.containsValue(2L)) {
      // Full house
      return 5;
    } else if (identityCount.size() == 3 && identityCount.containsValue(3L)) {
      // Three of a kind
      return 4;
    } else if (identityCount.size() == 3 && identityCount.containsValue(2L)) {
      // Two pair
      return 3;
    } else if (identityCount.size() == 4 && identityCount.containsValue(2L)) {
      // One pair
      return 2;
    } else if (identityCount.size() == 5) {
      // High card
      return 1;
    }
    return 0;
  }

  public static Map<String, Integer> highestPartOne = new HashMap<>() {{
    put("A", 13);
    put("K", 12);
    put("Q", 11);
    put("J", 10);
    put("T", 9);
    put("9", 8);
    put("8", 7);
    put("7", 6);
    put("6", 5);
    put("5", 4);
    put("4", 3);
    put("3", 2);
    put("2", 1);
  }};

  private static void sortHandPartOne(List<Hand> hands) {
    hands.sort((h1, h2) -> {
      for (int i = 0; i < h1.cards.size(); i++) {
        int compare = highestPartOne.get(h1.cards.get(i)).compareTo(highestPartOne.get(h2.cards.get(i)));
        if (compare != 0) {
          return compare;
        }
      }
      return 0;
    });
  }

  // -- PART TWO --

  private static int partTwo(List<String> lines) {
    int result = 0;
    Map<Long, List<Hand>> map = new HashMap<>();
    for (String current : lines) {
      String[] list = current.split(" ");
      List<String> cards = Arrays.stream(list[0].split("")).toList();
      String bid = list[1];
      Hand hand = new Hand(cards, Integer.parseInt(bid));

      List<Hand> mapList = map.get(handTypePartTwo(cards));
      if (mapList == null) {
        mapList = new ArrayList<>();
      }
      mapList.add(hand);
      map.put(handTypePartTwo(cards), mapList);
    }
    List<Map.Entry<Long, List<Hand>>> sorted = map.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByKey())
        .toList();
    int rank = 1;
    for (Map.Entry<Long, List<Hand>> v : sorted) {
      System.out.println(v.getKey());
      List<Hand> hands = v.getValue();
      sortHandPartTwo(hands);
      for (Hand hand : v.getValue()) {
        System.out.println(hand.cards);
        result = result + rank * hand.bid;
        rank += 1;
      }
    }
    return result;
  }

  private static long handTypePartTwo(List<String> cards) {
    long result = 0;
    Map<String, Long> identityCount = cards.stream()
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    if (identityCount.size() == 1 && identityCount.containsValue(5L)) {
      // Five of a kind
      result = 7;
    } else if (identityCount.size() == 2 && identityCount.containsValue(4L)) { // 2, 2, 2, 2, J || // 2, J, J, J, J
      if (identityCount.containsKey("J")) {
        // Five of a kind
        result = 7;
      } else {
        // Four of a kind
        result = 6;
      }
    } else if (identityCount.size() == 2 && identityCount.containsValue(3L) && identityCount.containsValue(2L)) { // 2, 2, 2, J, J || // 2, 2, J, J, J
      if (identityCount.containsKey("J")) {
        Long countJ = identityCount.get("J");
        if (countJ == 3) {
          // Five of a kind
          result = 7;
        } else if (countJ == 2) {
          // Five of a kind
          result = 7;
        }
      } else {
        // Full house
        result = 5;
      }
    } else if (identityCount.size() == 3 && identityCount.containsValue(3L)) { // 2, 2, 2, 4, J || // 2, 3, J, J, J
      if (identityCount.containsKey("J")) {
        Long countJ = identityCount.get("J");
        if (countJ == 3) {
          // Four of a kind
          result = 6;
        } else if (countJ == 1) {
          // Four of a kind
          result = 6;
        }
      } else {
        // Three of a kind
        result = 4;
      }
    } else if (identityCount.size() == 3 && identityCount.containsValue(2L)) { // 2, 2, 4, 4, J || // 2, 2, 4, J, J
      if (identityCount.containsKey("J")) {
        Long countJ = identityCount.get("J");
        if (countJ == 2) {
          // Four of a kind
          result = 6;
        } else if (countJ == 1) {
          // Full house
          result = 5;
        }
      } else {
        // Two pair
        result = 3;
      }
    } else if (identityCount.size() == 4 && identityCount.containsValue(2L)) { // 2, 2, 4, 5, J || // 2, 3, 4, J, J
      if (identityCount.containsKey("J")) {
        Long countJ = identityCount.get("J");
        if (countJ == 2) {
          // Three of a kind with J pair
          result = 4;
        } else if (countJ == 1) {
          // Three of a kind
          result = 4;
        }
      } else {
        // One pair
        result = 2;
      }
    } else if (identityCount.size() == 5) { // 2, 3, 4, 5, J
      if (identityCount.containsKey("J")) {
        Long countJ = identityCount.get("J");
        if (countJ == 1) {
          // One pair
          result = 2;
        }
      } else {
        // High card
        result = 1;
      }
    }
    return result;
  }

  public static Map<String, Integer> highestPartTwo = new HashMap<>() {{
    put("A", 13);
    put("K", 12);
    put("Q", 11);
    put("T", 10);
    put("9", 9);
    put("8", 8);
    put("7", 7);
    put("6", 6);
    put("5", 5);
    put("4", 4);
    put("3", 3);
    put("2", 2);
    put("J", 1);
  }};

  private static void sortHandPartTwo(List<Hand> hands) {
    hands.sort((h1, h2) -> {
      for (int i = 0; i < h1.cards.size(); i++) {
        int compare = highestPartTwo.get(h1.cards.get(i)).compareTo(highestPartTwo.get(h2.cards.get(i)));
        if (compare != 0) {
          return compare;
        }
      }
      return 0;
    });
  }

}
