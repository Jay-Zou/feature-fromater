package cn.demojie.helper;

import cn.demojie.helper.RegexHelper;
import cn.demojie.model.JsonBlock;
import java.util.List;
import java.util.regex.MatchResult;
import org.junit.Assert;
import org.junit.Test;

public class RegexHelperTest {

  @Test
  public void test() {
    String data = "123r6546<12r12asdfasdf>67<675865n>i";
    JsonBlock jsonBlock = new JsonBlock();
    jsonBlock.setDataWithoutQuotes(data);

    RegexHelper.extractAndReplaceParams(jsonBlock);

    System.out.println();
  }

  @Test
  public void extractParams1() {
    String[] expectedArr = {"<12r12asdfasdf>", "\"<675865n>\""};
    String data = "123r6546<12r12asdfasdf>67\"<675865n>\"i";
    List<MatchResult> matchResults = RegexHelper.extractParams(RegexHelper.JSON_PARAMS_REGEX, data);

    Assert.assertNotNull(matchResults);
    Assert.assertFalse(matchResults.isEmpty());

    for (int i = 0; i < matchResults.size(); i++) {
      MatchResult matchResult = matchResults.get(i);

      System.out.println("=================================================");
      System.out.println("groupCount: " + matchResult.groupCount());
      System.out.println("group     : " + matchResult.group());
      int groupCount = matchResult.groupCount();
      for (int j = 0; j <= groupCount; j++) {
        System.out.println("group(" + j + ")  : " + matchResult.group(j));
        System.out.println("start(" + j + ")     : " + matchResult.start(j));
        System.out.println("end(" + j + ")       : " + matchResult.end(j));
      }

      Assert.assertEquals(expectedArr[i], matchResult.group(0));
    }
  }

  @Test
  public void extractParams2() {
    String[] expectedArr = {"\"\"\"asdfasdfadsfafas\"\"\"", "\"\"\"zxcvdhd\"\"\""};
    String data = "4537h\nh\"\"\"\nasdfasdfadsfafa\ns\"\"\"123412\"\"\"zxcvdhd\"\"\"m98";
    List<MatchResult> matchResults =
        RegexHelper.extractParams(RegexHelper.JSON_TRIPLE_QUOTES_REGEX, data);

    Assert.assertNotNull(matchResults);
    Assert.assertFalse(matchResults.isEmpty());

    for (int i = 0; i < matchResults.size(); i++) {
      MatchResult matchResult = matchResults.get(i);

      System.out.println("=================================================");
      System.out.println("groupCount: " + matchResult.groupCount());
      System.out.println("group     : " + matchResult.group());
      int groupCount = matchResult.groupCount();
      for (int j = 0; j <= groupCount; j++) {
        System.out.println("group(" + j + ")  : " + matchResult.group(j));
        System.out.println("start(" + j + ")     : " + matchResult.start(j));
        System.out.println("end(" + j + ")       : " + matchResult.end(j));
      }

      Assert.assertEquals(expectedArr[i], matchResult.group(0));
    }
  }

  @Test
  public void extractParams3() {
    String[] expectedArr = {"\"\"\"asdfasdfadsfafas\"\"\"", "\"\"\"zxcvdhd\"\"\""};
    String data = "    \"\"\"\n" + "      This is not json\n" + "    \"\"\"";
    List<MatchResult> matchResults =
        RegexHelper.extractParams(RegexHelper.JSON_TRIPLE_QUOTES_REGEX, data);

    Assert.assertNotNull(matchResults);
    Assert.assertFalse(matchResults.isEmpty());

    for (int i = 0; i < matchResults.size(); i++) {
      MatchResult matchResult = matchResults.get(i);

      System.out.println("=================================================");
      System.out.println("groupCount: " + matchResult.groupCount());
      System.out.println("group     : " + matchResult.group());
      int groupCount = matchResult.groupCount();
      for (int j = 0; j <= groupCount; j++) {
        System.out.println("group(" + j + ")  : " + matchResult.group(j));
        System.out.println("start(" + j + ")     : " + matchResult.start(j));
        System.out.println("end(" + j + ")       : " + matchResult.end(j));
      }

      Assert.assertEquals(expectedArr[i], matchResult.group(0));
    }
  }

  @Test
  public void extractJsonBlock() {
    String[] expectedArr = {"asdfasdfadsfafas", "zxcvdhd"};
    String data = "    \"\"\"\n" + "      This is not json\n" + "    \"\"\"";
    List<JsonBlock> jsonBlocks = RegexHelper.extractJsonBlock(data);
    for (int i = 0; i < jsonBlocks.size(); i++) {
      Assert.assertEquals(expectedArr[i], jsonBlocks.get(i).getDataWithoutQuotes());
    }
  }
}
