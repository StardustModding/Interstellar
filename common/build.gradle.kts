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

    modApi("dev.architectury:architectury:${rootProject.property("architectury_version")}")
    modApi("me.shedaniel.cloth:cloth-config:${rootProject.property("cloth_config_version")}")
    modImplementation("dev.galacticraft:dynamicdimensions-common:${rootProject.property("dyndims_version")}")

    include("io.wispforest:owo-sentinel:${project.property("owo_version")}")
}

tasks.processResources {
    inputs.property("version", version)
}

tasks.remapJar {
    nestedJars.setFrom()
}
