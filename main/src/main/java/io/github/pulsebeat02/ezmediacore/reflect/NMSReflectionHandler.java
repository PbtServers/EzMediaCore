package io.github.pulsebeat02.ezmediacore.reflect;

import io.github.pulsebeat02.ezmediacore.Logger;
import io.github.pulsebeat02.ezmediacore.nms.PacketHandler;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public final class NMSReflectionHandler {

  public static final String VERSION;

  static {
    VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
  }

  @NotNull
  public static Optional<PacketHandler> getNewPacketHandlerInstance() {
    try {
      Logger.info("Loading NMS Class for Version %s".formatted(VERSION));
      final Class<?> clazz =
          Class.forName(
              "io.github.pulsebeat02.ezmediacore.nms.impl.%s.NMSMapPacketIntercepter"
                  .formatted(VERSION));
      return Optional.of((PacketHandler) clazz.getDeclaredConstructor().newInstance());
    } catch (final ClassNotFoundException
        | InstantiationException
        | IllegalAccessException
        | NoSuchMethodException
        | InvocationTargetException e) {
      Logger.error(
          "The Server Version you are using (%s) is not yet supported by EzMediaCore! Shutting down due to the Fatal Error"
              .formatted(VERSION));
      return Optional.empty();
    }
  }
}
