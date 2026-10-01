package net.steveson.solidgoldstairs.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.Identifier;
import net.steveson.solidgoldstairs.SolidGoldStairsMod;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> LOW_REDSTONE_COMPONENTS = tag("low_redstone_components");

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(SolidGoldStairsMod.MOD_ID, name));

        }
    }
}
