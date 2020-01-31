package cn.demojie.helper;

import cn.demojie.model.JsonLikeBlock;
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

  public static List<JsonLikeBlock> extractJsonLikeBlock(String data) {
    List<JsonLikeBlock> jsonLikeBlocks = new ArrayList<>();

    // Extract json block by regex
    Pattern pattern = Pattern.compile(JSON_TRIPLE_QUOTES_REGEX);
    Matcher matcher = pattern.matcher(data);
    while (matcher.find()) {
      MatchResult matchResult = matcher.toMatchResult();
      JsonLikeBlock jsonLikeBlock =
          new JsonLikeBlock(
              matchResult.start(),
              matchResult.end(),
              matchResult.group(JSON_TRIPLE_QUOTES_REGEX_GROUP_INDEX));
      jsonLikeBlocks.add(jsonLikeBlock);
    }
    return jsonLikeBlocks;
  }

  /**
   * Extract <xxx> or "<xxx>" and replace them to "TEMP_REPLACE"
   *
   * @param jsonLikeBlock
   */
  public static void extractAndReplaceParams(JsonLikeBlock jsonLikeBlock) {

    Pattern pattern = Pattern.compile(RegexHelper.JSON_PARAMS_REGEX);
    Matcher matcher = pattern.matcher(jsonLikeBlock.getDataWithoutQuotes());
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
    jsonLikeBlock.setDataWithoutQuotes(replaced.toString());
    jsonLikeBlock.setParams(params);
  }

  /**
   * Replace all "TEMP_REPLACE" with params extracted from previous steps
   *
   * @param jsonLikeBlock
   */
  public static void restoreParams(JsonLikeBlock jsonLikeBlock) {
    List<String> params = jsonLikeBlock.getParams();
    if (null == params || params.isEmpty()) {
      return;
    }
    String dataWithoutQuotes = jsonLikeBlock.getDataWithoutQuotes();

    StringBuilder stringBuilder = new StringBuilder(dataWithoutQuotes);
    int index;
    while ((index = stringBuilder.indexOf(JSON_PARAMS_REGEX_REPLACEMENT)) != -1) {
      stringBuilder.replace(
          index, JSON_PARAMS_REGEX_REPLACEMENT.length() + index, params.remove(0));
    }
    jsonLikeBlock.setDataWithoutQuotes(stringBuilder.toString());
  }
}
