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
package io.github.pulsebeat02.ezmediacore.player.input.implementation;

import static com.google.common.base.Preconditions.checkNotNull;

import io.github.pulsebeat02.ezmediacore.player.input.Input;
import java.io.File;
import java.nio.file.Path;
import java.util.Objects;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class MrlInput implements Input {

  private static final Input EMPTY_MRL;

  static {
    EMPTY_MRL = ofMrl("");
  }

  private final String mrl;

  MrlInput(@NotNull final String mrl) {
    checkNotNull(mrl, "MRL specified cannot be null!");
    this.mrl = mrl;
  }

  @Contract(value = "_ -> new", pure = true)
  public static @NotNull Input ofMrl(@NotNull final String mrl) {
    return new MrlInput(mrl);
  }

  @Contract(value = "_ -> new", pure = true)
  public static @NotNull Input ofMrl(@NotNull final Path path) {
    return ofMrl(path.toString());
  }

  @Contract(value = "_ -> new", pure = true)
  public static @NotNull Input ofMrl(@NotNull final File path) {
    return ofMrl(path.getPath());
  }

  public static @NotNull Input emptyMrl() {
    return EMPTY_MRL;
  }

  @Override
  public @NotNull String getInput() {
    return this.mrl;
  }

  @Override
  public void setupInput() {
  }

  @Contract(pure = true)
  @Override
  public @NotNull String toString() {
    return "{mrl=%s}".formatted(this.mrl);
  }

  @Override
  public boolean equals(final Object obj) {
    if (!(obj instanceof MrlInput)) {
      return false;
    }
    return ((MrlInput) obj).mrl.equals(this.mrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.mrl);
  }
}
