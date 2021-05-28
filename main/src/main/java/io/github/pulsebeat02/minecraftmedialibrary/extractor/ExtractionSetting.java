/*............................................................................................
. Copyright © 2021 Brandon Li                                                               .
.                                                                                           .
. Permission is hereby granted, free of charge, to any person obtaining a copy of this      .
. software and associated documentation files (the “Software”), to deal in the Software     .
. without restriction, including without limitation the rights to use, copy, modify, merge, .
. publish, distribute, sublicense, and/or sell copies of the Software, and to permit        .
. persons to whom the Software is furnished to do so, subject to the following conditions:  .
.                                                                                           .
. The above copyright notice and this permission notice shall be included in all copies     .
. or substantial portions of the Software.                                                  .
.                                                                                           .
. THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND,                           .
.  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF                       .
.   MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND                                   .
.   NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS                     .
.   BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN                      .
.   ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN                       .
.   CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE                        .
.   SOFTWARE.                                                                               .
............................................................................................*/

package io.github.pulsebeat02.minecraftmedialibrary.extractor;

import com.google.common.base.Preconditions;
import io.github.pulsebeat02.minecraftmedialibrary.json.GsonHandler;

/**
 * A class full of audio properties to specify the properties of an audio file. It is used
 * eventually in JAVE2 while audio conversion is taking place. Strongly recommended to research each
 * of these properties first or else you might make someone deaf!
 */
public class ExtractionSetting implements ExtractionConfiguration {

  private final String codec;
  private final String outputFormat;
  private final String inputFormat;
  private int bitrate;
  private int channels;
  private int samplingRate;
  private int volume;

  /**
   * Instantiates a new ExtractionSetting.
   *
   * @param bitrate the bitrate
   * @param channels the channels
   * @param samplingRate the sampling rate
   * @param volume the volume
   */
  public ExtractionSetting(
      final int bitrate, final int channels, final int samplingRate, final int volume) {
    codec = "libvorbis";
    outputFormat = "ogg";
    inputFormat = "mp4";
    Preconditions.checkArgument(bitrate > 0, String.format("Invalid Bitrate! (%d)", bitrate));
    Preconditions.checkArgument(channels > 0, String.format("Invalid Channels! (%d)", channels));
    Preconditions.checkArgument(
        samplingRate > 0, String.format("Invalid Sampling Rate! (%d)", samplingRate));
    Preconditions.checkArgument(volume >= 0, String.format("Invalid Volume! (%d)", volume));
    this.bitrate = bitrate;
    this.channels = channels;
    this.samplingRate = samplingRate;
    this.volume = volume;
  }

  /**
   * Returns a new builder class to use.
   *
   * @return the builder
   */
  public static Builder builder() {
    return new Builder();
  }

  @Override
  public boolean equals(final Object obj) {
    if (!(obj instanceof ExtractionSetting)) {
      return false;
    }
    final ExtractionConfiguration setting = (ExtractionConfiguration) obj;
    return setting.getCodec().equals(codec)
        && setting.getOutputFormat().equals(outputFormat)
        && setting.getInputFormat().equals(inputFormat)
        && setting.getBitrate() == bitrate
        && setting.getChannels() == channels
        && setting.getSamplingRate() == samplingRate
        && setting.getVolume() == volume;
  }

  @Override
  public String toString() {
    return GsonHandler.getGson().toJson(this);
  }

  @Override
  public String getCodec() {
    return codec;
  }

  @Override
  public String getOutputFormat() {
    return outputFormat;
  }

  @Override
  public String getInputFormat() {
    return inputFormat;
  }

  @Override
  public int getBitrate() {
    return bitrate;
  }

  @Override
  public void setBitrate(final int bitrate) {
    this.bitrate = bitrate;
  }

  @Override
  public int getChannels() {
    return channels;
  }

  @Override
  public void setChannels(final int channels) {
    this.channels = channels;
  }

  @Override
  public int getSamplingRate() {
    return samplingRate;
  }

  @Override
  public void setSamplingRate(final int samplingRate) {
    this.samplingRate = samplingRate;
  }

  @Override
  public int getVolume() {
    return volume;
  }

  @Override
  public void setVolume(final int volume) {
    this.volume = volume;
  }

  /** The type Builder. */
  public static class Builder {

    private int bitrate = 160000;
    private int channels = 2;
    private int samplingRate = 44100;
    private int volume = 48;

    private Builder() {}

    /**
     * Sets bitrate.
     *
     * @param bitrate the bitrate
     * @return the bitrate
     */
    public Builder setBitrate(final int bitrate) {
      this.bitrate = bitrate;
      return this;
    }

    /**
     * Sets channels.
     *
     * @param channels the channels
     * @return the channels
     */
    public Builder setChannels(final int channels) {
      this.channels = channels;
      return this;
    }

    /**
     * Sets sampling rate.
     *
     * @param samplingRate the sampling rate
     * @return the sampling rate
     */
    public Builder setSamplingRate(final int samplingRate) {
      this.samplingRate = samplingRate;
      return this;
    }

    /**
     * Sets volume.
     *
     * @param volume the volume
     * @return the volume
     */
    public Builder setVolume(final int volume) {
      this.volume = volume;
      return this;
    }

    /**
     * Creates ExtractionSetting from Builder
     *
     * @return the extraction setting
     */
    public ExtractionConfiguration build() {
      return new ExtractionSetting(bitrate, channels, samplingRate, volume);
    }
  }
}