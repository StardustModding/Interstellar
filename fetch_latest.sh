#!/bin/bash

MC_VERSION="1.20.1"

# Forge
FORGE_PAGE="$(curl -fsSL "https://files.minecraftforge.net/net/minecraftforge/forge/index_$MC_VERSION.html")"
FORGE_INSTALLERS="$(echo "$FORGE_PAGE" | grep 'installer.jar')"
FORGE_POSTPROCESS_0="$(echo "$FORGE_INSTALLERS" | sed -E 's/^\s+//g')"
FORGE_POSTPROCESS_1="$(echo "$FORGE_POSTPROCESS_0" | sed -E 's/<br><a href="//g')"
FORGE_POSTPROCESS_2="$(echo "$FORGE_POSTPROCESS_1" | sed -E 's/">.*//g')"
FORGE_POSTPROCESS_3="$(echo "$FORGE_POSTPROCESS_2" | grep -v 'data-toggle="popup"')"
FORGE_POSTPROCESS_4="$(echo "$FORGE_POSTPROCESS_3" | grep -v 'adfoc.us')"
FORGE_POSTPROCESS_5="$(echo "$FORGE_POSTPROCESS_4" | sed -E 's/https:\/\/maven.minecraftforge.net\/net\/minecraftforge\/forge\///g')"
FORGE_POSTPROCESS_6="$(echo "$FORGE_POSTPROCESS_5" | sed -E 's/\/.*//g')"
FORGE_POSTPROCESS_7="$(echo "$FORGE_POSTPROCESS_6" | sed -E 's/.*\-//g')"
FORGE_VERSIONS_0="$(echo "$FORGE_POSTPROCESS_7" | sort | uniq)"
FORGE_LATEST_MINOR="$(echo "$FORGE_VERSIONS_0" | tail -n 1 | cut -d '.' -f 1,2)"
FORGE_LATEST_MINORS="$(echo "$FORGE_VERSIONS_0" | grep "^$FORGE_LATEST_MINOR" | sed -E "s/^$FORGE_LATEST_MINOR//g")"
FORGE_LATEST_PATCH="$(echo "$FORGE_LATEST_MINORS" | sort | uniq | tail -n 1)"
FORGE_LATEST_VERSION="$FORGE_LATEST_MINOR$FORGE_LATEST_PATCH"

# Fabric API
FABRIC_PAGE="$(curl -fsSL "https://maven.fabricmc.net/net/fabricmc/fabric-api/fabric-api/" | grep "$MC_VERSION")"
FABRIC_POSTPROCESS_0="$(echo "$FABRIC_PAGE" | sed -E 's/<a href=".*">//g')"
FABRIC_POSTPROCESS_1="$(echo "$FABRIC_POSTPROCESS_0" | sed -E 's/\/<\/a>.*//g')"
FABRIC_VERSIONS_0="$(echo "$FABRIC_POSTPROCESS_1" | cut -d '+' -f 1 | sort | uniq)"
FABRIC_LATEST_MINOR="$(echo "$FABRIC_VERSIONS_0" | tail -n 1 | cut -d '.' -f 1,2)"
FABRIC_LATEST_MINORS="$(echo "$FABRIC_VERSIONS_0" | grep "^$FABRIC_LATEST_MINOR" | sed -E "s/^$FABRIC_LATEST_MINOR//g")"
FABRIC_LATEST_PATCH="$(echo "$FABRIC_LATEST_MINORS" | sort | uniq | tail -n 1)"
FABRIC_LATEST_VERSION="$FABRIC_LATEST_MINOR$FABRIC_LATEST_PATCH"

# Fabric Loader
FABRIC_L_PAGE="$(curl -fsSL "https://maven.fabricmc.net/net/fabricmc/fabric-loader/" | grep -v '.xml' | grep -v 'build.' | grep '<a' | grep -v '<h1>')"
FABRIC_L_POSTPROCESS_0="$(echo "$FABRIC_L_PAGE" | sed -E 's/<a href=".*">//g')"
FABRIC_L_POSTPROCESS_1="$(echo "$FABRIC_L_POSTPROCESS_0" | sed -E 's/\/<\/a>.*//g' | sort | uniq)"
FABRIC_L_LATEST_MINOR="0.$(echo "$FABRIC_L_POSTPROCESS_1" | cut -d '.' -f 2 | sort -g | uniq | tail -n 1)"
FABRIC_L_LATEST_MINORS="$(echo "$FABRIC_L_POSTPROCESS_1" | grep "^$FABRIC_L_LATEST_MINOR" | sed -E "s/^$FABRIC_L_LATEST_MINOR//g" | sed 's/^.//g')"
FABRIC_L_LATEST_PATCH="$(echo "$FABRIC_L_LATEST_MINORS" | sort -g | uniq | tail -n 1)"
FABRIC_L_LATEST_VERSION="$FABRIC_L_LATEST_MINOR.$FABRIC_L_LATEST_PATCH"

echo "Forge: $MC_VERSION-$FORGE_LATEST_VERSION"
echo "Fabric: $FABRIC_LATEST_VERSION+$MC_VERSION"
echo "Fabric Loader: $FABRIC_L_LATEST_VERSION"
