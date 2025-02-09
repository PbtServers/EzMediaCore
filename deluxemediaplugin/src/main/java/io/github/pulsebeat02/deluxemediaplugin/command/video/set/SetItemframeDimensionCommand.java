package io.github.pulsebeat02.deluxemediaplugin.command.video.set;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;
import static io.github.pulsebeat02.deluxemediaplugin.command.Permission.has;
import static io.github.pulsebeat02.deluxemediaplugin.utility.component.ChatUtils.checkDimensionBoundaries;
import static io.github.pulsebeat02.ezmediacore.dimension.FrameDimension.X10_14;
import static io.github.pulsebeat02.ezmediacore.dimension.FrameDimension.X1_1;
import static io.github.pulsebeat02.ezmediacore.dimension.FrameDimension.X1_2;
import static io.github.pulsebeat02.ezmediacore.dimension.FrameDimension.X3_3;
import static io.github.pulsebeat02.ezmediacore.dimension.FrameDimension.X3_5;
import static io.github.pulsebeat02.ezmediacore.dimension.FrameDimension.X5_5;
import static io.github.pulsebeat02.ezmediacore.dimension.FrameDimension.X6_10;
import static io.github.pulsebeat02.ezmediacore.dimension.FrameDimension.X8_14;
import static io.github.pulsebeat02.ezmediacore.dimension.FrameDimension.X8_18;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.github.pulsebeat02.deluxemediaplugin.DeluxeMediaPlugin;
import io.github.pulsebeat02.deluxemediaplugin.command.CommandSegment;
import io.github.pulsebeat02.deluxemediaplugin.command.video.ScreenConfig;
import io.github.pulsebeat02.deluxemediaplugin.message.Locale;
import io.github.pulsebeat02.ezmediacore.dimension.Dimension;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.kyori.adventure.audience.Audience;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public final class SetItemframeDimensionCommand implements CommandSegment.Literal<CommandSender> {

  private final DeluxeMediaPlugin plugin;
  private final LiteralCommandNode<CommandSender> node;
  private final ScreenConfig config;

  public SetItemframeDimensionCommand(
      @NotNull final DeluxeMediaPlugin plugin, @NotNull final ScreenConfig config) {
    this.plugin = plugin;
    this.config = config;
    this.node =
        this.literal("itemframe-dimension")
            .requires(has("deluxemediaplugin.command.video.set.itemframe-dimension"))
            .then(
                this.argument("dimension", StringArgumentType.greedyString())
                    .suggests(this::suggestItemframeDimension)
                    .executes(this::setItemframeDimension))
            .build();
  }

  private @NotNull CompletableFuture<Suggestions> suggestItemframeDimension(
      @NotNull final CommandContext<CommandSender> context,
      @NotNull final SuggestionsBuilder builder) {
    final Set<Dimension> dimensions =
        Set.of(X1_1, X1_2, X3_3, X3_5, X5_5, X6_10, X8_14, X8_18, X10_14);
    for (final Dimension dimension : dimensions) {
      builder.suggest("%s:%s".formatted(dimension.getWidth(), dimension.getHeight()));
    }
    return builder.buildFuture();
  }

  private int setItemframeDimension(@NotNull final CommandContext<CommandSender> context) {

    final Audience audience = this.plugin.audience().sender(context.getSource());
    final Optional<int[]> optional =
        checkDimensionBoundaries(audience, context.getArgument("dimension", String.class));
    if (optional.isEmpty()) {
      return SINGLE_SUCCESS;
    }

    final int[] dims = optional.get();
    final int width = dims[0];
    final int height = dims[1];

    this.setDimensions(width, height);

    audience.sendMessage(Locale.CHANGED_ITEMFRAME_DIMS.build(width, height));

    return SINGLE_SUCCESS;
  }

  private void setDimensions(final int width, final int height) {
    this.config.setItemframeWidth(width);
    this.config.setItemframeHeight(height);
  }

  @Override
  public @NotNull LiteralCommandNode<CommandSender> getNode() {
    return this.node;
  }
}
