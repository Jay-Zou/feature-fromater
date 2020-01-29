package cn.demojie.formater;

import static java.nio.charset.StandardCharsets.UTF_8;

import cn.demojie.helper.CommonHelper;
import cn.demojie.helper.FileHelper;
import cn.demojie.helper.JsonHelper;
import cn.demojie.helper.RegexHelper;
import cn.demojie.model.JsonBlock;
import com.google.common.base.Strings;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;

public class FeatureJsonStringsFormater {

  public static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out, UTF_8));
  public static PrintWriter err = new PrintWriter(new OutputStreamWriter(System.err, UTF_8));

  public static final String TRIPLE_QUOTES = "\"\"\"";
  public static final int START_SPACE_COUNT = 4;
  public static final String NEW_LINE = "\n";

  public static void formatAndWrite(File file) {
    formatAndWrite(file, file);
  }

  public static void formatAndWrite(File fromFile, File toFile) {
    /**
     * TODO if the json is shorter than a specify value, compressed it and can configurable the
     * compressed or pretty json pattern
     *
     * <pre>
     * Steps:
     * 1. read file as string
     * 2. extract """json""" as List<String>, but not change the origin content
     * 3. format them and extract/restore param
     * 4. replace """ content in the fileString with formatted json
     * </pre>
     */
    // Get file content
    String fileContent = FileHelper.getFileContent(fromFile);

    // Extract the string block(""") contents and convert to json and format them
    List<JsonBlock> jsonBlocks = RegexHelper.extractJsonBlock(fileContent);

    // Filter json block from TripleQuotesBlock

    // Format them, notice that the parametric string
    processJsonFormat(jsonBlocks);

    // merge them into origin data, from end to start
    fileContent = mergeBack(fileContent, jsonBlocks);
    // rewrite them to the file
    // rewrite them to the file

    try {
      Files.write(toFile.toPath(), fileContent.getBytes(UTF_8));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static String mergeBack(String fileContent, List<JsonBlock> jsonBlocks) {
    StringBuilder stringBuilder = new StringBuilder(fileContent);
    // Replace from back to front
    for (int i = jsonBlocks.size() - 1; i >= 0; i--) {
      JsonBlock jsonBlock = jsonBlocks.get(i);
      if (!jsonBlock.isValid()) {
        continue;
      }
      int startIndex = jsonBlock.getStartIndex();
      int endIndex = jsonBlock.getEndIndex();
      String dataWithoutQuotes = jsonBlock.getDataWithoutQuotes();
      // Replace
      stringBuilder.replace(startIndex, endIndex, dataWithoutQuotes);
    }
    return stringBuilder.toString();
  }

  private static void processJsonFormat(List<JsonBlock> jsonBlocks) {
    for (JsonBlock jsonBlock : jsonBlocks) {
      RegexHelper.extractAndReplaceParams(jsonBlock);
      String formatted = JsonHelper.format(jsonBlock.getDataWithoutQuotes());
      if (!Strings.isNullOrEmpty(formatted)) {
        jsonBlock.setDataWithoutQuotes(formatted);
        jsonBlock.setValid(true);
      }
      RegexHelper.restoreParams(jsonBlock);
      // align or indent
      indentJson(jsonBlock);
    }
  }

  private static void indentJson(JsonBlock jsonBlock) {
    if (!jsonBlock.isValid()) {
      return;
    }
    /** TODO : Notice: 1. If """ isn't align with json 2. If the line before """ is blank line */
    String dataWithoutQuotes = jsonBlock.getDataWithoutQuotes();
    StringBuilder stringBuilder = new StringBuilder(dataWithoutQuotes);
    String spaceStr = CommonHelper.generateSpace(START_SPACE_COUNT);
    stringBuilder
        .insert(0, NEW_LINE)
        .insert(0, TRIPLE_QUOTES)
        .append(NEW_LINE)
        .append(TRIPLE_QUOTES);
    CommonHelper.replaceAll(stringBuilder, NEW_LINE, NEW_LINE + spaceStr);
    jsonBlock.setDataWithoutQuotes(stringBuilder.toString());
  }
}
