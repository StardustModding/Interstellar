import net.fabricmc.loom.api.LoomGradleExtensionAPI
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import java.net.URL

plugins {
    id("java")
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.serialization") version "1.9.24"
    id("org.jetbrains.dokka") version "1.9.20"
    id("architectury-plugin") version "3.4-SNAPSHOT"
    id("dev.architectury.loom") version "1.6-SNAPSHOT" apply false
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

architectury {
    minecraft = rootProject.property("minecraft_version").toString()
}

dependencies {
    subprojects
}

tasks.build.get().finalizedBy(tasks.named("shadowJar"))

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    for (file in listOf("fabric.mod.json", "mods.toml", "pack.mcmeta")) {
        filesMatching(file) {
            expand(
                mapOf(
                    "group" to rootProject.property("maven_group"),
                    "version" to project.version,

                    "mod_id" to rootProject.property("mod_id"),
                    "minecraft_version" to rootProject.property("minecraft_version"),
                    "architectury_version" to rootProject.property("architectury_version"),
                    "fabric_kotlin_version" to rootProject.property("fabric_kotlin_version"),
                    "cloth_config_version" to rootProject.property("cloth_config_version"),
                )
            )
        }
    }
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "kotlinx-serialization")
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "architectury-plugin")
    apply(plugin = "com.github.johnrengelman.shadow")

    base.archivesName.set(rootProject.property("archives_base_name").toString())
    version = rootProject.property("mod_version").toString()
    group = rootProject.property("maven_group").toString()

    repositories {
        mavenLocal()
        mavenCentral()

        maven {
            name = "TerraformersMC Maven"
            url = uri("https://maven.terraformersmc.com/releases")
        }

        maven {
            name = "Shedaniel Maven"
            url = uri("https://maven.shedaniel.me")
        }

        maven {
            name = "Galacticraft Maven"
            url = uri("https://maven.galacticraft.net/repository/maven-releases/")
        }

        maven {
            name = "Kyrptonaught Maven"
            url = uri("https://maven.kyrptonaught.dev")
        }

        maven {
            name = "BadAsIntended Maven"
            url = uri("https://maven2.bai.lol")
        }

        maven {
            name = "ParchmentMC Maven"
            url = uri("https://maven.parchmentmc.org")
        }

        maven {
            name = "tterrag Maven"
            url = uri("https://maven.tterrag.com/")
        }

        maven {
            name = "Modrinth Maven"
            url = uri("https://api.modrinth.com/maven")
        }

        maven {
            name = "BlameJared Maven"
            url = uri("https://maven.blamejared.com")
        }

        maven {
            name = "StardustModding Maven Releases"
            url = uri("https://maven.stardustmodding.org/releases")
        }
    }

    dependencies {
        compileOnly("org.jetbrains.kotlin:kotlin-stdlib")
        compileOnly("org.jetbrains.kotlin:kotlin-reflect:1.9.24")

        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0-RC")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(17)
    }

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
            languageVersion = KotlinVersion.KOTLIN_1_9
            apiVersion = KotlinVersion.KOTLIN_1_9
        }
    }

    java {
        withSourcesJar()
    }

    tasks.processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE

        for (file in listOf("fabric.mod.json", "mods.toml", "pack.mcmeta")) {
            filesMatching(file) {
                expand(
                    mapOf(
                        "group" to rootProject.property("maven_group"),
                        "version" to project.version,

                        "mod_id" to rootProject.property("mod_id"),
                        "minecraft_version" to rootProject.property("minecraft_version"),
                        "architectury_version" to rootProject.property("architectury_version"),
                        "fabric_kotlin_version" to rootProject.property("fabric_kotlin_version"),
                        "cloth_config_version" to rootProject.property("cloth_config_version"),
                    )
                )
            }
        }
    }

    tasks.register<DokkaTask>("dokkaAll") {
        dokkaSourceSets {
            subprojects.map {
                create(it.name) {
                    sourceRoots.from(it.sourceSets.main.get().kotlin)
                    includeNonPublic.set(true)

                    externalDocumentationLink {
                        url.set(URL("https://kotlinlang.org/api/kotlinx.serialization/"))
                    }

                    externalDocumentationLink {
                        url.set(URL("https://kotlinlang.org/api/kotlinx.coroutines/"))
                    }

                    externalDocumentationLink {
                        url.set(URL("https://maven.stardustmodding.org/dokka/releases/net/fabricmc/yarn/1.20.1+build.local/raw/"))
                        packageListUrl.set(URL("${url.get()}yarn/package-list"))
                    }

                    externalDocumentationLink {
                        url.set(URL("https://repo.kadaroja.com/dokka/releases/net/fabricmc/fabric-api/fabric-api/0.92.2+local-1.20.1/raw/"))
                        packageListUrl.set(URL("${url.get()}fabric-api/package-list"))
                    }
                }
            }
        }

        failOnWarning.set(false)
        outputDirectory.set(file("${layout.buildDirectory.get()}/docs/dokka"))
    }

    tasks.register<Jar>("dokkaJar") {
        dependsOn(tasks.named("dokkaAll"))
        from(tasks.named<DokkaTask>("dokkaAll").get().outputDirectory)

        if (project.name == rootProject.name) {
            archiveBaseName.set(archiveBaseName.get())
        } else {
            archiveBaseName.set(archiveBaseName.get() + "-" + project.name)
        }

        archiveClassifier.set("dokka")
        archiveVersion.set("${version}+${rootProject.property("minecraft_version")}")
    }

    tasks.register<Jar>("prodJar") {
        from(tasks.named("compileKotlin"))
        from(sourceSets.main.get().resources)

        if (project.name == rootProject.name) {
            archiveBaseName.set(archiveBaseName.get())
        } else {
            archiveBaseName.set(archiveBaseName.get() + "-" + project.name)
        }

        archiveClassifier.set(null as String?)
        archiveVersion.set("${version}+${rootProject.property("minecraft_version")}")
    }

    tasks.kotlinSourcesJar {
        from(subprojects.map {
            it.sourceSets.main.get().allSource
        })

        duplicatesStrategy = DuplicatesStrategy.INCLUDE

        if (project.name == rootProject.name) {
            archiveBaseName.set(archiveBaseName.get())
        } else {
            archiveBaseName.set(archiveBaseName.get() + "-" + project.name)
        }

        archiveClassifier.set("sources")
        archiveVersion.set("${version}+${rootProject.property("minecraft_version")}")
    }

    tasks.named("shadowJar").get().finalizedBy(
        tasks.named("dokkaJar"),
        tasks.named("prodJar"),
        tasks.kotlinSourcesJar,
    )

    configure<PublishingExtension> {
        publications {
            create<MavenPublication>("mod") {
                groupId = "org.stardustmodding.interstellar"
                version = rootProject.property("mod_version")!! as String
                pom.packaging = "jar"

                if (project.name == rootProject.name) {
                    artifactId = "interstellar"
                } else {
                    artifactId = "interstellar-${project.name}"
                }

                if (tasks.names.contains("remapJar")) {
                    artifact(tasks.named("remapJar"))
                } else {
                    artifact(tasks.named("shadowJar"))
                }

                artifact(tasks.kotlinSourcesJar)
                artifact(tasks.named("prodJar"))
                artifact(tasks.named("dokkaJar"))
            }
        }

        repositories {
            if (System.getenv("MAVEN_USER") != null && System.getenv("MAVEN_PASSWORD") != null) {
                maven {
                    name = "StardustModding"
                    url = uri("https://maven.stardustmodding.org/snapshots/")

                    credentials {
                        username = System.getenv("MAVEN_USER")
                        password = System.getenv("MAVEN_PASSWORD")
                    }
                }
            }

            mavenLocal()
        }
    }
}

subprojects {
    apply(plugin = "dev.architectury.loom")

    val loom = project.extensions.getByName<LoomGradleExtensionAPI>("loom")

    repositories {
        mavenLocal()
    }

    dependencies {
        "minecraft"("com.mojang:minecraft:${rootProject.property("minecraft_version")}")

//        "mappings"("net.fabricmc:yarn:${rootProject.property("yarn_mappings")}:v2")

        @Suppress("UnstableApiUsage")
        "mappings"(loom.layered {
            officialMojangMappings()
            parchment("org.parchmentmc.data:parchment-${rootProject.property("minecraft_version")}:${rootProject.property("parchment_version")}@zip")
        })
    }
}
