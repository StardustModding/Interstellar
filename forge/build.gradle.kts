architectury {
    platformSetupLoomIde()
    forge()
}

loom {
    accessWidenerPath.set(project(":common").loom.accessWidenerPath)

    forge.apply {
        mixinConfig("interstellar-common.mixins.json")
        mixinConfig("interstellar.mixins.json")
        mixinConfig("dynamicdimensions.mixins.json")

        convertAccessWideners.set(true)
        extraAccessWideners.add(loom.accessWidenerPath.get().asFile.name)
    }
}

val common: Configuration by configurations.creating
val shadowCommon: Configuration by configurations.creating
val developmentForge: Configuration by configurations.getting

configurations {
    compileOnly.configure { extendsFrom(common) }
    runtimeOnly.configure { extendsFrom(common) }
    developmentForge.extendsFrom(common)
}

repositories {
    maven {
        name = "Kotlin for Forge"
        url = uri("https://thedarkcolour.github.io/KotlinForForge/")
    }
}

dependencies {
    forge("net.minecraftforge:forge:${rootProject.property("forge_version")}")

    common(project(":common", "namedElements")) { isTransitive = false }
    shadowCommon(project(":common", "transformProductionForge")) { isTransitive = false }

    modApi("dev.engine_room.flywheel:flywheel-forge-api-${rootProject.property("minecraft_version")}:${rootProject.property("flywheel_version")}")
    modApi("dev.architectury:architectury-forge:${rootProject.property("architectury_version")}")
    modApi("me.shedaniel.cloth:cloth-config:${rootProject.property("cloth_config_version")}")
    modApi("me.shedaniel.cloth:cloth-config-forge:${rootProject.property("cloth_config_version")}")

    modRuntimeOnly("lol.bai:badpackets:forge-${rootProject.property("badpackets_version")}")
    modRuntimeOnly("dev.engine_room.flywheel:flywheel-forge-${rootProject.property("minecraft_version")}:${rootProject.property("flywheel_version")}")
    modRuntimeOnly("me.shedaniel.cloth:cloth-config-forge:${rootProject.property("cloth_config_version")}")

    implementation("thedarkcolour:kotlinforforge:${rootProject.property("kotlin_for_forge_version")}")
}

tasks.shadowJar {
    exclude("fabric.mod.json")
    exclude("architectury.common.json")
    configurations = listOf(shadowCommon)
    archiveBaseName.set(archiveBaseName.get() + "-forge")
    archiveClassifier.set("dev-shadow")
    archiveVersion.set("${version}+${rootProject.property("minecraft_version")}")
}

tasks.remapJar {
    injectAccessWidener.set(true)
    inputFile.set(tasks.shadowJar.get().archiveFile)
    dependsOn(tasks.shadowJar)
    archiveBaseName.set(archiveBaseName.get() + "-forge")
    archiveClassifier.set(null as String?)
    archiveVersion.set("${version}+${rootProject.property("minecraft_version")}")
}

tasks.jar {
    archiveBaseName.set(archiveBaseName.get() + "-forge")
    archiveClassifier.set("dev")
    archiveVersion.set("${version}+${rootProject.property("minecraft_version")}")
}

tasks.sourcesJar {
    val commonSources = project(":common").tasks.getByName<Jar>("sourcesJar")
    dependsOn(commonSources)
    from(commonSources.archiveFile.map { zipTree(it) })
}

components.getByName("java") {
    this as AdhocComponentWithVariants
    this.withVariantsFromConfiguration(project.configurations["shadowRuntimeElements"]) {
        skip()
    }
}