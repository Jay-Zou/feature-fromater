package cn.demojie.model;

import java.util.List;

public class TripleQuotesBlock {

  /** Do not contains the TripleQuotes line */
  int startLine;

  /** Do contains the TripleQuotes line */
  int endLine;

  List<String> lines;

  public int getStartLine() {
    return startLine;
  }

  public void setStartLine(int startLine) {
    this.startLine = startLine;
  }

  public int getEndLine() {
    return endLine;
  }

  public void setEndLine(int endLine) {
    this.endLine = endLine;
  }

  public List<String> getLines() {
    return lines;
  }

  public void setLines(List<String> lines) {
    this.lines = lines;
  }
}
