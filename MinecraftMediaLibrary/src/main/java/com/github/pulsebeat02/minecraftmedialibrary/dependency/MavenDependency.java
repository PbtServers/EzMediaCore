/*
 * ============================================================================
 * Copyright (C) PulseBeat_02 - All Rights Reserved
 *
 * This file is part of MinecraftMediaLibrary
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Brandon Li <brandonli2006ma@gmail.com>, 2/11/2021
 * ============================================================================
 */

package com.github.pulsebeat02.minecraftmedialibrary.dependency;

import org.jetbrains.annotations.NotNull;

public enum MavenDependency {

  /** VLCJ Maven Dependency */
  VLCJ("uk.co.caprica", "vlcj", "4.6.0"),

  /** VLCJ Natives Maven Dependency */
  VLCJ_NATIVES("uk.co.caprica", "vlcj-natives", "4.5.0"),

  /** Youtube Downloader Maven Dependency */
  YOUTUBE_DOWNLOADER("com.github.sealedtx", "java-youtube-downloader", "2.4.6"),

  /** Jave Core Maven Dependency */
  JAVE_CORE("ws.schild", "jave-core", "3.0.1"),

  /** Compression Maven Dependency */
  COMPRESSION("org.rauschig", "jarchivelib", "0.7.1"),

  /** ASM Maven Dependency */
  ASM("org.ow2.asm", "asm", "9.1"),

  /** ASM Commons Maven Dependency */
  ASM_COMMONS("org.ow2.asm", "asm-commons", "9.1");

  //  JAVACV("org.bytedeco", "javacv-platform", "1.5.4");

  private final String group;
  private final String artifact;
  private final String version;

  MavenDependency(
      @NotNull final String group, @NotNull final String artifact, @NotNull final String version) {
    this.group = group;
    this.artifact = artifact;
    this.version = version;
  }

  /**
   * Gets group.
   *
   * @return the group
   */
  public String getGroup() {
    return group;
  }

  /**
   * Gets artifact.
   *
   * @return the artifact
   */
  public String getArtifact() {
    return artifact;
  }

  /**
   * Gets version.
   *
   * @return the version
   */
  public String getVersion() {
    return version;
  }
}
