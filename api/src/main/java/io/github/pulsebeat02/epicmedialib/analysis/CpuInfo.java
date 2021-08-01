package io.github.pulsebeat02.epicmedialib.analysis;

import org.jetbrains.annotations.NotNull;

public interface CpuInfo {

  @NotNull
  String getArchitecture();

  boolean isBits64();
}
