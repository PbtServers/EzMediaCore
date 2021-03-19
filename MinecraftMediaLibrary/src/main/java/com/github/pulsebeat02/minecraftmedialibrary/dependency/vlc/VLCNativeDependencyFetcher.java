/*
 * ============================================================================
 * Copyright (C) PulseBeat_02 - All Rights Reserved
 *
 * This file is part of MinecraftMediaLibrary
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Brandon Li <brandonli2006ma@gmail.com>, 3/2/2021
 * ============================================================================
 */

package com.github.pulsebeat02.minecraftmedialibrary.dependency.vlc;

import com.github.pulsebeat02.minecraftmedialibrary.MinecraftMediaLibrary;
import com.github.pulsebeat02.minecraftmedialibrary.logger.Logger;
import com.github.pulsebeat02.minecraftmedialibrary.utility.ArchiveUtilities;
import com.github.pulsebeat02.minecraftmedialibrary.utility.RuntimeUtilities;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

public class VLCNativeDependencyFetcher {

  /** Instantiates a new VLCNativeDependencyFetcher. */
  private final String dir;

  public VLCNativeDependencyFetcher(@NotNull final MinecraftMediaLibrary library) {
    dir = library.getVlcFolder();
  }

  public VLCNativeDependencyFetcher(@NotNull final String dir) {
    this.dir = dir;
  }

  /** Download libraries. */
  public void downloadLibraries() {
    Logger.info("Trying to find Native VLC Installation...");
    final NativeDiscovery nativeDiscovery = new NativeDiscovery();
    final EnhancedNativeDiscovery enhancedNativeDiscovery = new EnhancedNativeDiscovery(dir);
    final File before = findVLCFolder(new File(dir));
    if (before != null) {
      System.setProperty("jna.library.path", before.getAbsolutePath());
      enhancedNativeDiscovery.discover();
    }
    final boolean installed =
        nativeDiscovery.discover() || enhancedNativeDiscovery.getPath() != null;
    if (!installed) {
      Logger.info("No VLC Installation found on this system. Proceeding to install.");
      final String option = RuntimeUtilities.URL;
      File zip = null;
      if (option.equalsIgnoreCase("LINUX")) {
        try {
          final LinuxPackageManager manager = new LinuxPackageManager(dir);
          zip = manager.getPackage();
          Logger.info("Extracting File...");
          manager.extractContents();
          Logger.info("Successfully Extracted File");
        } catch (final IOException e) {
          e.printStackTrace();
        }
      } else {
        Logger.info("User is not using Linux. Proceeding to download archive from Github.");
        try {
          zip = new File(dir, "VLC.zip");
          FileUtils.copyURLToFile(new URL(option), zip);
          final String path = zip.getAbsolutePath();
          Logger.info("Zip File Path: " + path);
          Logger.info("Extracting File...");
          ArchiveUtilities.decompressArchive(new File(path), new File(dir));
          Logger.info("Successfully Extracted File");
        } catch (final IOException e) {
          e.printStackTrace();
        }
      }
      assert zip != null;
      Logger.info("Deleting Archive...");
      if (zip.delete()) {
        Logger.info("Archive deleted after installation.");
      } else {
        Logger.error("Archive could NOT be deleted after installation!");
      }
      final String folder = findVLCFolder(zip.getParentFile()).getAbsolutePath();
      System.setProperty("jna.library.path", folder);
      enhancedNativeDiscovery.discover();
      Logger.info("VLC JNA Lookup Path: " + folder);
      printSystemEnvironmentVariables();
      printSystemProperties();
    } else {
      Logger.info("Found VLC Installation! No need to install VLC beforehand.");
    }
  }

  /** Prints all System environment variables. */
  public void printSystemEnvironmentVariables() {
    Logger.info("======== SYSTEM ENVIRONMENT VARIABLES ========");
    for (final Map.Entry<String, String> entry : System.getenv().entrySet()) {
      Logger.info("Key: " + entry.getKey() + "| Entry: " + entry.getValue());
    }
    Logger.info("==============================================");
  }

  /** Prints all System properties. */
  public void printSystemProperties() {
    Logger.info("============== SYSTEM PROPERTIES ==============");
    final Properties p = System.getProperties();
    final Enumeration<Object> keys = p.keys();
    while (keys.hasMoreElements()) {
      final String key = (String) keys.nextElement();
      Logger.info("Key: " + key + "| Entry: " + p.get(key));
    }
    Logger.info("===============================================");
  }

  /**
   * Gets VLC folder in folder.
   *
   * @param folder search folder
   * @return file
   */
  public File findVLCFolder(@NotNull final File folder) {
    for (final File f : folder.listFiles()) {
      if (f.getName().contains("vlc")) {
        return f;
      }
    }
    return null;
  }
}
