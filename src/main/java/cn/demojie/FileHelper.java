package cn.demojie;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {

  public static String getFileContent(File file) {
    byte[] bytes = {};
    try {
      bytes = Files.readAllBytes(file.toPath());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new String(bytes, StandardCharsets.UTF_8);
  }

  public static List<String> getFileContentAsListLines(File file) {
    try {
      return Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }

  public static void main(String[] args) {
    byte[] bytes = {};
    String s = new String(bytes, StandardCharsets.UTF_8);
    System.out.println(s);
  }
}
