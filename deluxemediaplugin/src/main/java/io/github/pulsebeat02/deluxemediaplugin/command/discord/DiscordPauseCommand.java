package io.github.pulsebeat02.deluxemediaplugin.command.discord;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;
import static io.github.pulsebeat02.deluxemediaplugin.command.Permission.has;
import static io.github.pulsebeat02.deluxemediaplugin.utility.nullability.ArgumentUtils.handleNull;
import static java.util.Objects.requireNonNull;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.github.pulsebeat02.deluxemediaplugin.DeluxeMediaPlugin;
import io.github.pulsebeat02.deluxemediaplugin.command.CommandSegment;
import io.github.pulsebeat02.deluxemediaplugin.message.Locale;
import net.kyori.adventure.audience.Audience;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public final class DiscordPauseCommand implements CommandSegment.Literal<CommandSender> {

  private final LiteralCommandNode<CommandSender> node;
  private final DeluxeMediaPlugin plugin;

  public DiscordPauseCommand(@NotNull final DeluxeMediaPlugin plugin) {
    this.plugin = plugin;
    this.node =
        this.literal("pause")
            .requires(has("deluxemediaplugin.command.discord.pause"))
            .executes(this::pauseDiscordBot)
            .build();
  }

  private int pauseDiscordBot(@NotNull final CommandContext<CommandSender> context) {

    final Audience audience = this.plugin.audience().sender(context.getSource());

    if (this.checkDiscordStatus(audience)) {
      return SINGLE_SUCCESS;
    }

    this.pause();

    audience.sendMessage(Locale.PAUSED_TRACK_DISCORD.build());

    return SINGLE_SUCCESS;
  }

  private boolean checkDiscordStatus(@NotNull final Audience audience) {
    return handleNull(audience, Locale.ERR_INVALID_DISCORD_BOT.build(), this.plugin.getMediaBot());
  }

  private void pause() {
    requireNonNull(this.plugin.getMediaBot()).getMusicManager().pauseTrack();
  }

  @Override
  public @NotNull LiteralCommandNode<CommandSender> getNode() {
    return this.node;
  }
}
