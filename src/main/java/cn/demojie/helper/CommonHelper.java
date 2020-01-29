package cn.demojie.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CommonHelper {

  /**
   * Generate space string
   *
   * @param spaceCount
   * @return spaceStr
   */
  public static String generateSpace(int spaceCount) {
    char space = ' ';
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < spaceCount; i++) {
      stringBuilder.append(space);
    }
    return stringBuilder.toString();
  }

  public static void replaceAll(StringBuilder stringBuilder, String find, String replacement) {
    replaceAll(stringBuilder, find, (index) -> replacement);
  }

  public static void replaceAll(
      StringBuilder stringBuilder, String find, Function<Integer, String> function) {
    List<Integer> indexToReplace = new ArrayList<>();
    int length = find.length();
    int findIndex;
    int fromIndex = 0;
    // Find all index
    while ((findIndex = stringBuilder.indexOf(find, fromIndex)) != -1) {
      indexToReplace.add(findIndex);
      fromIndex = findIndex + length;
    }
    // Replace them from back to front
    for (int i = indexToReplace.size() - 1; i >= 0; i--) {
      stringBuilder.replace(
          indexToReplace.get(i), indexToReplace.get(i) + length, function.apply(i));
    }
  }
}
