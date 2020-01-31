package cn.demojie.model;

import java.util.List;

public class JsonLikeBlock {

  public JsonLikeBlock() {}

  public JsonLikeBlock(int startIndex, int endIndex, String dataWithoutQuotes) {
    this.startIndex = startIndex;
    this.endIndex = endIndex;
    this.dataWithoutQuotes = dataWithoutQuotes;
  }

  private int startIndex;
  private int endIndex;
  private String dataWithoutQuotes;

  private List<String> params;
  private boolean valid = false;

  public int getStartIndex() {
    return startIndex;
  }

  public void setStartIndex(int startIndex) {
    this.startIndex = startIndex;
  }

  public int getEndIndex() {
    return endIndex;
  }

  public void setEndIndex(int endIndex) {
    this.endIndex = endIndex;
  }

  public String getDataWithoutQuotes() {
    return dataWithoutQuotes;
  }

  public void setDataWithoutQuotes(String dataWithoutQuotes) {
    this.dataWithoutQuotes = dataWithoutQuotes;
  }

  public List<String> getParams() {
    return params;
  }

  public void setParams(List<String> params) {
    this.params = params;
  }

  public boolean isValid() {
    return valid;
  }

  public void setValid(boolean valid) {
    this.valid = valid;
  }
}
