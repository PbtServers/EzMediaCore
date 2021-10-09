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
package io.github.pulsebeat02.deluxemediaplugin.command.video.output.audio;

import io.github.pulsebeat02.deluxemediaplugin.DeluxeMediaPlugin;
import io.github.pulsebeat02.deluxemediaplugin.command.video.VideoCommandAttributes;
import io.github.pulsebeat02.deluxemediaplugin.config.ServerInfo;
import io.github.pulsebeat02.deluxemediaplugin.executors.ExecutorProvider;
import io.github.pulsebeat02.ezmediacore.ffmpeg.FFmpegMediaStreamer;
import io.github.pulsebeat02.ezmediacore.utility.RequestUtils;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

public abstract class FFmpegOutput extends AudioOutput implements FFmpegOutputHandle {

  public FFmpegOutput(@NotNull final String name) {
    super(name);
  }

  @Override
  public void setAudioHandler(
      @NotNull final DeluxeMediaPlugin plugin,
      @NotNull final VideoCommandAttributes attributes,
      @NotNull final Audience audience,
      @NotNull final String mrl) {
  }

  @NotNull
  @Override
  public String openFFmpegStream(
      @NotNull final DeluxeMediaPlugin plugin, @NotNull final String mrl) {
    final ServerInfo info = plugin.getHttpAudioServer();
    final String ip = info.getIp();
    final int port = info.getPort();
    final FFmpegMediaStreamer streamer =
        new FFmpegMediaStreamer(
            plugin.library(),
            plugin.getAudioConfiguration(),
            RequestUtils.getAudioURLs(mrl).get(0),
            ip,
            port);
    plugin.getAttributes().setStreamExtractor(streamer);
    streamer.executeAsync(ExecutorProvider.STREAM_THREAD_EXECUTOR);
    return "http://%s:%s/live.stream".formatted(ip, port);
  }
}