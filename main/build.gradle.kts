import io.github.slimjar.task.SlimJar

plugins {
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("io.github.slimjar") version "1.3.0"
}

dependencies {

    setOf("io.github.slimjar:slimjar:1.2.6").forEach {
        "implementation"(it)
    }

    setOf("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT").forEach {
        "compileOnlyApi"(it)
    }

    //////// TESTING ////////

    setOf("org.junit.jupiter:junit-jupiter-engine:5.4.2").forEach {
        testRuntimeOnly(it);
    }

    setOf(
        "org.junit.jupiter:junit-jupiter-api:5.4.2",
        "com.github.seeseemelk:MockBukkit-v1.17:1.7.0",
        "org.spigotmc:spigot-api:1.17.1-R0.1-SNAPSHOT"
    ).forEach {
        testImplementation(it)
    }

    /////////////////////////

    setOf(
        "io.netty:netty-all:4.1.70.Final",
        "com.mojang:authlib:1.5.26",
        "com.google.guava:guava:30.1.1-jre",
        "com.mpatric:mp3agic:0.9.1",
        "com.github.kevinsawicki:http-request:6.0",
    ).forEach {
        "compileOnly"(it)
        testImplementation(it)
    }

    setOf(
        "uk.co.caprica:vlcj:4.7.1",
        "uk.co.caprica:vlcj-natives:4.5.0",
        "com.github.sealedtx:java-youtube-downloader:3.0.1",
        "org.tukaani:xz:1.9",
        "com.alibaba:fastjson:1.2.78",
        "net.java.dev.jna:jna:5.9.0",
        "net.java.dev.jna:jna-platform:5.9.0",
        "se.michaelthelin.spotify:spotify-web-api-java:6.5.4",
        "com.github.kokorin.jaffree:jaffree:2021.08.31",
        "org.apache.commons:commons-lang3:3.12.0",
        "org.jcodec:jcodec:0.2.5",
        "com.github.ben-manes.caffeine:caffeine:3.0.3",
        "io.github.pulsebeat02:jarchivelib:v1.4.0",
        "io.github.pulsebeat02:emc-installers:v1.0.1",
        "it.unimi.dsi:fastutil:8.5.6"
    ).forEach {
        slim(it)
        "testImplementation"(it)
    }

    setOf(project(":api"), project(":v1_16_R3"), project(":v1_17_R1"), project(":v1_18_R1")).forEach {
        "api"(it)
    }
}


tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<SlimJar> {
    relocate("uk.co.caprica.vlcj", "io.github.pulsebeat02.ezmediacore.lib.vlcj")
    relocate("uk.co.caprica.vlcj.binding", "io.github.pulsebeat02.ezmediacore.lib.vlcj.binding")
    relocate(
        "uk.co.caprica.nativestreams",
        "io.github.pulsebeat02.ezmediacore.lib.vlcj.nativestreams"
    )
    relocate("com.github.kiulian.downloader", "io.github.pulsebeat02.ezmediacore.lib.youtube")
    relocate("ws.schild.jave", "io.github.pulsebeat02.ezmediacore.lib.jave")
    relocate("org.apache.commons.compress", "io.github.pulsebeat02.ezmediacore.lib.compress")
    relocate("org.rauschig.jarchivelib", "io.github.pulsebeat02.ezmediacore.lib.jarchivelib")
    relocate("org.tukaani.xz", "io.github.pulsebeat02.ezmediacore.lib.xz")
    relocate("org.apache.commons.io", "org.bukkit.craftbukkit.libs.org.apache.commons.io")
    relocate("com.wrapper.spotify", "io.github.pulsebeat02.ezmediacore.lib.spotify")
    relocate("com.github.kokorin", "io.github.pulsebeat02.ezmediacore.lib.kokorin")
    relocate("io.github.slimjar", "io.github.pulsebeat02.ezmediacore.lib.slimjar")
    relocate("org.jcodec", "io.github.pulsebeat02.ezmediacore.lib.jcodec")
    relocate("com.github.benmanes.caffeine", "io.github.pulsebeat02.ezmediacore.lib.caffeine")
    relocate("it.unimi.dsi.fastutil", "io.github.pulsebeat02.ezmediacore.lib.fastutil")
}