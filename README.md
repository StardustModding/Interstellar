# Interstellar

A space-focused Minecraft tech mod, supporting Fabric, Forge, Quilt, and NeoForge,
with a massive amount of content and things to explore, and a heavy focus on ultra-high
quality, and ultimate customization. Build your own ship, build your own space station,
manage resources, terraform planets, upgrade your suit, battle your friends in the vast
emptiness of space, and so much more.

> ***Warning*** \
> This mod is a work-in-progress! There will be many bugs and missing features!

> ***Note*** \
> This is NOT related to @doctor4t's *Reach for the Stars* mod, nor @CreateSimilated's *Create: Liftoff* mod! This is an ORIGINAL idea that we came up with before we had heard of any of those mods. I wish I didn't have to say this, but I don't want anyone getting mad. :p

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
