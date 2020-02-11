package cn.demojie.helper;

public class LogHelper {

  public static final String INFO = "[INFO] ";
  public static final String WARN = "[WARN] ";
  public static final String ERROR = "[ERROR] ";

  public static void info(String msg) {
    System.out.println(INFO + msg);
  }

  public static void warn(String msg) {
    System.out.println(WARN + msg);
  }

  public static void error(String msg) {
    System.out.println(ERROR + msg);
  }
}
