/*.........................................................................................
. Copyright © 2021 Brandon Li
.                                                                                        .
. Permission is hereby granted, free of charge, to any person obtaining a copy of this
. software and associated documentation files (the “Software”), to deal in the Software
. without restriction, including without limitation the rights to use, copy, modify, merge,
. publish, distribute, sublicense, and/or sell copies of the Software, and to permit
. persons to whom the Software is furnished to do so, subject to the following conditions:
.
. The above copyright notice and this permission notice shall be included in all copies
. or substantial portions of the Software.
.
. THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND,
. EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
. MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
. NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
. BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
. ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
. CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
.  SOFTWARE.
.                                                                                        .
.........................................................................................*/

package io.github.pulsebeat02.deluxemediaplugin.command.video;

import io.github.pulsebeat02.ezmediacore.MediaLibraryCore;
import io.github.pulsebeat02.ezmediacore.callback.BlockHighlightCallback;
import io.github.pulsebeat02.ezmediacore.callback.ChatCallback;
import io.github.pulsebeat02.ezmediacore.callback.EntityCallback;
import io.github.pulsebeat02.ezmediacore.callback.EntityType;
import io.github.pulsebeat02.ezmediacore.callback.MapCallback;
import io.github.pulsebeat02.ezmediacore.callback.ScoreboardCallback;
import io.github.pulsebeat02.ezmediacore.dither.algorithm.FilterLiteDither;
import io.github.pulsebeat02.ezmediacore.player.VideoFactory;
import io.github.pulsebeat02.ezmediacore.player.VideoPlayer;
import io.github.pulsebeat02.ezmediacore.utility.ImmutableDimension;
import java.util.Collection;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class VideoCreator {

  private final MediaLibraryCore library;
  private final VideoCommandAttributes attributes;

  public VideoCreator(
      @NotNull final MediaLibraryCore library, @NotNull final VideoCommandAttributes attributes) {
    this.library = library;
    this.attributes = attributes;
  }

  public VideoPlayer createMapPlayer(@NotNull final Collection<? extends Player> viewers) {
    return VideoFactory.builder()
        .core(this.library)
        .url(this.attributes.getMrl())
        .dims(
            new ImmutableDimension(
                this.attributes.getPixelWidth(), this.attributes.getPixelHeight()))
        .callback(
            new MapCallback(
                this.library,
                this.getUniqueIDs(viewers),
                new FilterLiteDither(),
                this.attributes.getMap(),
                new ImmutableDimension(
                    this.attributes.getFrameWidth(), this.attributes.getFrameHeight()),
                this.attributes.getPixelWidth(),
                0))
        .build();
  }

  private UUID[] getUniqueIDs(@NotNull final Collection<? extends Player> viewers) {
    return viewers.stream().map(Player::getUniqueId).toArray(UUID[]::new);
  }

  public VideoPlayer createEntityPlayer(
      @NotNull final Player sender, @NotNull final Collection<? extends Player> viewers) {
    return VideoFactory.builder()
        .core(this.library)
        .url(this.attributes.getMrl())
        .dims(
            new ImmutableDimension(
                this.attributes.getPixelWidth(), this.attributes.getPixelHeight()))
        .callback(
            new EntityCallback(
                this.library,
                this.getUniqueIDs(viewers),
                sender.getLocation(),
                "▉",
                EntityType.ARMORSTAND,
                new ImmutableDimension(
                    this.attributes.getPixelWidth(), this.attributes.getPixelHeight()),
                this.attributes.getPixelWidth(),
                20))
        .build();
  }

  public VideoPlayer createChatBoxPlayer(@NotNull final Collection<? extends Player> viewers) {
    return VideoFactory.builder()
        .core(this.library)
        .url(this.attributes.getMrl())
        .dims(
            new ImmutableDimension(
                this.attributes.getPixelWidth(), this.attributes.getPixelHeight()))
        .callback(
            new ChatCallback(
                this.library,
                this.getUniqueIDs(viewers),
                "#",
                new ImmutableDimension(
                    this.attributes.getPixelWidth(), this.attributes.getPixelHeight()),
                this.attributes.getPixelWidth(),
                20))
        .build();
  }

  public VideoPlayer createScoreboardPlayer(@NotNull final Collection<? extends Player> viewers) {
    return VideoFactory.builder()
        .core(this.library)
        .url(this.attributes.getMrl())
        .dims(
            new ImmutableDimension(
                this.attributes.getPixelWidth(), this.attributes.getPixelHeight()))
        .callback(
            new ScoreboardCallback(
                this.library,
                this.getUniqueIDs(viewers),
                1080,
                new ImmutableDimension(
                    this.attributes.getPixelWidth(), this.attributes.getPixelHeight()),
                this.attributes.getPixelWidth(),
                20))
        .build();
  }

  public VideoPlayer createBlockHighlightPlayer(
      @NotNull final Player sender, @NotNull final Collection<? extends Player> viewers) {
    return VideoFactory.builder()
        .core(this.library)
        .url(this.attributes.getMrl())
        .dims(
            new ImmutableDimension(
                this.attributes.getPixelWidth(), this.attributes.getPixelHeight()))
        .callback(
            new BlockHighlightCallback(
                this.library,
                this.getUniqueIDs(viewers),
                sender.getLocation(),
                new ImmutableDimension(
                    this.attributes.getPixelWidth(), this.attributes.getPixelHeight()),
                this.attributes.getPixelWidth(),
                40))
        .build();
  }
}
