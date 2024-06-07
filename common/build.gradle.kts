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
    compileOnly("com.google.code.gson:gson:2.10.1")

    modApi("dev.engine_room.flywheel:flywheel-common-intermediary-api-${rootProject.property("minecraft_version")}:${rootProject.property("flywheel_version")}")
    modApi("dev.architectury:architectury:${rootProject.property("architectury_version")}")
    modApi("me.shedaniel.cloth:cloth-config:${rootProject.property("cloth_config_version")}")
    modCompileOnly("lol.bai:badpackets:fabric-${rootProject.property("badpackets_version")}")

    include("io.wispforest:owo-sentinel:${project.property("owo_version")}")

    // PhysX
    // For some reason, IntelliJ doesn't want to resolve some of these jars.
    // It's incredibly dumb.

    implementation("de.fabmax:physx-jni:2.5.0")
//    implementation("de.fabmax:physx-jni:2.5.0:natives-windows")
    implementation("de.fabmax:physx-jni:2.5.0:natives-linux-x86_64")
    implementation("de.fabmax:physx-jni:2.5.0:natives-linux-arm64")
    implementation("de.fabmax:physx-jni:2.5.0:natives-macos-x86_64")
//    implementation("de.fabmax:physx-jni:2.5.0:natives-macos-arm64")
}

tasks.processResources {
    inputs.property("version", version)
}
