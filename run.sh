#!/bin/bash

set -euo pipefail

MOD_VERSION="$(cat gradle.properties | grep mod_version | cut -d '=' -f 2)"
MC_VERSION="$(cat gradle.properties | grep minecraft_version | cut -d '=' -f 2)"
LOADER_VERSION="$(cat gradle.properties | grep fabric_loader_version | cut -d '=' -f 2)"

./gradlew build
mkdir -p run
cd run

if [[ ! -f "fabric-server-launch.jar" ]]; then
    curl -fsSL \
        https://maven.fabricmc.net/net/fabricmc/fabric-installer/0.11.2//fabric-installer-0.11.2.jar \
        -o "installer.jar"

    java -jar installer.jar server \
        -dir . \
        -mcversion "$MC_VERSION" \
        -downloadMinecraft \
        -loader "$LOADER_VERSION"
fi

[[ -d mods ]] && rm -r mods
mkdir -p mods

curl -fsSL \
    "https://maven.architectury.dev/dev/architectury/architectury-fabric/6.5.77/architectury-fabric-6.5.77.jar" \
    -o "mods/architectury-fabric-6.5.77.jar"

curl -fsSL \
    "https://maven.fabricmc.net/net/fabricmc/fabric-api/fabric-api/0.76.1+1.19.2/fabric-api-0.76.1+1.19.2.jar" \
    -o "mods/fabric-api-0.76.1+1.19.2.jar"

curl -fsSL \
    "https://maven.shedaniel.me/me/shedaniel/cloth/cloth-config-fabric/8.3.103/cloth-config-fabric-8.3.103.jar" \
    -o "mods/cloth-config-fabric-8.3.103.jar"

cp -f "../build/libs/merged/interstellar-1.19.2-$MOD_VERSION.jar" mods/dev.jar

read -p "Do you agree to the Mojang EULA? (y/n) " choice

case "$choice" in 
    y|Y) echo "eula=true" > eula.txt ;;
    n|N) exit 1 ;;
    *) echo "Invalid response!" ;;
esac

java -Xmx8192M -Xms8192M -jar fabric-server-launch.jar nogui
