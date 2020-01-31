package cn.demojie.helper;

import cn.demojie.model.JsonLikeBlock;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class RegexHelperTest {

  @Test
  public void extractJsonLikeBlockNotJson() {
    String[] expectedArr = {"\nasdfasdfadsfafa\ns", "zxcvdhd"};
    String data = "4537h\nh\"\"\"\nasdfasdfadsfafa\ns\"\"\"123412\"\"\"zxcvdhd\"\"\"m98";

    List<JsonLikeBlock> jsonLikeBlocks = RegexHelper.extractJsonLikeBlock(data);

    for (int i = 0; i < jsonLikeBlocks.size(); i++) {
      JsonLikeBlock jsonLikeBlock = jsonLikeBlocks.get(i);
      Assert.assertEquals(expectedArr[i], jsonLikeBlock.getDataWithoutQuotes());
    }
  }

  @Test
  public void extractJsonLikeBlock() {
    String[] expectedArr = {"{\"name\":\"jay\", \"age\": 123}", "{\"name\":\"zou\", \"age\": 234}"};
    String data =
        "\"\"\"{\"name\":\"jay\", \"age\": 123}\"\"\"\n\"\"\"{\"name\":\"zou\", \"age\": 234}\"\"\"";

    List<JsonLikeBlock> jsonLikeBlocks = RegexHelper.extractJsonLikeBlock(data);

    for (int i = 0; i < jsonLikeBlocks.size(); i++) {
      JsonLikeBlock jsonLikeBlock = jsonLikeBlocks.get(i);
      Assert.assertEquals(expectedArr[i], jsonLikeBlock.getDataWithoutQuotes());
    }
  }

  @Test
  public void extractJsonLikeBlockWithParams() {
    String[] expectedArr = {"{\"name\":\"<name>\", \"age\": 123}", "{\"name\":\"zou\", \"age\": <age>}"};
    String data =
        "\"\"\"{\"name\":\"<name>\", \"age\": 123}\"\"\"\n\"\"\"{\"name\":\"zou\", \"age\": <age>}\"\"\"";

    List<JsonLikeBlock> jsonLikeBlocks = RegexHelper.extractJsonLikeBlock(data);
    for (int i = 0; i < jsonLikeBlocks.size(); i++) {
      Assert.assertEquals(expectedArr[i], jsonLikeBlocks.get(i).getDataWithoutQuotes());
    }
  }

  @Test
  public void extractAndReplaceParams() {
    String[] expectedArr = {"\"<name>\"", "<age>"};

    String expectedData = "{\"name\":\"TEMP_REPLACE\", \"age\": \"TEMP_REPLACE\"}";
    String data = "{\"name\":\"<name>\", \"age\": <age>}";
    JsonLikeBlock jsonLikeBlock = new JsonLikeBlock();
    jsonLikeBlock.setDataWithoutQuotes(data);

    RegexHelper.extractAndReplaceParams(jsonLikeBlock);

    Assert.assertArrayEquals(expectedArr, jsonLikeBlock.getParams().toArray());
    Assert.assertEquals(expectedData, jsonLikeBlock.getDataWithoutQuotes());
  }
}
