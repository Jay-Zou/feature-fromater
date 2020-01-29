package cn.demojie.formater;

import cn.demojie.formater.FeatureJsonStringsFormater;
import java.io.File;
import org.junit.Test;

public class FeatureJsonStringsFormaterTest {

  @Test
  public void formatAndWrite() {
    File fromFile = new File("src/test/resources/features/test2.feature");
    File toFile = new File("src/test/resources/features/test1.feature");
    FeatureJsonStringsFormater.formatAndWrite(fromFile, toFile);
    System.out.println();
  }
}