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
package io.github.pulsebeat02.ezmediacore.callback.entity;

import static com.google.common.base.Preconditions.checkNotNull;

import org.jetbrains.annotations.NotNull;

public final class NamedEntityString {

  public static final NamedEntityString DASH;
  public static final NamedEntityString HYPHEN;
  public static final NamedEntityString PERIOD;

  public static final NamedEntityString TINY_SQUARE;
  public static final NamedEntityString NORMAL_SQUARE;
  public static final NamedEntityString VERTICAL_RECTANGLE;
  public static final NamedEntityString HORIZONTAL_RECTANGLE;

  static {
    DASH = ofString("-");
    HYPHEN = ofString("—");
    PERIOD = ofString(".");

    TINY_SQUARE = ofString("■");
    NORMAL_SQUARE = ofString("■");
    VERTICAL_RECTANGLE = ofString("█");
    HORIZONTAL_RECTANGLE = ofString("▬");
  }

  private final String name;

  NamedEntityString(@NotNull final String name) {
    checkNotNull(name, "NamedEntityString cannot be null!");
    this.name = name;
  }

  public static @NotNull NamedEntityString ofString(@NotNull final String name) {
    return new NamedEntityString(name);
  }

  public @NotNull String getName() {
    return this.name;
  }
}
