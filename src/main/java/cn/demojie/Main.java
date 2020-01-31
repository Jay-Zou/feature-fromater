package cn.demojie;

import cn.demojie.formater.FeatureJsonStringsFormater;
import java.io.File;

public class Main {

  public static void main(String[] args) {
    // File or directory
    if (args.length == 0) {
      System.err.println("Please specify a file or directory!");
      System.exit(0);
    }
    String path = args[0];
    File file = new File(path);

    format(file);
  }

  public static void format(File file) {
    // TODO Or can get all files first, then format them
    if (!file.exists()) {
      System.err.println("The file:" + file.getAbsolutePath() + " does not exist!");
      return;
    }
    if (file.isFile() && file.getName().endsWith(".feature")) {
      System.out.println("Format feature file: " + file.getName());
      FeatureJsonStringsFormater.formatAndWrite(file);
    } else if (file.isDirectory()) {
      File[] files = file.listFiles();
      for (File subFile : files) {
        format(subFile);
      }
    }
  }
}
