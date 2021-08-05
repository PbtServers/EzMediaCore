package io.github.pulsebeat02.ezmediacore.listener;

import io.github.pulsebeat02.ezmediacore.Logger;
import io.github.pulsebeat02.ezmediacore.MediaLibraryCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public record RegistrationListener(MediaLibraryCore core) implements Listener {

  public RegistrationListener(@NotNull final MediaLibraryCore core) {
    this.core = core;
    final Plugin plugin = core.getPlugin();
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  @EventHandler
  public void onPlayerJoin(final PlayerJoinEvent event) {
    final Player p = event.getPlayer();
    this.core.getHandler().registerPlayer(p);
    Logger.info("Registered Player %s".formatted(p.getUniqueId()));
  }

  /**
   * Unregisters the player on leave.
   *
   * @param event PlayerQuitEvent event
   */
  @EventHandler
  public void onPlayerLeave(final PlayerQuitEvent event) {
    final Player p = event.getPlayer();
    this.core.getHandler().unregisterPlayer(p);
    Logger.info("Unregistered Player %s".formatted(p.getUniqueId()));
  }
}