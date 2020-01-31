package cn.demojie;

import org.junit.Test;

public class MainTest {
  @Test
  public void mainFileTest() {
    String file = "src/test/resources/features/json-format-test.feature";
    String[] args = {file};
    Main.main(args);
  }

  @Test
  public void mainDirectoryTest() {
    String file = "src/test/resources/features/";
    String[] args = {file};
    Main.main(args);
  }
}
