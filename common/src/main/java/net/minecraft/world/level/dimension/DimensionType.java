package net.minecraft.world.level.dimension;

import net.minecraft.block.Block;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.OptionalLong;

public class DimensionType extends net.minecraft.world.dimension.DimensionType {
    public DimensionType(OptionalLong fixedTime, boolean hasSkylight, boolean bl, boolean ultrawarm, boolean bl2, double coordinateScale, boolean bl3, boolean piglinSafe, int i, int j, int k, TagKey<Block> tagKey, Identifier identifier, float f, MonsterSettings monsterSettings) {
        super(fixedTime, hasSkylight, bl, ultrawarm, bl2, coordinateScale, bl3, piglinSafe, i, j, k, tagKey, identifier, f, monsterSettings);
    }
}
