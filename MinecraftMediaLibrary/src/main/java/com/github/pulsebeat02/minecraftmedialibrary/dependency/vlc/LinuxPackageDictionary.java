/*
 * ============================================================================
 * Copyright (C) PulseBeat_02 - All Rights Reserved
 *
 * This file is part of MinecraftMediaLibrary
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Brandon Li <brandonli2006ma@gmail.com>, 2/23/2021
 * ============================================================================
 */

package com.github.pulsebeat02.minecraftmedialibrary.dependency.vlc;

import com.github.pulsebeat02.minecraftmedialibrary.exception.UnsupportedOperatingSystemException;
import com.github.pulsebeat02.minecraftmedialibrary.logger.Logger;
import com.github.pulsebeat02.minecraftmedialibrary.utility.OperatingSystemUtilities;
import com.github.pulsebeat02.minecraftmedialibrary.utility.ZipFileUtilities;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

public enum LinuxPackageDictionary {

  // https://pkgs.org/download/vlc

  // http://ftp.tku.edu.tw/Linux/ArchLinux-arm/aarch64/extra/
  // http://ftp.tku.edu.tw/Linux/ArchLinux-arm/armv7h/extra/
  ARCH_LINUX(
      ImmutableMap.of(
          "LATEST",
          ImmutableSet.of(
              "http://ftp.tku.edu.tw/Linux/ArchLinux-arm/aarch64/extra/vlc-3.0.12-1-aarch64.pkg.tar.xz", // AARCH64
              "http://ftp.tku.edu.tw/Linux/ArchLinux-arm/armv7h/extra/vlc-3.0.12-1-armv7h.pkg.tar.xz" // ARMV7H
              ))),

  // http://rpmfind.net/linux/rpmfusion/free/fedora/development/rawhide/Everything/aarch64/os/Packages/v/
  // http://rpmfind.net/linux/rpmfusion/free/fedora/development/rawhide/Everything/armv7hl/os/Packages/v/
  // http://rpmfind.net/linux/rpmfusion/free/fedora/development/rawhide/Everything/x86_64/os/Packages/v/
  CENTOS(
      ImmutableMap.of(
          "8",
              ImmutableSet.of(
                  "http://rpmfind.net/linux/rpmfusion/free/fedora/development/rawhide/Everything/aarch64/os/Packages/v/vlc-3.0.12.1-6.fc35.aarch64.rpm", // AARCH64
                  "http://rpmfind.net/linux/rpmfusion/free/fedora/development/rawhide/Everything/x86_64/os/Packages/v/vlc-3.0.12.1-6.fc35.x86_64.rpm"), // x86_64
          "7",
              ImmutableSet.of(
                  "http://rpmfind.net/linux/rpmfusion/free/fedora/development/rawhide/Everything/x86_64/os/Packages/v/vlc-3.0.12.1-6.fc35.x86_64.rpm"), // x86_64
          "6",
              ImmutableSet.of(
                  "http://rpmfind.net/linux/rpmfusion/free/fedora/development/rawhide/Everything/x86_64/os/Packages/v/vlc-3.0.12.1-6.fc35.x86_64.rpm", // x86_64
                  "http://ftp.riken.jp/Linux/rpmfusion/free/el/updates/6/x86_64/vlc-core-2.0.10-3.el6.i686.rpm" // i386
                  ))),

