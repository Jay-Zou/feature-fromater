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

public class FeatureJsonFormater2 {

  public static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out, UTF_8));
  public static PrintWriter err = new PrintWriter(new OutputStreamWriter(System.err, UTF_8));

  public static final String TRIPLE_QUOTES = "\"\"\"";
  public static final int START_SPACE_COUNT = 4;

  // TODO or just read as a String instead of a List
  public static void formatAndWrite(File file) {
    /**
     * Steps:
     * 1. read file as string
     * 2. extract """json""" as List<String>, but not change the origin content
     * 3. format them and extract/restore param
     * 3. replace """content in the fileString with formatted json
     */
    // Get file content
    String fileContent = FileHelper.getFileContent(file);

    // Extract the string block(""") contents and convert to json and format them

    // Filter json block from TripleQuotesBlock

    // Format them, notice that the parametric string

    // merge them into origin data, from end to start

    // rewrite them to the file

  }

}
