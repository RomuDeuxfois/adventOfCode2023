package io.adventOfCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class Utils {

  public List<String> parseCsvFilePath(final String path) {
    ClassLoader classLoader = getClass().getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(path)) {
      assert inputStream != null;
      StringBuilder resultStringBuilder = new StringBuilder();
      try (BufferedReader br
          = new BufferedReader(new InputStreamReader(inputStream))) {
        String line;
        while ((line = br.readLine()) != null) {
          resultStringBuilder.append(line).append("\n");
        }
      }
      return Arrays.stream(resultStringBuilder.toString().split("\n")).toList();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