  // http://ftp.debian.org/debian/pool/main/v/vlc/
  DEBIAN(
      ImmutableMap.of(
          "Sid",
              ImmutableSet.of(
                  "http://ftp.debian.org/debian/pool/main/v/vlc/vlc_3.0.12-2_amd64.deb", // AMD64
                  "http://ftp.debian.org/debian/pool/main/v/vlc/vlc_3.0.12-2_arm64.deb", // ARM64
                  "http://ftp.debian.org/debian/pool/main/v/vlc/vlc_3.0.12-2_armhf.deb", // ARMHF
                  "http://ftp.debian.org/debian/pool/main/v/vlc/vlc_3.0.12-2_i386.deb" // i386
                  ),
          "11",
              ImmutableSet.of(
                  "http://ftp.debian.org/debian/pool/main/v/vlc/vlc_3.0.12-2_amd64.deb", // AMD64
                  "http://ftp.debian.org/debian/pool/main/v/vlc/vlc_3.0.12-2_arm64.deb", // ARM64
                  "http://ftp.debian.org/debian/pool/main/v/vlc/vlc_3.0.12-2_armhf.deb", // ARMHF
                  "http://ftp.debian.org/debian/pool/main/v/vlc/vlc_3.0.12-2_i386.deb" // i386
                  ),
          "10",
              ImmutableSet.of(
                  "http://ftp.debian.org/debian/pool/main/v/vlc/vlc_3.0.12-0+deb10u1_amd64.deb", // AMD64
                  "http://ftp.debian.org/debian/pool/main/v/vlc/vlc_3.0.12-0+deb10u1_arm64.deb", // ARM64
                  "http://ftp.debian.org/debian/pool/main/v/vlc/vlc_3.0.12-0+deb10u1_armhf.deb", // ARMHF
                  "http://ftp.debian.org/debian/pool/main/v/vlc/vlc_3.0.12-0+deb10u1_i386.deb" // i386
                  ),
          "9",
              ImmutableSet.of(
                  "http://ftp.debian.org/debian/pool/main/v/vlc/vlc_3.0.11-0+deb9u1_amd64.deb", // AMD64
                  "http://ftp.debian.org/debian/pool/main/v/vlc/vlc_3.0.11-0+deb9u1_arm64.deb", // ARM64
                  "http://ftp.debian.org/debian/pool/main/v/vlc/vlc_3.0.11-0+deb9u1_armhf.deb", // ARMHF
                  "http://ftp.debian.org/debian/pool/main/v/vlc/vlc_3.0.11-0+deb9u1_i386.deb" // i386
                  ))),

  // http://rpmfind.net/linux/rpmfusion/free/fedora/development/rawhide/Everything/aarch64/os/Packages/v/
  // http://rpmfind.net/linux/rpmfusion/free/fedora/development/rawhide/Everything/armv7hl/os/Packages/v/
  // http://rpmfind.net/linux/rpmfusion/free/fedora/development/rawhide/Everything/x86_64/os/Packages/v/
  FEDORA(
      ImmutableMap.of(
          "Rawhide",
              ImmutableSet.of(
                  "http://rpmfind.net/linux/rpmfusion/free/fedora/development/rawhide/Everything/aarch64/os/Packages/v/vlc-3.0.12.1-6.fc35.aarch64.rpm", // AARCH64
                  "http://rpmfind.net/linux/rpmfusion/free/fedora/development/rawhide/Everything/armhfp/os/Packages/v/vlc-3.0.12.1-6.fc35.armv7hl.rpm", // ARMHFP
                  "http://rpmfind.net/linux/rpmfusion/free/fedora/development/rawhide/Everything/x86_64/os/Packages/v/vlc-3.0.12.1-6.fc35.x86_64.rpm" // x86_64
                  ),
          "33",
              ImmutableSet.of(
                  "http://rpmfind.net/linux/rpmfusion/free/fedora/development/rawhide/Everything/aarch64/os/Packages/v/vlc-3.0.12.1-6.fc35.aarch64.rpm", // AARCH64
                  "http://rpmfind.net/linux/rpmfusion/free/fedora/development/rawhide/Everything/armhfp/os/Packages/v/vlc-3.0.12.1-6.fc35.armv7hl.rpm", // ARMHFP
                  "http://rpmfind.net/linux/rpmfusion/free/fedora/development/rawhide/Everything/x86_64/os/Packages/v/vlc-3.0.12.1-6.fc35.x86_64.rpm" // x86_64
                  ),
          "32",
              ImmutableSet.of(
                  "http://rpmfind.net/linux/rpmfusion/free/fedora/development/rawhide/Everything/aarch64/os/Packages/v/vlc-3.0.12.1-6.fc35.aarch64.rpm", // AARCH64
                  "http://rpmfind.net/linux/rpmfusion/free/fedora/development/rawhide/Everything/armhfp/os/Packages/v/vlc-3.0.12.1-6.fc35.armv7hl.rpm", // ARMHFP
                  "http://rpmfind.net/linux/rpmfusion/free/fedora/development/rawhide/Everything/x86_64/os/Packages/v/vlc-3.0.12.1-6.fc35.x86_64.rpm" // x86_64
                  ))),

