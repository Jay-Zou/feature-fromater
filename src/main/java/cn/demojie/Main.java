package cn.demojie;

import cn.demojie.formatter.FeatureJsonStringsFormatter;
import cn.demojie.helper.LogHelper;
import java.io.File;

public class Main {
  public static final String FEATURE_SUFFIX = ".feature";

  public static void main(String[] args) {
    // File or directory
    if (args.length == 0) {
      LogHelper.error("Please specify a file or directory!");
      System.exit(0);
    }
    String path = args[0];
    File file = new File(path);

    format(file);
  }

  public static void format(File file) {
    // TODO Or can get all files first, then format them
    if (!file.exists()) {
      LogHelper.error("The file:" + file.getAbsolutePath() + " does not exist!");
      return;
    }
    if (file.isFile() && file.getName().endsWith(FEATURE_SUFFIX)) {
      LogHelper.info("Format feature file: " + file.getName());
      FeatureJsonStringsFormatter.formatAndWrite(file);
    } else if (file.isDirectory()) {
      File[] files = file.listFiles();
      for (File subFile : files) {
        format(subFile);
      }
    }
  }
}
