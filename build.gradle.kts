import org.jetbrains.dokka.gradle.DokkaTask
import java.net.URL

plugins {
    id("java")
    kotlin("jvm") version "1.9.22"
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

        maven {
            name = "tterrag Maven"
            url = uri("https://maven.tterrag.com/")
        }
    }

    dependencies {
        compileOnly("org.jetbrains.kotlin:kotlin-stdlib")
        compileOnly("org.jetbrains.kotlin:kotlin-reflect:1.9.22")
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

    tasks.register<Javadoc>("javadocAll") {
        val docletProj = rootProject.project("doclet")
        val classes = docletProj.layout.buildDirectory.get().dir("classes").asFileTree
        val resources = docletProj.sourceSets.main.get().resources.asFileTree

        isFailOnError = false
        setMaxMemory("2G")

        val opts = options as StandardJavadocDocletOptions

        opts.source = "17"
        opts.encoding = "UTF-8"
        opts.charSet = "UTF-8"
        opts.memberLevel = JavadocMemberLevel.PRIVATE
        opts.splitIndex(true)

        opts.links(
            "https://guava.dev/releases/31.1-jre/api/docs/",
            "https://asm.ow2.io/javadoc/",
            "https://docs.oracle.com/en/java/javase/17/docs/api/",
            "https://jenkins.liteloader.com/job/Mixin/javadoc/",
            "https://maven.fabricmc.net/docs/yarn-${rootProject.property("yarn_mappings")}/"
        )

        opts.tags(
            "author:a",
            "reason:m:\"Reason:\"",
            "apiNote:a:API Note:",
            "implSpec:a:Implementation Requirements:",
            "implNote:a:Implementation Note:",
        )

        opts.taglets("org.stardustmodding.docs.taglet.MappingTaglet")
        opts.use()
        opts.addBooleanOption("-allow-script-in-comments", true)
        opts.classpath(classes.toList())
        opts.addBooleanOption("Xdoclint:html", true)
        opts.addBooleanOption("Xdoclint:syntax", true)
        opts.addBooleanOption("Xdoclint:reference", true)
        opts.addBooleanOption("Xdoclint:accessibility", true)
        opts.addStringOption("-notimestamp")
        opts.addStringOption("Xdoclint:none", "-quiet")

        dependsOn(docletProj.tasks.named("build"))

        source(subprojects.map {
            it.sourceSets.main.get().allJava
        })

        classpath = files(subprojects.map {
            it.sourceSets.main.get().compileClasspath
        })

        doFirst {
            @Suppress("NAME_SHADOWING") val opts = options as StandardJavadocDocletOptions

            opts.tagletPath = docletProj.sourceSets.main.get().output.classesDirs.files.toList()
            opts.header(resources.filter { it.name == "javadoc_header.txt" }.singleFile.readText().trim())
            opts.addFileOption("-add-stylesheet", resources.filter { it.name == "style.css" }.singleFile)
        }

        doLast {
            project.copy {
                from(resources)
                include("copy_on_click.js")
                into(destinationDir!!.path)
            }
        }

        setDestinationDir(file("${layout.buildDirectory.get()}/docs/javadoc"))
    }

    tasks.register<Jar>("javadocJar") {
        dependsOn(tasks.named("javadocAll"))
        from(tasks.named<Javadoc>("javadocAll").get().destinationDir)

        if (project.name == rootProject.name) {
            archiveBaseName.set(archiveBaseName.get())
        } else {
            archiveBaseName.set(archiveBaseName.get() + "-" + project.name)
        }

        archiveClassifier.set("javadoc")
        archiveVersion.set("${version}+${rootProject.property("minecraft_version")}")
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
                        url.set(URL("https://maven.stardustmodding.org/dokka/releases/net/fabricmc/yarn/1.20.1+build.local/raw/yarn/"))
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
        tasks.named("javadocJar"),
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
                artifact(tasks.named("javadocJar"))
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

    dependencies {
        "minecraft"("com.mojang:minecraft:${rootProject.property("minecraft_version")}")
        "mappings"("net.fabricmc:yarn:${rootProject.property("yarn_mappings")}:v2")
    }
}
