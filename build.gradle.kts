import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.fabricmc.loom.api.LoomGradleExtensionAPI
import net.fabricmc.loom.task.RemapJarTask

plugins {
    id("java")
    kotlin("jvm") version "1.9.22"
    id("org.jetbrains.dokka") version "1.9.20"
    id("architectury-plugin") version "3.4-SNAPSHOT"
    id("dev.architectury.loom") version "1.6-SNAPSHOT" apply false
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
    id("io.github.pacifistmc.forgix") version "1.2.9"
}

architectury {
    minecraft = rootProject.property("minecraft_version").toString()
}

forgix {
    group = "org.stardustmodding.interstellar"
    mergedJarName = "interstellar"
    outputDir = "build/libs/merged"
    mergedJarName = "interstellar-universal-${rootProject.property("mod_version")}+${rootProject.property("minecraft_version")}.jar"
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "architectury-plugin")
    apply(plugin = "maven-publish")
    apply(plugin = "com.github.johnrengelman.shadow")

    base.archivesName.set(rootProject.property("archives_base_name").toString())
    version = rootProject.property("mod_version").toString()
    group = rootProject.property("maven_group").toString()

    repositories {
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
            name = "WispForest Maven"
            url = uri("https://maven.wispforest.io")
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
    }

    dependencies {
        compileOnly("org.jetbrains.kotlin:kotlin-stdlib")
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(17)
    }

    kotlin.target.compilations.all {
        kotlinOptions.jvmTarget = "17"
    }

    java {
        withSourcesJar()
    }
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "dev.architectury.loom")

    dependencies {
        "minecraft"("com.mojang:minecraft:${rootProject.property("minecraft_version")}")
        "mappings"("net.fabricmc:yarn:${rootProject.property("yarn_mappings")}:v2")
    }

    if (tasks.names.contains("remapJar")) {
        tasks.named("remapJar") {
            finalizedBy(rootProject.tasks.mergeJars)
        }
    }

    configure<PublishingExtension> {
        publications {
            create<MavenPublication>("mod") {
                // groupId = "org.stardustmodding.interstellar"
                // artifactId = "interstellar-${project.name}"
                // version = rootProject.property("mod_version")!! as String
                // pom.packaging = "jar"

                if (tasks.names.contains("remapJar")) {
                    artifact(tasks.named<RemapJarTask>("remapJar").get().archiveFile)
                } else {
                    artifact(tasks.named<ShadowJar>("shadowJar").get().archiveFile)
                }

                artifact(tasks.kotlinSourcesJar.get().archiveFile)
            }
        }

        repositories {
            if (System.getenv("GITHUB_ACTOR") != null && System.getenv("GITHUB_TOKEN") != null) {
                maven {
                    name = "GitHubPackages"
                    url = uri("https://maven.pkg.github.com/StardustModding/Interstellar")

                    credentials {
                        username = System.getenv("GITHUB_ACTOR")
                        password = System.getenv("GITHUB_TOKEN")
                    }
                }
            }

            if (System.getenv("MAVEN_USER") != null && System.getenv("MAVEN_PASSWORD") != null) {
                maven {
                    name = "StardustModding"
                    url = uri("https://repo.kadaroja.com/snapshots/")

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
