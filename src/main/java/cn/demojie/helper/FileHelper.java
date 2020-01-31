package cn.demojie.helper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {

  /**
   * Get all contents as a {@link String} from a file.
   *
   * @param file just support a file
   * @return contents of the file, or empty string if occur exception
   */
  public static String getFileContent(File file) {
    byte[] bytes = {};
    try {
      bytes = Files.readAllBytes(file.toPath());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new String(bytes, StandardCharsets.UTF_8);
  }

  /**
   * Get all contents as a {@link List} contains each lines of the file.
   *
   * @param file just support a file
   * @return
   */
  public static List<String> getFileContentAsListLines(File file) {
    try {
      return Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }
}
