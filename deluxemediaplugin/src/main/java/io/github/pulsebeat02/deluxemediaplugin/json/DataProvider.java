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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class DataProvider<T> {

  private final DeluxeMediaPlugin plugin;
  private final String name;
  private final Path path;
  private T object;

  public DataProvider(@NotNull final DeluxeMediaPlugin plugin, @NotNull final String name)
      throws IOException {
    this.plugin = plugin;
    this.name = name;
    this.path = plugin.getBootstrap().getDataFolder().toPath().resolve(this.name);
  }

  public void deserialize() throws IOException {
    GsonProvider.getGson().toJson(this.object, Files.newBufferedWriter(this.path));
  }

  public void serialize() throws IOException {
    if (!Files.exists(this.path)) {
      this.plugin.getBootstrap().saveResource(this.name, false);
    }
    this.object =
        (T)
            GsonProvider.getGson()
                .fromJson(Files.newBufferedReader(this.path), this.object.getClass());
  }

  public @Nullable T getSerializedValue() {
    return this.object;
  }

  public @NotNull DeluxeMediaPlugin getPlugin() {
    return this.plugin;
  }

  public @NotNull String getFileName() {
    return this.name;
  }

  public @NotNull Path getConfigFile() {
    return this.path;
  }
}
