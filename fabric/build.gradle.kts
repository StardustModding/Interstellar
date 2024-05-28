import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

architectury {
    platformSetupLoomIde()
    fabric()
}

loom {
    accessWidenerPath.set(project(":common").loom.accessWidenerPath)
}

val common: Configuration by configurations.creating
val shadowCommon: Configuration by configurations.creating
val developmentFabric: Configuration by configurations.getting

configurations {
    compileOnly.configure { extendsFrom(common) }
    runtimeOnly.configure { extendsFrom(common) }
    developmentFabric.extendsFrom(common)
}

dependencies {
    modApi("net.fabricmc.fabric-api:fabric-api:${rootProject.property("fabric_api_version")}")
    compileOnly("net.fabricmc:fabric-loader:${rootProject.property("fabric_loader_version")}")

    common(project(":common", "namedElements")) { isTransitive = false }
    shadowCommon(project(":common", "transformProductionFabric")) { isTransitive = false }

    modApi("dev.architectury:architectury-fabric:${rootProject.property("architectury_version")}")
    modApi("com.terraformersmc:modmenu:${rootProject.property("modmenu_version")}")
    modApi("me.shedaniel.cloth:cloth-config:${rootProject.property("cloth_config_version")}")
    modApi("me.shedaniel.cloth:cloth-config-fabric:${rootProject.property("cloth_config_version")}")
    modApi("dev.galacticraft:dynamicdimensions-common:${rootProject.property("dyndims_version")}")
    modApi("dev.galacticraft:dynamicdimensions-fabric:${rootProject.property("dyndims_version")}")

    modImplementation("net.fabricmc:fabric-language-kotlin:${rootProject.property("fabric_kotlin_version")}")
}

tasks.processResources {
    filesMatching("fabric.mod.json") {
        expand(mapOf(
            "group" to rootProject.property("maven_group"),
            "version" to project.version,

            "mod_id" to rootProject.property("mod_id"),
            "minecraft_version" to rootProject.property("minecraft_version"),
            "architectury_version" to rootProject.property("architectury_version"),
            "fabric_kotlin_version" to rootProject.property("fabric_kotlin_version"),
            "cloth_config_version" to rootProject.property("cloth_config_version"),
        ))
    }
}

tasks.shadowJar {
    exclude("architectury.common.json")
    configurations = listOf(shadowCommon)
    archiveClassifier.set("dev-shadow")
}

tasks.remapJar {
    injectAccessWidener.set(true)
    inputFile.set(tasks.shadowJar.get().archiveFile)
    dependsOn(tasks.shadowJar)
    archiveClassifier.set(null as String?)
}

tasks.jar {
    archiveClassifier.set("dev")
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
