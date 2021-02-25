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

package com.github.pulsebeat02.minecraftmedialibrary.nms;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.nio.ByteBuffer;
import java.util.UUID;

public interface PacketHandler {

  /**
   * Display.
   *
   * @param viewers the viewers
   * @param map the map
   * @param mapWidth the map width
   * @param mapHeight the map height
   * @param rgb the rgb
   * @param videoWidth the video width
   * @param xOffset the x offset
   * @param yOffset the y offset
   */
  void display(
      UUID[] viewers,
      int map,
      int mapWidth,
      int mapHeight,
      ByteBuffer rgb,
      int videoWidth,
      int xOffset,
      int yOffset);

  /**
   * Display.
   *
   * @param viewers the viewers
   * @param map the map
   * @param mapWidth the map width
   * @param mapHeight the map height
   * @param rgb the rgb
   * @param videoWidth the video width
   */
  void display(
      UUID[] viewers, int map, int mapWidth, int mapHeight, ByteBuffer rgb, int videoWidth);

  /**
   * Display.
   *
   * @param viewers the viewers
   * @param entities the entities
   * @param data the data
   * @param width the width
   */
  void display(UUID[] viewers, Entity[] entities, int[] data, int width);

  /**
   * Registers player.
   *
   * @param player the player
   */
  void registerPlayer(Player player);

  /**
   * Unregisters player.
   *
   * @param player the player
   */
  void unregisterPlayer(Player player);

  /**
   * Checks if the map is registered.
   *
   * @param id the id
   * @return the boolean
   */
  boolean isMapRegistered(int id);

  /**
   * Unregisters map.
   *
   * @param id the id
   */
  void unregisterMap(int id);

  /**
   * Registers map.
   *
   * @param id the id
   */
  void registerMap(int id);

  /**
   * Called when packet is intercepted out.
   *
   * @param viewer the viewer
   * @param packet the packet
   * @return the object
   */
  Object onPacketInterceptOut(Player viewer, Object packet);

  /**
   * Called when packet is intercepted in.
   *
   * @param viewer the viewer
   * @param packet the packet
   * @return the object
   */
  Object onPacketInterceptIn(Player viewer, Object packet);
}
