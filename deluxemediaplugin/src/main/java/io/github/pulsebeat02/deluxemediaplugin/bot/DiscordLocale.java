/*
 * MIT License
 *
 * Copyright (c) 2021 Brandon Li
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.pulsebeat02.deluxemediaplugin.bot;

import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import io.github.pulsebeat02.deluxemediaplugin.message.Sender;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public interface DiscordLocale {

  SimpleDateFormat HOURS_MINUTES_SECONDS = new SimpleDateFormat("mm:ss:SSS");

  NullComponent<Sender> CONNECT_VC_EMBED =
      () ->
          new EmbedBuilder()
              .setTitle("Audio Voice Channel Connection")
              .setDescription("Connected to voice channel!")
              .build();

  NullComponent<Sender> DC_VC_EMBED =
      () ->
          new EmbedBuilder()
              .setTitle("Audio Voice Channel Connection")
              .setDescription("Left voice channel!")
              .build();

  NullComponent<Sender> ERR_INVALID_MRL =
      () ->
          new EmbedBuilder()
              .setTitle("User Error")
              .setDescription("Invalid arguments! Specify a media source argument to play.")
              .build();

  NullComponent<Sender> ERR_LAVAPLAYER =
      () ->
          new EmbedBuilder()
              .setTitle("Severe Player Error Occurred!")
              .setDescription(
                  "An error occurred! Check console for possible exceptions or warnings.")
              .build();

  NullComponent<Sender> PAUSE_AUDIO =
      () -> new EmbedBuilder().setTitle("Audio Stop").setDescription("Stopped Audio!").build();

  UniComponent<Sender, AudioTrackInfo> LOADED_TRACK =
      (info) ->
          new EmbedBuilder()
              .setTitle(info.title, info.uri)
              .addField("Author", info.author, false)
              .addField(
                  "Playtime Length", HOURS_MINUTES_SECONDS.format(new Date(info.length)), false)
              .addField("Stream", info.isStream ? "Yes" : "No", false)
              .build();

  UniComponent<Sender, String> ERR_INVALID_TRACK =
      (url) ->
          new EmbedBuilder()
              .setTitle("Media Error")
              .setDescription("Could not find song %s!".formatted(url))
              .build();

  TriComponent<Sender, Long, AudioPlaylist, String> LOADED_PLAYLIST =
      (ms, audioPlaylist, url) ->
          new EmbedBuilder()
              .setTitle(audioPlaylist.getName(), url)
              .addField("Playtime Length", HOURS_MINUTES_SECONDS.format(new Date(ms)), false)
              .build();

  @FunctionalInterface
  interface NullComponent<S extends Sender> {

    MessageEmbed build();
  }

  @FunctionalInterface
  interface UniComponent<S extends Sender, A0> {

    MessageEmbed build(A0 arg0);
  }

  @FunctionalInterface
  interface BiComponent<S extends Sender, A0, A1> {

    MessageEmbed build(A0 arg0, A1 arg1);
  }

  @FunctionalInterface
  interface TriComponent<S extends Sender, A0, A1, A2> {

    MessageEmbed build(A0 arg0, A1 arg1, A2 arg2);
  }

  @FunctionalInterface
  interface QuadComponent<S extends Sender, A0, A1, A2, A3> {

    MessageEmbed build(A0 arg0, A1 arg1, A2 arg2, A3 arg3);
  }

  @FunctionalInterface
  interface PentaComponent<S extends Sender, A0, A1, A2, A3, A4> {

    MessageEmbed build(A0 arg0, A1 arg1, A2 arg2, A3 arg3, A4 arg4);
  }

  @FunctionalInterface
  interface HexaComponent<S extends Sender, A0, A1, A2, A3, A4, A5> {

    MessageEmbed build(A0 arg0, A1 arg1, A2 arg2, A3 arg3, A4 arg4, A5 arg5);
  }
}