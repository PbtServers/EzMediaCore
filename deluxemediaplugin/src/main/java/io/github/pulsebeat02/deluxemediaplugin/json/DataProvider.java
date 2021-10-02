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
package io.github.pulsebeat02.deluxemediaplugin.json;

import io.github.pulsebeat02.deluxemediaplugin.DeluxeMediaPlugin;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class DataProvider<T> implements DataHolder<T> {

  private final DeluxeMediaPlugin plugin;
  private final String name;
  private final Path path;
  private final Class<T> clazz;
  private T object;

  public DataProvider(
      @NotNull final DeluxeMediaPlugin plugin,
      @NotNull final Class<T> clazz,
      @NotNull final String name) {
    this.plugin = plugin;
    this.name = name;
    this.path = plugin.getBootstrap().getDataFolder().toPath().resolve(this.name).toAbsolutePath();
    this.clazz = clazz;
  }

  @Override
  public void deserialize(@NotNull final T obj) throws IOException {
    try (final BufferedWriter writer = Files.newBufferedWriter(this.path)) {
      GsonProvider.getGson().toJson(obj, writer);
    }
  }

  @Override
  public void serialize() throws IOException {
    this.saveConfig();
    try (final BufferedReader reader = Files.newBufferedReader(this.path.toAbsolutePath())) {
      this.object = GsonProvider.getGson().fromJson(reader, this.clazz);
    }
  }

  private void saveConfig() throws IOException {
    if (!Files.exists(this.path)) {
      Files.copy(
          this.plugin.getBootstrap().getResource(this.name),
          this.path,
          StandardCopyOption.REPLACE_EXISTING);
    }
  }

  @Override
  public @Nullable T getSerializedValue() {
    return this.object;
  }

  @Override
  public @NotNull DeluxeMediaPlugin getPlugin() {
    return this.plugin;
  }

  @Override
  public @NotNull String getFileName() {
    return this.name;
  }

  @Override
  public @NotNull Path getConfigFile() {
    return this.path;
  }
}