  // http://ftp.freebsd.org/pub/FreeBSD/releases/
  // https://club.bisd.ro/package/freebsd/13/x86/64/annex/uvlist
  FREEBSD(
      ImmutableMap.of(
          "13",
              ImmutableSet.of(
                  "https://club.bisd.ro/package/freebsd/13/x86/64/annex/uv/All/vlc-3.0.11_9%2C4.txz", // AARCH64
                  "https://club.bisd.ro/package/freebsd/13/x86/64/annex/uv/All/vlc-3.0.11_9%2C4.txz", // AMD64
                  "https://club.bisd.ro/package/freebsd/13/x86/64/annex/uv/All/vlc-3.0.11_9%2C4.txz" // i386
                  ),
          "12",
              ImmutableSet.of(
                  "https://club.bisd.ro/package/freebsd/13/x86/64/annex/uv/All/vlc-3.0.11_9%2C4.txz", // AARCH64
                  "https://club.bisd.ro/package/freebsd/13/x86/64/annex/uv/All/vlc-3.0.11_9%2C4.txz", // AMD64
                  "https://club.bisd.ro/package/freebsd/13/x86/64/annex/uv/All/vlc-3.0.11_9%2C4.txz", // ARMV7
                  "https://club.bisd.ro/package/freebsd/13/x86/64/annex/uv/All/vlc-3.0.11_9%2C4.txz" // i386
                  ),
          "11",
              ImmutableSet.of(
                  "https://club.bisd.ro/package/freebsd/13/x86/64/annex/uv/All/vlc-3.0.11_9%2C4.txz", // AARCH64
                  "https://club.bisd.ro/package/freebsd/13/x86/64/annex/uv/All/vlc-3.0.11_9%2C4.txz", // AMD64
                  "https://club.bisd.ro/package/freebsd/13/x86/64/annex/uv/All/vlc-3.0.11_9%2C4.txz" // i386
                  ))),

  // https://mirror.math.princeton.edu/pub/kaoslinux/build/
  KAOS(
      ImmutableMap.of(
          "LATEST",
          ImmutableSet.of(
              "https://github.com/PulseBeat02/VLC-Release-Mirror/raw/master/linux/vlc-1_3.0.12.1-2-x86_64.pkg.tar.zst" // x86_64
              ))),

  // http://ftp.netbsd.org/pub/pkgsrc/current/pkgsrc/multimedia/vlc/README.html
  NETBSD(
      ImmutableMap.of(
          "9.1",
              ImmutableSet.of(
                  "ftp://ftp.netbsd.org/pub/pkgsrc/packages/NetBSD/x86_64/.9.0-current-2021-02-02-15.06/All/vlc-3.0.12.tgz", // x86_64
                  "ftp://ftp.netbsd.org/pub/pkgsrc/packages/NetBSD/earmv7hf/9.0/All/vlc-3.0.11.1.tgz", // EARMV7HF
                  "ftp://ftp.netbsd.org/pub/pkgsrc/packages/NetBSD/i386/9.0_2020Q2/All/vlc-3.0.11.tgz" // i386
                  ),
          "8.2",
              ImmutableSet.of(
                  "ftp://ftp.netbsd.org/pub/pkgsrc/packages/NetBSD/x86_64/8.0_2020Q2/All/vlc-3.0.11.tgz", // x86_64
                  "ftp://ftp.netbsd.org/pub/pkgsrc/packages/NetBSD/earmv7hf/8.0/All/vlc-3.0.11.1.tgz", // EARNMV7HF
                  "ftp://ftp.netbsd.org/pub/pkgsrc/packages/NetBSD/i386/8.0_2020Q4/All/vlc-3.0.11.1.tgz" // i386
                  ))),

