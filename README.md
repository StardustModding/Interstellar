# Interstellar

A space-focused Minecraft tech mod, supporting Fabric, Forge, Quilt, and NeoForge,
with a massive amount of content and things to explore, and a heavy focus on ultra-high
quality, and ultimate customization. Build your own ship, build your own space station,
manage resources, terraform planets, upgrade your suit, battle your friends in the vast
emptiness of space, and so much more.

> ***Warning*** \
> This mod is a work-in-progress! There will be many bugs and missing features!

## API

We have an API for addon development! To use it, follow these instructions:

Add our Maven repo:
```kts
repositories {
    maven {
        name = "Stardust Modding"
        url = uri("https://maven.stardustmodding.org/snapshots/")
    }
}
```

And then add the API:

<details>
<summary>Fabric</summary>

```kts
dependencies {
    modApi("org.stardustmodding.interstellar:interstellar-fabric:${rootProject.property("interstellar_version")}")
}
```
</details>

<details>
<summary>Forge</summary>

```kts
dependencies {
    compileOnly(fg.deobf("org.stardustmodding.interstellar:interstellar-forge:${rootProject.property("interstellar_version")}"))
}
```
</details>

<details>
<summary>Common (Architectury)</summary>

```kts
dependencies {
    modApi("org.stardustmodding.interstellar:interstellar-common:${rootProject.property("interstellar_version")}")
}
```
</details>
