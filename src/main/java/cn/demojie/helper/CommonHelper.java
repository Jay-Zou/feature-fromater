package cn.demojie.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CommonHelper {

  /**
   * Generate space string
   *
   * @param spaceCount the count used for generating the space
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

  /**
   * ReplaceAll method for StringBuilder
   *
   * @param stringBuilder
   * @param find
   * @param replacement
   */
  public static void replaceAll(StringBuilder stringBuilder, String find, String replacement) {
    replaceAll(stringBuilder, find, (index) -> replacement);
  }

  /**
   * ReplaceAll method for StringBuilder. Can get the index when replace
   *
   * @param stringBuilder
   * @param find
   * @param function
   */
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