  // http://ftp.us2.freshrpms.net/linux/opensuse/tumbleweed/repo/oss/i586/
  // http://ftp.us2.freshrpms.net/linux/opensuse/tumbleweed/repo/oss/x86_64/
  // http://fr2.rpmfind.net/linux/opensuse/ports/aarch64/tumbleweed/repo/oss/aarch64/
  // http://fr2.rpmfind.net/linux/opensuse/ports/armv7hl/tumbleweed/repo/oss/armv7hl/
  // http://download.videolan.org/pub/videolan/vlc//SuSE/
  OPENSUSE(
      ImmutableMap.of(
          "Tumbleweed",
              ImmutableSet.of(
                  "http://ftp.us2.freshrpms.net/linux/opensuse/tumbleweed/repo/oss/i586/vlc-3.0.12-1.3.i586.rpm", // i586
                  "http://ftp.us2.freshrpms.net/linux/opensuse/tumbleweed/repo/oss/x86_64/vlc-3.0.12-1.3.x86_64.rpm", // x86_64
                  "http://fr2.rpmfind.net/linux/opensuse/ports/aarch64/tumbleweed/repo/oss/aarch64/vlc-3.0.12-1.3.aarch64.rpm", // AARCH64
                  "http://fr2.rpmfind.net/linux/opensuse/ports/armv7hl/tumbleweed/repo/oss/armv7hl/vlc-3.0.12-1.3.armv7hl.rpm" // ARMC7HL
                  ),
          "Leap 15.2",
              ImmutableSet.of(
                  "http://download.videolan.org/pub/videolan/vlc//SuSE/Leap_15.2/x86_64/vlc-3.0.12-lp152.342.1.x86_64.rpm", // x86_64
                  "http://fr2.rpmfind.net/linux/opensuse/ports/aarch64/distribution/leap/15.2/repo/oss/aarch64/vlc-3.0.10-lp152.1.1.aarch64.rpm", // AARCH64
                  "http://fr2.rpmfind.net/linux/opensuse/ports/armv7hl/distribution/leap/15.2/repo/oss/armv7hl/vlc-3.0.10-lp152.1.1.armv7hl.rpm" // ARMV7HL
                  ))),

  // http://www.slackware.com/~alien/slackbuilds/vlc/pkg64/
  SLACKWARE(
      ImmutableMap.of(
          "current",
              ImmutableSet.of(
                  "http://www.slackware.com/~alien/slackbuilds/vlc/pkg/current/vlc-3.0.12-i586-1alien.txz", // i586
                  "http://www.slackware.com/~alien/slackbuilds/vlc/pkg64/current/vlc-3.0.12-x86_64-1alien.txz" // x86_64
                  ),
          "14.2",
              ImmutableSet.of(
                  "http://www.slackware.com/~alien/slackbuilds/vlc/pkg/14.2/vlc-3.0.12-i586-1alien.txz", // i586
                  "http://www.slackware.com/~alien/slackbuilds/vlc/pkg64/14.2/vlc-3.0.12-x86_64-1alien.txz" // x86_64
                  ),
          "14.1",
              ImmutableSet.of(
                  "http://www.slackware.com/~alien/slackbuilds/vlc/pkg/14.1/vlc-2.2.6-i486-1alien.txz", // i486
                  "http://www.slackware.com/~alien/slackbuilds/vlc/pkg64/14.1/vlc-2.2.6-x86_64-1alien.txz" // x86_64
                  ))),

  // https://mirrors.rit.edu/solus/packages/shannon/v/vlc/
  SOLUS(
      ImmutableMap.of(
          "LATEST",
          ImmutableSet.of(
              "https://mirrors.rit.edu/solus/packages/shannon/v/vlc/vlc-3.0.12-123-1-x86_64.eopkg" // x86_64
              ))),

