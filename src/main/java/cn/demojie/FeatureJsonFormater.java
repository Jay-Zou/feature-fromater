package cn.demojie;

import static java.nio.charset.StandardCharsets.UTF_8;

import cn.demojie.model.TripleQuotesBlock;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.MatchResult;

public class FeatureJsonFormater {

  public static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out, UTF_8));
  public static PrintWriter err = new PrintWriter(new OutputStreamWriter(System.err, UTF_8));

  public static final String TRIPLE_QUOTES = "\"\"\"";
  public static final int START_SPACE_COUNT = 4;

  // TODO or just read as a String instead of a List
  public static void formatAndWrite(File file) {
    // Get all lines
    List<String> fileAllLines = FileHelper.getFileContentAsListLines(file);

    // Extract the string block(""") contents and convert to json and format them
    List<TripleQuotesBlock> tripleQuotesBlocks = extractTripleQuotesBlock(fileAllLines);

    // Filter json block from TripleQuotesBlock
    List<TripleQuotesBlock> jsonBlocks = filterJsonTripleQuotesBlock(tripleQuotesBlocks);

    // Format them, notice that the parametric string
    formatJsonBlocks(jsonBlocks, START_SPACE_COUNT);

    // merge them into origin data, from end to start
    updateOriginLines(fileAllLines, jsonBlocks);

    // rewrite them to the file
    try {
      Files.write(file.toPath(), fileAllLines);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void formatJsonBlocks(List<TripleQuotesBlock> jsonBlocks, int startSpaceCount) {
    Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
    JsonParser jsonParser = new JsonParser();
    for (TripleQuotesBlock jsonBlock : jsonBlocks) {
      // Notice that the jsonBlock(List) is a subList of lines
      List<String> lines = jsonBlock.getLines();
      if (lines.isEmpty()) {
        continue;
      }

      // Pretty json
      StringBuilder stringBuilder = new StringBuilder();
      lines.forEach(stringBuilder::append);
      String json = stringBuilder.toString();
      // TODO Extract all params and put them back after format
      List<MatchResult> matchResults = RegexHelper.extractParams(RegexHelper.JSON_PARAMS_REGEX, json);

      out.println(json);
      JsonElement je = jsonParser.parse(json);
      String prettyJsonString = gson.toJson(je);

      String[] prettyLines = prettyJsonString.split("\\n");

      // Convert to List and add space prefix and add triple quotes
      List<String> prettyLineList = new ArrayList<>();
      String spacePrefix = generateSpace(startSpaceCount);
      prettyLineList.add(spacePrefix + TRIPLE_QUOTES);
      for (String prettyLine : prettyLines) {
        prettyLineList.add(spacePrefix + prettyLine);
      }
      prettyLineList.add(spacePrefix + TRIPLE_QUOTES);

      jsonBlock.setLines(prettyLineList);
    }
  }

  public static String generateSpace(int startSpaceCount) {
    char space = ' ';
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < startSpaceCount; i++) {
      stringBuilder.append(space);
    }
    return stringBuilder.toString();
  }

  private static List<TripleQuotesBlock> filterJsonTripleQuotesBlock(
      List<TripleQuotesBlock> tripleQuotesBlocks) {
    return tripleQuotesBlocks;
  }

  private static List<TripleQuotesBlock> extractTripleQuotesBlock(List<String> fileAllLines) {
    List<TripleQuotesBlock> jsonBlocks = new ArrayList<>();

    List<Integer> tripleQuotesLineIndex = new LinkedList<>();

    // Get all triple quotes indexs
    for (int i = 0; i < fileAllLines.size(); i++) {
      String oneLine = fileAllLines.get(i);
      if (!oneLine.trim().startsWith(TRIPLE_QUOTES)) {
        continue;
      }
      tripleQuotesLineIndex.add(i);
    }

    // Extract the triple quotes block contents
    for (int i = 0; i < tripleQuotesLineIndex.size(); i = i + 2) {
      int startLine = tripleQuotesLineIndex.get(i) + 1;
      int endLine = tripleQuotesLineIndex.get(i + 1);
      // Notice that the jsonBlock(List) is a subList of fileAllLines
      List<String> jsonBlock = fileAllLines.subList(startLine, endLine);

      TripleQuotesBlock tripleQuotesBlock = new TripleQuotesBlock();

      tripleQuotesBlock.setStartLine(startLine);
      tripleQuotesBlock.setEndLine(endLine);
      tripleQuotesBlock.setLines(jsonBlock);

      jsonBlocks.add(tripleQuotesBlock);
    }

    return jsonBlocks;
  }

  public static List<String> getTestJsonFromResources(String fileName) {
    try {
      URI uri = App.class.getClassLoader().getResource(fileName).toURI();
      return Files.readAllLines(Paths.get(uri));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }

  public static void main(String[] args) throws Exception {
    String dirName = "src/main/resources/";
    String fileName = "test.feature";

    // Get all lines
    List<String> fileAllLines = Files.readAllLines(Paths.get(dirName + fileName));

    // Extract the string block(""") contents and convert to json and format them
    List<TripleQuotesBlock> tripleQuotesBlocks = extractTripleQuotesBlock(fileAllLines);

    // Filter json block from TripleQuotesBlock
    List<TripleQuotesBlock> jsonBlocks = filterJsonTripleQuotesBlock(tripleQuotesBlocks);

    // Format them
    formatJsonBlocks(jsonBlocks, START_SPACE_COUNT);

    // merge them into origin data, from end to start
    updateOriginLines(fileAllLines, jsonBlocks);

    // rewrite them to the file

    Path path1 = Paths.get(dirName + "0-" + fileName);
    Files.write(path1, fileAllLines);
  }

  private static void updateOriginLines(
      List<String> fileAllLines, List<TripleQuotesBlock> jsonBlocks) {
    for (int i = jsonBlocks.size() - 1; i >= 0; i--) {
      TripleQuotesBlock tripleQuotesBlock = jsonBlocks.get(i);
      int startLine = tripleQuotesBlock.getStartLine();
      int endLine = tripleQuotesBlock.getEndLine();
      List<String> lines = tripleQuotesBlock.getLines();

      removeFromList(fileAllLines, startLine, endLine);

      fileAllLines.addAll(startLine - 1, lines);
    }
  }

  private static void removeFromList(List<String> fileAllLines, int startLine, int endLine) {
    // Remove contains the triple quotes
    for (int i = endLine; i >= startLine - 1; i--) {
      fileAllLines.remove(i);
    }
  }
}
