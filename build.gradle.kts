import java.io.ByteArrayOutputStream

plugins {
    `java-library`
    `maven-publish`
}

group = "com.pwdim"
description = "PwdimLobby"

// --- Lógica de Versão ---

// --- Lógica de Versão (Corrigida para Kotlin DSL) ---

val getCommitHash = {
    providers.exec {
        commandLine("git", "rev-parse", "--short", "HEAD")
    }.standardOutput.asText.get().trim().ifEmpty { "unknown" }
}

val determinePatchVersion = {
    val output = providers.exec {
        commandLine("git", "describe", "--tags")
        isIgnoreExitValue = true
    }.standardOutput.asText.get().trim()

    if (output.isEmpty() || !output.contains("-")) {
        "0"
    } else {
        output.split("-")[1]
    }
}

// Aplicando a versão
version = "1.0.${try { determinePatchVersion() } catch(e: Exception) { "0" }}-${try { getCommitHash() } catch(e: Exception) { "noscm" }}"

// --- Configurações de Java ---

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

// --- Repositórios ---

repositories {
    mavenLocal()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = uri("https://repo.extendedclip.com/releases/") }
    maven { url = uri("https://m2.dv8tion.net/releases") }
    maven { url = uri("https://repo.glaremasters.me/repository/concuncan/") }
    maven { url = uri("https://repo.viaversion.com") }
    mavenCentral()
}

// --- Dependências ---

dependencies {
    api(libs.org.mariadb.jdbc.mariadb.java.client)
    api(libs.com.zaxxer.hikaricp)
    api(libs.net.dv8tion.jda)
    api(libs.fr.mrmicky.fastboard)
    api(libs.com.grinderwolf.slimeworldmanager.api)

    compileOnly(libs.org.spigotmc.spigot.api)
    compileOnly(libs.me.clip.placeholderapi)
    compileOnly(libs.com.viaversion.viaversion.api)
}

// --- Tarefas e Publicação ---

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>().configureEach {
    options.encoding = "UTF-8"
}