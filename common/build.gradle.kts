import net.fabricmc.loom.task.RemapJarTask

tasks.register<RemapJarTask>("remapJar") {
    nestedJars.setFrom()
}

architectury {
    common(rootProject.property("enabled_platforms").toString().split(","))
}

loom {
    accessWidenerPath.set(file("src/main/resources/interstellar.accesswidener"))
}

dependencies {
    annotationProcessor("io.wispforest:owo-lib:${project.property("owo_version")}")
    modImplementation("io.wispforest:owo-lib:${project.property("owo_version")}")

    compileOnly("net.fabricmc:fabric-loader:${rootProject.property("fabric_loader_version")}")

    modApi("dev.engine_room.flywheel:flywheel-common-intermediary-api-${rootProject.property("minecraft_version")}:${rootProject.property("flywheel_version")}")
    modApi("dev.architectury:architectury:${rootProject.property("architectury_version")}")
    modApi("me.shedaniel.cloth:cloth-config:${rootProject.property("cloth_config_version")}")
    modCompileOnly("lol.bai:badpackets:fabric-${rootProject.property("badpackets_version")}")

    include("io.wispforest:owo-sentinel:${project.property("owo_version")}")
}

tasks.processResources {
    inputs.property("version", version)
}
