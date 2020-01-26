package cn.demojie;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/** Hello world! */
public class App {
  public static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out, UTF_8));
  public static PrintWriter err = new PrintWriter(new OutputStreamWriter(System.err, UTF_8));

  public static void main(String[] args) {
    out.println("Start......");
    // File or directory
    String path = "src/main/resources/features";
    File file = new File(path);

    format(file);
  }

  public static void format(File file) {
    if (!file.exists()) {
      err.println("The file:" + file.getAbsolutePath() + " does not exist!");
      return;
    }
    if (file.isFile() && file.getName().endsWith(".feature")) {
      System.out.println("Format feature file: " + file.getName());
      FeatureJsonFormater.formatAndWrite(file);
    } else if (file.isDirectory()) {
      File[] files = file.listFiles();
      for (File subFile : files) {
        format(subFile);
      }
    }
  }
}
