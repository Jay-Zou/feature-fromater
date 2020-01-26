package cn.demojie;

import java.util.List;
import java.util.regex.MatchResult;
import org.junit.Assert;
import org.junit.Test;

public class RegexHelperTest {

  @Test
  public void extractParams1() {
    String[] expectedArr = {"<12r12asdfasdf>", "<675865n>"};
    String data = "123r6546<12r12asdfasdf>67<675865n>i";
    List<MatchResult> matchResults = RegexHelper.extractParams(RegexHelper.JSON_PARAMS_REGEX, data);

    Assert.assertNotNull(matchResults);
    Assert.assertFalse(matchResults.isEmpty());

    for (int i = 0; i < matchResults.size(); i++) {
      MatchResult matchResult = matchResults.get(i);

      System.out.println("=================================================");
      System.out.println("groupCount: " + matchResult.groupCount());
      System.out.println("group     : " + matchResult.group());
      System.out.println("group(0)  : " + matchResult.group(0));
      System.out.println("start     : " + matchResult.start());
      System.out.println("end       : " + matchResult.end());

      Assert.assertEquals(expectedArr[i], matchResult.group(0));
    }
  }

  @Test
  public void extractParams2() {
    String[] expectedArr = {"\"\"\"asdfasdfadsfafas\"\"\"", "\"\"\"zxcvdhd\"\"\""};
    String data = "4537hh\"\"\"asdfasdfadsfafas\"\"\"123412\"\"\"zxcvdhd\"\"\"m98";
    List<MatchResult> matchResults = RegexHelper.extractParams(RegexHelper.JSON_TRIPLE_QUOTES_REGEX, data);

    Assert.assertNotNull(matchResults);
    Assert.assertFalse(matchResults.isEmpty());

    for (int i = 0; i < matchResults.size(); i++) {
      MatchResult matchResult = matchResults.get(i);

      System.out.println("=================================================");
      System.out.println("groupCount: " + matchResult.groupCount());
      System.out.println("group     : " + matchResult.group());
      System.out.println("group(0)  : " + matchResult.group(0));
      System.out.println("start     : " + matchResult.start());
      System.out.println("end       : " + matchResult.end());

      Assert.assertEquals(expectedArr[i], matchResult.group(0));
    }
  }


}
