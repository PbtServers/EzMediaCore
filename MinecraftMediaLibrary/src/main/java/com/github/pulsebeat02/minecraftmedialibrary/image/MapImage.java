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

package com.github.pulsebeat02.minecraftmedialibrary.image;

import com.github.pulsebeat02.minecraftmedialibrary.MinecraftMediaLibrary;
import com.github.pulsebeat02.minecraftmedialibrary.logger.Logger;
import com.github.pulsebeat02.minecraftmedialibrary.utility.FileUtilities;
import com.github.pulsebeat02.minecraftmedialibrary.utility.VideoUtilities;
import com.github.pulsebeat02.minecraftmedialibrary.video.dither.FloydImageDither;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.NumberConversions;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MapImage implements AbstractImageMapHolder, ConfigurationSerializable {

  private final MinecraftMediaLibrary library;
  private final int map;
  private final File image;
  private final int height;
  private final int width;

  /**
   * Instantiates a new MapImage.
   *
   * @param library the library
   * @param map the map
   * @param image the image
   * @param width the width
   * @param height the height
   */
  public MapImage(
      @NotNull final MinecraftMediaLibrary library,
      final int map,
      @NotNull final File image,
      final int width,
      final int height) {
    this.library = library;
    this.map = map;
    this.image = image;
    this.width = width;
    this.height = height;
    Logger.info(
        "Initialized Image at Map ID " + map + " (Source: " + image.getAbsolutePath() + ")");
  }

  /**
   * Instantiates a new MapImage.
   *
   * @param library the library
   * @param map the map
   * @param url the url
   * @param width the width
   * @param height the height
   */
  public MapImage(
      @NotNull final MinecraftMediaLibrary library,
      final int map,
      @NotNull final String url,
      final int width,
      final int height) {
    this.library = library;
    this.map = map;
    this.image = FileUtilities.downloadImageFile(url, library.getPath());
    this.width = width;
    this.height = height;
    Logger.info(
        "Initialized Image at Map ID " + map + " (Source: " + image.getAbsolutePath() + ")");
  }

  /**
   * Deserializes a map image.
   *
   * @param library the library
   * @param deserialize the deserialize
   * @return the map image
   */
  public static MapImage deserialize(
      @NotNull final MinecraftMediaLibrary library,
      @NotNull final Map<String, Object> deserialize) {
    return new MapImage(
        library,
        NumberConversions.toInt(deserialize.get("map")),
        new File(String.valueOf(deserialize.get("image"))),
        NumberConversions.toInt(deserialize.get("width")),
        NumberConversions.toInt(deserialize.get("height")));
  }

  /**
   * Resets a specific map id.
   *
   * @param library the library
   * @param id the id
   */
  public static void resetMap(@NotNull final MinecraftMediaLibrary library, final int id) {
    library.getHandler().unregisterMap(id);
  }

  /** Draws the specific image on the map id. */
  @Override
  public void drawImage() {
    onDrawImage();
    final ByteBuffer buffer =
        new FloydImageDither()
            .ditherIntoMinecraft(Objects.requireNonNull(VideoUtilities.getBuffer(image)), width);
    library.getHandler().display(null, map, width, height, buffer, width);
    Logger.info("Drew Image at Map ID " + map + " (Source: " + image.getAbsolutePath() + ")");
  }

  /** Called when an image is being draw on a map. */
  @Override
  public void onDrawImage() {}

  /**
   * Serializes a MapImage.
   *
   * @return map of serialized values
   */
  @Override
  public Map<String, Object> serialize() {
    final Map<String, Object> serialized = new HashMap<>();
    serialized.put("map", map);
    serialized.put("image", image.getAbsolutePath());
    serialized.put("width", width);
    serialized.put("height", height);
    return serialized;
  }

  /**
   * Gets library.
   *
   * @return the library
   */
  public MinecraftMediaLibrary getLibrary() {
    return library;
  }

  /**
   * Gets map.
   *
   * @return the map
   */
  public int getMap() {
    return map;
  }

  /**
   * Gets image.
   *
   * @return the image
   */
  public File getImage() {
    return image;
  }

  /**
   * Gets height.
   *
   * @return the height
   */
  public int getHeight() {
    return height;
  }

  /**
   * Gets width.
   *
   * @return the width
   */
  public int getWidth() {
    return width;
  }

  public static class Builder {

    private int map;
    private File image;
    private int height;
    private int width;

    /**
     * Sets map.
     *
     * @param map the map
     * @return the map
     */
    public Builder setMap(final int map) {
      this.map = map;
      return this;
    }

    /**
     * Sets image.
     *
     * @param image the image
     * @return the image
     */
    public Builder setImage(final File image) {
      this.image = image;
      return this;
    }

    /**
     * Sets height.
     *
     * @param height the height
     * @return the height
     */
    public Builder setHeight(final int height) {
      this.height = height;
      return this;
    }

    /**
     * Sets width.
     *
     * @param width the width
     * @return the width
     */
    public Builder setWidth(final int width) {
      this.width = width;
      return this;
    }

    /**
     * Create image map map image.
     *
     * @param library the library
     * @return the map image
     */
    public MapImage createImageMap(final MinecraftMediaLibrary library) {
      return new MapImage(library, map, image, width, height);
    }
  }
}