  // http://archive.ubuntu.com/ubuntu/pool/universe/v/vlc/
  // http://ports.ubuntu.com/ubuntu-ports/pool/universe/v/vlc/
  UBUNTU(
      ImmutableMap.of(
          "20.10",
              ImmutableSet.of(
                  "http://archive.ubuntu.com/ubuntu/pool/universe/v/vlc/vlc_3.0.11.1-2_amd64.deb", // AMD64
                  "http://ports.ubuntu.com/ubuntu-ports/pool/universe/v/vlc/vlc_3.0.11.1-2_arm64.deb", // ARM64
                  "http://ports.ubuntu.com/ubuntu-ports/pool/universe/v/vlc/vlc_3.0.11.1-2_armhf.deb", // ARMHF
                  "http://archive.ubuntu.com/ubuntu/pool/universe/v/vlc/vlc_3.0.11.1-2_i386.deb" // i386
                  ),
          "20.04",
              ImmutableSet.of(
                  "http://archive.ubuntu.com/ubuntu/pool/universe/v/vlc/vlc_3.0.9.2-1_amd64.deb", // AMD64
                  "http://ports.ubuntu.com/ubuntu-ports/pool/universe/v/vlc/vlc_3.0.9.2-1_arm64.deb", // ARM64
                  "http://ports.ubuntu.com/ubuntu-ports/pool/universe/v/vlc/vlc_3.0.9.2-1_armhf.deb", // ARMHF
                  "http://archive.ubuntu.com/ubuntu/pool/universe/v/vlc/vlc_3.0.9.2-1_i386.deb" // i386
                  ),
          "18.04",
              ImmutableSet.of(
                  "http://archive.ubuntu.com/ubuntu/pool/universe/v/vlc/vlc_3.0.1-3build1_amd64.deb", // AMD64
                  "http://ports.ubuntu.com/ubuntu-ports/pool/universe/v/vlc/vlc_3.0.1-3build1_arm64.deb", // ARM64
                  "http://ports.ubuntu.com/ubuntu-ports/pool/universe/v/vlc/vlc_3.0.1-3build1_armhf.deb", // ARMHF
                  "http://archive.ubuntu.com/ubuntu/pool/universe/v/vlc/vlc_3.0.1-3build1_i386.deb" // i386
                  ),
          "16.04",
              ImmutableSet.of(
                  "http://archive.ubuntu.com/ubuntu/pool/universe/v/vlc/vlc_2.2.2-5_amd64.deb", // AMD64
                  "http://ports.ubuntu.com/ubuntu-ports/pool/universe/v/vlc/vlc_2.2.2-5_arm64.deb", // ARM64
                  "http://ports.ubuntu.com/ubuntu-ports/pool/universe/v/vlc/vlc_2.2.2-5_armhf.deb", // ARMHF
                  "http://archive.ubuntu.com/ubuntu/pool/universe/v/vlc/vlc_2.2.2-5_i386.deb" // i386
                  ),
          "14.04",
              ImmutableSet.of(
                  "http://archive.ubuntu.com/ubuntu/pool/universe/v/vlc/vlc_2.1.2-2build2_amd64.deb", // AMD64
                  "http://ports.ubuntu.com/ubuntu-ports/pool/universe/v/vlc/vlc_2.1.2-2build2_arm64.deb", // ARM64
                  "http://ports.ubuntu.com/ubuntu-ports/pool/universe/v/vlc/vlc_2.1.2-2build2_armhf.deb", // ARMHF
                  "http://archive.ubuntu.com/ubuntu/pool/universe/v/vlc/vlc_2.1.2-2build2_i386.deb" // i386
                  )));

  static {
    Logger.info("Printing All Package Links: ");
    for (final LinuxPackageDictionary dict : values()) {
      Logger.info("Operating System:" + dict.name());
      final Map<String, Set<String>> links = dict.getLinks();
      for (final String key : links.keySet()) {
        Logger.info("Version: " + key);
        for (final String url : links.get(key)) {
          Logger.info("URL: " + url);
        }
      }
    }
  }

  private final Map<String, Set<String>> links;

  LinuxPackageDictionary(@NotNull final Map<String, Set<String>> links) {
    this.links = links;
  }

  public static Set<CPUArchitecture> getCpuArchitectures(
      @NotNull final LinuxPackageDictionary dir, @NotNull final String link) {
    Logger.info("Attempting to Find CPU Architectures for URL: " + link);
    for (final CPUArchitecture architecture : CPUArchitecture.values()) {
      final String name = architecture.name().toLowerCase();
      if (link.contains(name)) {
        return ImmutableSet.of(architecture);
      }
    }
    /*
    FreeBSD and NetBSD sucks ass. They don't have the architecture in the file name.
     */
    Logger.info("User is using FreeBSD/NetBSD. Proceeding for more detailed search.");
    if (dir == FREEBSD) {
      if (link.equalsIgnoreCase("vlc-3.0.11_9,4.txz")) {
        return ImmutableSet.of(
            CPUArchitecture.AARCH64, CPUArchitecture.AMD64, CPUArchitecture.I386);
      } else if (link.equalsIgnoreCase("vlc-3.0.12,4.txz")) {
        return ImmutableSet.of(CPUArchitecture.AMD64, CPUArchitecture.I386);
      } else if (link.equalsIgnoreCase("vlc-3.0.11_6,4.txz")) {
        return ImmutableSet.of(CPUArchitecture.ARMV7);
      } else if (link.equalsIgnoreCase("vlc-3.0.11_1,4.txz")) {
        return ImmutableSet.of(CPUArchitecture.AMD64);
      }
    } else if (dir == NETBSD) {
      if (link.equalsIgnoreCase("vlc-3.0.11.1.tgz")) {
        return ImmutableSet.of(
            CPUArchitecture.AMD64, CPUArchitecture.EARNMV7HF, CPUArchitecture.I386);
      } else if (link.equalsIgnoreCase("vlc-3.0.11nb3.tgz")) {
        return ImmutableSet.of(CPUArchitecture.AMD64);
      }
    }
    return ImmutableSet.of();
  }

