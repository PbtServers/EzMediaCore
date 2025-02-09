package io.github.pulsebeat02.deluxemediaplugin.command.video.load.wrapper;

import io.github.pulsebeat02.deluxemediaplugin.DeluxeMediaPlugin;
import io.github.pulsebeat02.deluxemediaplugin.command.video.ScreenConfig;
import io.github.pulsebeat02.ezmediacore.extraction.AudioConfiguration;
import io.github.pulsebeat02.ezmediacore.ffmpeg.OGGAudioExtractor;
import io.github.pulsebeat02.ezmediacore.player.input.implementation.MrlInput;
import io.github.pulsebeat02.ezmediacore.resourcepack.PackFormat;
import io.github.pulsebeat02.ezmediacore.resourcepack.ResourcepackSoundWrapper;
import io.github.pulsebeat02.ezmediacore.resourcepack.hosting.HttpServer;
import io.github.pulsebeat02.ezmediacore.utility.io.HashingUtils;
import java.io.IOException;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;

public final class SimpleResourcepackWrapper {

  private final DeluxeMediaPlugin plugin;
  private final ScreenConfig config;
  private final Path download;
  private final Path ogg;

  public SimpleResourcepackWrapper(
      @NotNull final DeluxeMediaPlugin plugin,
      @NotNull final ScreenConfig config,
      @NotNull final Path download,
      @NotNull final Path videoFolder) {
    this.plugin = plugin;
    this.config = config;
    this.download = download;
    this.ogg = videoFolder.resolve(".output-ogg.tmp");
  }

  public void loadResourcepack() throws IOException {
    this.executeFFmpegExtractor();
    this.setResourcepack(this.wrapResourcepack());
  }

  private void executeFFmpegExtractor() {

    final AudioConfiguration configuration = this.plugin.getAudioConfiguration();
    final OGGAudioExtractor extractor =
        OGGAudioExtractor.ofFFmpegAudioExtractor(
            this.plugin.library(), configuration, this.download, this.ogg);

    this.config.setExtractor(extractor);

    extractor.execute();
  }

  private void setResourcepack(@NotNull final ResourcepackSoundWrapper wrapper) {

    final Path path = wrapper.getResourcepackFilePath();
    final String url = this.plugin.getHttpServer().createUrl(path);
    final byte[] hash = HashingUtils.createHashSha1(path);

    this.config.setOgg(MrlInput.ofMrl(this.ogg));
    this.config.setPackUrl(url);
    this.config.setPackHash(hash);
  }

  private @NotNull ResourcepackSoundWrapper wrapResourcepack() throws IOException {

    final HttpServer daemon = this.startDaemon();
    final String sound = "deluxemediaplugin";
    final Path target = daemon.getDaemon().getServerPath().resolve("pack.zip");
    final int id = PackFormat.getCurrentFormat().getId();

    final ResourcepackSoundWrapper wrapper =
        ResourcepackSoundWrapper.ofSoundPack(target, "Auto-Generated Audio Pack", id);
    wrapper.addSound(sound, this.ogg);
    wrapper.wrap();

    return wrapper;
  }

  private @NotNull HttpServer startDaemon() {
    final HttpServer daemon = this.plugin.getHttpServer();
    if (!daemon.isRunning()) {
      daemon.startServer();
    }
    return daemon;
  }
}
