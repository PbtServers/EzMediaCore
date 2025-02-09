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
package io.github.pulsebeat02.ezmediacore.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import io.github.pulsebeat02.ezmediacore.json.adapter.PathAdapter;
import io.github.pulsebeat02.ezmediacore.json.adapter.UUIDAdapter;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public final class GsonProvider {

  private static final Gson SIMPLE;
  private static final Gson PRETTY;

  static {
    final GsonBuilder builder = getBuilder();
    SIMPLE = builder.create();
    PRETTY = builder.setPrettyPrinting().create();
  }

  private static @NotNull GsonBuilder getBuilder() {
    final GsonBuilder builder = new GsonBuilder();
    getAdapters().forEach(builder::registerTypeAdapter);
    return builder;
  }

  @Contract(" -> new")
  private static @NotNull @Unmodifiable Map<Class<?>, TypeAdapter<?>> getAdapters() {
    return Map.of(
        Path.class, new PathAdapter(),
        UUID.class, new UUIDAdapter());
  }

  public static Gson getSimple() {
    return SIMPLE;
  }

  public static Gson getPretty() {
    return PRETTY;
  }
}