  public static File getPackage() throws IOException {
    Logger.info("Attempting to Find VLC Package for Machine.");
    final String os = OperatingSystemUtilities.OPERATING_SYSTEM;
    Set<String> set = null;
    LinuxPackageDictionary dir = null;
    outer:
    for (final LinuxPackageDictionary dict : values()) {
      final String name = dict.name().toLowerCase();
      Logger.info("Attempting Operating System" + name);
      if (os.contains(name)) {
        Logger.info("Found Operating System: " + name);
        final Map<String, Set<String>> links = dict.getLinks();
        for (final String version : links.keySet()) {
          Logger.info("Attempting Version: " + version);
          if (os.contains(version.toLowerCase())) {
            Logger.info("Found Version: " + version);
            set = links.get(version);
            dir = dict;
            break outer;
          }
        }
        Logger.warn("Could not find version, resorting to LATEST.");
        dir = dict;
        set = links.get("LATEST");
        break;
      }
    }
    final CPUArchitecture arch = CPUArchitecture.fromName(OperatingSystemUtilities.CPU_ARCH);
    if (set == null || arch == null) {
      Logger.error("Could not find architecture... throwing an error!");
      throw new UnsupportedOperatingSystemException("Unsupported Operating System Platform!");
    }
    final File vlc = new File("/vlc");
    if (!vlc.exists()) {
      if (vlc.mkdir()) {
        Logger.info("Made VLC Directory");
      } else {
        Logger.error("Failed to Make VLC Directory");
      }
    }
    for (final String link : set) {
      Logger.info("Trying Out Link: " + link);
      final Set<CPUArchitecture> options = getCpuArchitectures(dir, link);
      if (options.contains(arch)) {
        final String fileName = link.substring(link.lastIndexOf("/") + 1);
        final File file = new File("/vlc/" + fileName);
        URL url = new URL("");
        try {
          url = new URL(link);
        } catch (final MalformedURLException e) {
          Logger.info("Main Site is Down! Using Mirror! (" + url + ")");
          try {
            url =
                new URL(
                    "https://github.com/PulseBeat02/VLC-Release-Mirror/raw/master/linux/"
                        + fileName);
          } catch (final MalformedURLException e1) {
            Logger.error("Github Mirror is Down. You living in 2140 or something?");
            e1.printStackTrace();
          }
          e.printStackTrace();
        }
        FileUtils.copyURLToFile(url, file);
        return file;
      }
    }
    Logger.error("Could not find architecture... throwing an error!");
    throw new UnsupportedOperatingSystemException("Unsupported Operating System Platform!");
  }

  public static void extractContents() {
    final File vlc = new File("/vlc");
    final File f = new File("/vlc").listFiles()[0];
    final String name = f.getName();
    Logger.info("Trying to find extension for file: " + name);
    if (name.endsWith("deb") || name.endsWith("rpm") || name.endsWith("eopkg")) {
      Logger.info("Found .deb, .rpm, or .eopkg File!");
      ZipFileUtilities.decompressArchive(f, vlc);
    } else if (name.endsWith("txz") || name.endsWith(".tar.xz")) {
      Logger.info("Found .txz or .tar.xz File!");
      ZipFileUtilities.decompressArchive(f, vlc, "tar", "xz");
    } else if (name.endsWith("tgz")) {
      Logger.info("Found .tgz File!");
      ZipFileUtilities.decompressArchive(f, vlc, "tar", "gz");
    } else if (name.endsWith(".tar.zst")) {
      Logger.warn(
          "Hello user, please read this error carefully: Your computer seems to be using "
              + "KAOS Linux. The extract for KAOS Linux is a .tar.zst file, which is yet not supported by "
              + "the plugin yet. The archive has been downloaded in the /vlcj folder, and it is required by "
              + "you to extract the file in order to get the VLC libraries. This is a required step, and VLCJ "
              + "will not run if you do not perform this step.");
    }
  }

  public Map<String, Set<String>> getLinks() {
    return links;
  }
}
