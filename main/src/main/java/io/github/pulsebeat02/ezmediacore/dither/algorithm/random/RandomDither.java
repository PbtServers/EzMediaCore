/*
 * MIT License
 *
 * Copyright (c) 2021 Brandon Li
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.pulsebeat02.ezmediacore.dither.algorithm.random;

import static io.github.pulsebeat02.ezmediacore.dither.load.DitherLookupUtil.COLOR_MAP;
import static io.github.pulsebeat02.ezmediacore.dither.load.DitherLookupUtil.FULL_COLOR_MAP;

import io.github.pulsebeat02.ezmediacore.callback.buffer.BufferCarrier;
import io.github.pulsebeat02.ezmediacore.dither.MapPalette;
import io.github.pulsebeat02.ezmediacore.dither.algorithm.NativelySupportedDitheringAlgorithm;
import io.github.pulsebeat02.ezmediacore.dither.buffer.ByteBufCarrier;
import io.github.pulsebeat02.ezmediacore.natives.DitherLibC;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.concurrent.ThreadLocalRandom;
import org.jetbrains.annotations.NotNull;

public final class RandomDither extends NativelySupportedDitheringAlgorithm {

  private static final ThreadLocalRandom THREAD_LOCAL_RANDOM;

  public static final int LIGHT_WEIGHT;
  public static final int NORMAL_WEIGHT;
  public static final int HEAVY_WEIGHT;

  static {
    THREAD_LOCAL_RANDOM = ThreadLocalRandom.current();
    LIGHT_WEIGHT = 32;
    NORMAL_WEIGHT = 64;
    HEAVY_WEIGHT = 128;
  }

  private final int weight;
  private final int min;
  private final int max;

  public RandomDither(final int weight, final boolean useNative) {
    super(useNative);
    this.weight = weight;
    this.min = -weight;
    this.max = weight + 1;
  }

  public RandomDither(final int weight) {
    this(weight, false);
  }

  @Override
  public @NotNull BufferCarrier standardMinecraftDither(
      final int @NotNull [] buffer, final int width) {
    final int length = buffer.length;
    final int height = length / width;
    final ByteBuf data = Unpooled.buffer(length);
    for (int y = 0; y < height; y++) {
      final int yIndex = y * width;
      for (int x = 0; x < width; x++) {
        final int index = yIndex + x;
        final int color = buffer[index];
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color) & 0xFF;
        r = (r += this.random()) > 255 ? 255 : r < 0 ? 0 : r;
        g = (g += this.random()) > 255 ? 255 : g < 0 ? 0 : g;
        b = (b += this.random()) > 255 ? 255 : b < 0 ? 0 : b;
        data.setByte(index, this.getBestColor(r, g, b));
      }
    }
    return ByteBufCarrier.ofByteBufCarrier(data);
  }

  @Override
  public void dither(final int @NotNull [] buffer, final int width) {
    final int height = buffer.length / width;
    for (int y = 0; y < height; y++) {
      final int yIndex = y * width;
      for (int x = 0; x < width; x++) {
        final int index = yIndex + x;
        final int color = buffer[index];
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color) & 0xFF;
        r = (r += this.random()) > 255 ? 255 : r < 0 ? 0 : r;
        g = (g += this.random()) > 255 ? 255 : g < 0 ? 0 : g;
        b = (b += this.random()) > 255 ? 255 : b < 0 ? 0 : b;
        buffer[index] = this.getBestColorNormal(r, g, b);
      }
    }
  }

  @Override
  public @NotNull BufferCarrier ditherIntoMinecraftNatively(
      final int @NotNull [] buffer, final int width) {
    return ByteBufCarrier.ofByteBufCarrier(
        Unpooled.wrappedBuffer(
            DitherLibC.INSTANCE
                .randomDither(FULL_COLOR_MAP, COLOR_MAP, buffer, width, this.weight)
                .getByteArray(0L, buffer.length)));
  }

  private int random() {
    return THREAD_LOCAL_RANDOM.nextInt(this.min, this.max);
  }

  private byte getBestColor(final int red, final int green, final int blue) {
    return COLOR_MAP[red >> 1 << 14 | green >> 1 << 7 | blue >> 1];
  }

  private int getBestColorNormal(final int r, final int g, final int b) {
    return MapPalette.getColor(this.getBestColor(r, g, b)).getRGB();
  }
}
