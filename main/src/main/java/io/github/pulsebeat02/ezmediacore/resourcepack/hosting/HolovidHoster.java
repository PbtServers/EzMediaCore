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
package io.github.pulsebeat02.ezmediacore.resourcepack.hosting;

import io.github.pulsebeat02.ezmediacore.Logger;
import io.github.pulsebeat02.ezmediacore.json.GsonProvider;
import io.github.pulsebeat02.ezmediacore.locale.Locale;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.jcodec.codecs.mjpeg.tools.AssertionException;
import org.jetbrains.annotations.NotNull;

public class HolovidHoster implements HolovidSolution {

  private static final String HOLOVID_LINK;
  private static final HttpClient HTTP_CLIENT;

  static {
    HOLOVID_LINK = "https://holovid.glare.dev/resourcepack/download?videoUrl=";
    HTTP_CLIENT = HttpClient.newHttpClient();
  }

  @Override
  public @NotNull String createUrl(@NotNull final String input) {
    try {
      return GsonProvider.getSimple()
          .fromJson(
              HTTP_CLIENT.send(
                  HttpRequest.newBuilder()
                      .uri(new URI(HOLOVID_LINK + input))
                      .build(), HttpResponse.BodyHandlers.ofString()
              ).body(),
              HolovidResourcepackResult.class)
          .getUrl();
    } catch (final IOException | URISyntaxException | InterruptedException e) {
      Logger.info(Locale.ERR_HOLOVID);
      e.printStackTrace();
    }
    throw new AssertionException("Holovid website is down!");
  }
}
