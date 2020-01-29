package cn.demojie.helper;

import cn.demojie.model.JsonBlock;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {

  public static final String JSON_TRIPLE_QUOTES_REGEX = "\"\"\"((.|\n|\r\n|\r)*?)\"\"\"";
  public static final int JSON_TRIPLE_QUOTES_REGEX_GROUP_INDEX = 1;

  public static final String JSON_PARAMS_REGEX = "(\"?<.*?>\"?)";
  public static final String JSON_PARAMS_REGEX_REPLACEMENT = "\"TEMP_REPLACE\"";

  public static List<MatchResult> extractParams(String regx, String data) {
    Pattern pattern = Pattern.compile(regx);
    Matcher matcher = pattern.matcher(data);
    List<MatchResult> matchResults = new ArrayList<>();
    while (matcher.find()) {
      matchResults.add(matcher.toMatchResult());
    }
    return matchResults;
  }

  /**
   * Extract <xxx> or "<xxx>" and replace them to "TEMP_REPLACE"
   *
   * @param jsonBlock
   */
  public static void extractAndReplaceParams(JsonBlock jsonBlock) {

    Pattern pattern = Pattern.compile(RegexHelper.JSON_PARAMS_REGEX);
    Matcher matcher = pattern.matcher(jsonBlock.getDataWithoutQuotes());
    StringBuffer replaced = new StringBuffer();

    List<String> params = new ArrayList<>();
    while (matcher.find()) {
      params.add(matcher.group());
      matcher.appendReplacement(replaced, JSON_PARAMS_REGEX_REPLACEMENT);
    }
    if (params.isEmpty()) {
      return;
    }
    matcher.appendTail(replaced);
    jsonBlock.setDataWithoutQuotes(replaced.toString());
    jsonBlock.setParams(params);
  }

  /**
   * Replace all "TEMP_REPLACE" with params extracted from previous steps
   *
   * @param jsonBlock
   */
  public static void restoreParams(JsonBlock jsonBlock) {
    List<String> params = jsonBlock.getParams();
    if (null == params || params.isEmpty()) {
      return;
    }
    String dataWithoutQuotes = jsonBlock.getDataWithoutQuotes();

    StringBuilder stringBuilder = new StringBuilder(dataWithoutQuotes);
    int index;
    while ((index = stringBuilder.indexOf(JSON_PARAMS_REGEX_REPLACEMENT)) != -1) {
      stringBuilder.replace(
          index, JSON_PARAMS_REGEX_REPLACEMENT.length() + index, params.remove(0));
    }
    jsonBlock.setDataWithoutQuotes(stringBuilder.toString());
  }

  public static List<JsonBlock> extractJsonBlock(String data) {
    List<JsonBlock> jsonBlocks = new ArrayList<>();

    // Extract json block by regex
    Pattern pattern = Pattern.compile(JSON_TRIPLE_QUOTES_REGEX);
    Matcher matcher = pattern.matcher(data);
    while (matcher.find()) {
      MatchResult matchResult = matcher.toMatchResult();
      JsonBlock jsonBlock =
          new JsonBlock(
              matchResult.start(),
              matchResult.end(),
              matchResult.group(JSON_TRIPLE_QUOTES_REGEX_GROUP_INDEX));
      jsonBlocks.add(jsonBlock);
    }
    return jsonBlocks;
  }
}
