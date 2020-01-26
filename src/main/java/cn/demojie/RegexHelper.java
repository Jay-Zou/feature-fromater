package cn.demojie;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {

  public static final String JSON_TRIPLE_QUOTES_REGEX = "(\"\"\".*?\"\"\")";
  public static final String JSON_PARAMS_REGEX = "(\\<.*?\\>)";

  public static List<MatchResult> extractParams(String regx, String data) {
    Pattern pattern = Pattern.compile(regx);
    Matcher matcher = pattern.matcher(data);
    List<MatchResult> matchResults = new ArrayList<>();
    while (matcher.find()) {
      matchResults.add(matcher.toMatchResult());
    }
    return matchResults;
  }

  public static List<String>  extractTripleQuotesBlock(String fileContent) {
    List<String> tripleQuotesBlocks = new ArrayList<>();

    Pattern pattern = Pattern.compile(JSON_TRIPLE_QUOTES_REGEX);
    Matcher matcher = pattern.matcher(fileContent);
    List<MatchResult> matchResults = new ArrayList<>();
    while (matcher.find()) {
      matchResults.add(matcher.toMatchResult());
    }
//    return matchResults;
    return tripleQuotesBlocks;
  }
}
