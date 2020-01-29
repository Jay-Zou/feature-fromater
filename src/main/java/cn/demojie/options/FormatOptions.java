package cn.demojie.options;

import com.google.errorprone.annotations.Immutable;

@Immutable
public class FormatOptions {

  private final int blockIndent;
  private final int jsonIndent;
  private final int prettifyThreshold;

  private FormatOptions(int blockIndent, int jsonIndent, int prettifyThreshold) {
    this.blockIndent = blockIndent;
    this.jsonIndent = jsonIndent;
    this.prettifyThreshold = prettifyThreshold;
  }

  /** Returns the indent of the block. */
  public int getBlockIndent() {
    return blockIndent;
  }

  /** Returns the indent used for json formatting (when prettifyThreshold > 0). */
  public int getJsonIndent() {
    return jsonIndent;
  }

  /**
   * Returns the prettifyThreshold, when the json's length longer than it's value, the json will be
   * formatted prettily.
   */
  public int getPrettifyThreshold() {
    return prettifyThreshold;
  }

  /** Returns the default formatting options. */
  public static FormatOptions defaultOptions() {
    return builder().build();
  }

  /** Returns a builder for {@link FormatOptions}. */
  public static Builder builder() {
    return new Builder();
  }

  /** A builder for {@link FormatOptions}. */
  public static class Builder {

    private int blockIndent = 4;
    private int jsonIndent = 2;
    private int prettifyThreshold = 0;

    private Builder() {}

    public Builder blockIndent(int blockIndent) {
      this.blockIndent = blockIndent;
      return this;
    }

    public Builder jsonIndent(int jsonIndent) {
      this.jsonIndent = jsonIndent;
      return this;
    }

    public Builder prettifyThreshold(int prettifyThreshold) {
      this.prettifyThreshold = prettifyThreshold;
      return this;
    }

    public FormatOptions build() {
      return new FormatOptions(blockIndent, jsonIndent, prettifyThreshold);
    }
  }
}
