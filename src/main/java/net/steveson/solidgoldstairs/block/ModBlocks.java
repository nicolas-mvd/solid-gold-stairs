package net.steveson.solidgoldstairs.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.resources.ResourceKey;

import net.minecraft.world.item.CreativeModeTab.Output;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;

import net.minecraft.world.level.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.steveson.solidgoldstairs.SolidGoldStairsMod;
import net.steveson.solidgoldstairs.block.custom.*;

public class ModBlocks {
    public static final Block COAL_STAIRS = registerBlock("coal_stairs",
            new MineralStairBlock(Blocks.COAL_BLOCK.defaultBlockState(), settings("coal_stairs", Blocks.COAL_BLOCK)));
    public static final Block COAL_SLAB = registerBlock("coal_slab",
            new SlabBlock(settings("coal_slab", Blocks.COAL_BLOCK)));

    public static final Block IRON_STAIRS = registerBlock("iron_stairs",
            new MineralStairBlock(Blocks.IRON_BLOCK.defaultBlockState(), settings("iron_stairs", Blocks.IRON_BLOCK)));
    public static final Block IRON_SLAB = registerBlock("iron_slab",
            new SlabBlock(settings("iron_slab", Blocks.IRON_BLOCK)));

    public static final Block GOLD_STAIRS = registerBlock("gold_stairs",
            new MineralStairBlock(Blocks.GOLD_BLOCK.defaultBlockState(), settings("gold_stairs", Blocks.GOLD_BLOCK)));
    public static final Block GOLD_SLAB = registerBlock("gold_slab",
            new SlabBlock(settings("gold_slab", Blocks.GOLD_BLOCK)));

    public static final Block REDSTONE_STAIRS = registerBlock("redstone_stairs",
            new PoweredStairBlock(Blocks.REDSTONE_BLOCK.defaultBlockState(), settings("redstone_stairs", Blocks.REDSTONE_BLOCK)));
    public static final Block REDSTONE_SLAB = registerBlock("redstone_slab",
            new PoweredSlabBlock(settings("redstone_slab", Blocks.REDSTONE_BLOCK)));

    public static final Block EMERALD_STAIRS = registerBlock("emerald_stairs",
            new MineralStairBlock(Blocks.EMERALD_BLOCK.defaultBlockState(), settings("emerald_stairs", Blocks.EMERALD_BLOCK)));
    public static final Block EMERALD_SLAB = registerBlock("emerald_slab",
            new SlabBlock(settings("emerald_slab", Blocks.EMERALD_BLOCK)));

    public static final Block LAPIS_STAIRS = registerBlock("lapis_stairs",
            new MineralStairBlock(Blocks.LAPIS_BLOCK.defaultBlockState(), settings("lapis_stairs", Blocks.LAPIS_BLOCK)));
    public static final Block LAPIS_SLAB = registerBlock("lapis_slab",
            new SlabBlock(settings("lapis_slab", Blocks.LAPIS_BLOCK)));

    public static final Block DIAMOND_STAIRS = registerBlock("diamond_stairs",
            new MineralStairBlock(Blocks.DIAMOND_BLOCK.defaultBlockState(), settings("diamond_stairs", Blocks.DIAMOND_BLOCK)));
    public static final Block DIAMOND_SLAB = registerBlock("diamond_slab",
            new SlabBlock(settings("diamond_slab", Blocks.DIAMOND_BLOCK)));

    public static final Block NETHERITE_STAIRS = registerBlockNetherite("netherite_stairs",
            new MineralStairBlock(Blocks.NETHERITE_BLOCK.defaultBlockState(), settings("netherite_stairs", Blocks.NETHERITE_BLOCK)));
    public static final Block NETHERITE_SLAB = registerBlockNetherite("netherite_slab",
            new SlabBlock(settings("netherite_slab", Blocks.NETHERITE_BLOCK)));

    public static final Block CHISELED_QUARTZ_STAIRS = registerBlock("chiseled_quartz_stairs",
            new MineralStairBlock(Blocks.CHISELED_QUARTZ_BLOCK.defaultBlockState(), settings("chiseled_quartz_stairs", Blocks.CHISELED_QUARTZ_BLOCK)));
    public static final Block CHISELED_QUARTZ_SLAB = registerBlock("chiseled_quartz_slab",
            new SlabBlock(settings("chiseled_quartz_slab", Blocks.CHISELED_QUARTZ_BLOCK)));

    public static final Block QUARTZ_BRICK_STAIRS = registerBlock("quartz_brick_stairs",
            new MineralStairBlock(Blocks.QUARTZ_BRICKS.defaultBlockState(), settings("quartz_brick_stairs", Blocks.QUARTZ_BRICKS)));
    public static final Block QUARTZ_BRICK_SLAB = registerBlock("quartz_brick_slab",
            new SlabBlock(settings("quartz_brick_slab", Blocks.QUARTZ_BRICKS)));

    public static final Block AMETHYST_STAIRS = registerBlock("amethyst_stairs",
            new MineralStairBlock(Blocks.AMETHYST_BLOCK.defaultBlockState(), settings("amethyst_stairs", Blocks.AMETHYST_BLOCK)));
    public static final Block AMETHYST_SLAB = registerBlock("amethyst_slab",
            new SlabBlock(settings("amethyst_slab", Blocks.AMETHYST_BLOCK)));


    public static final Block OXIDIZED_COPPER_STAIRS = registerBlock("oxidized_copper_stairs",
            new UncutWeatheringCopperStairBlock(WeatheringCopper.WeatherState.OXIDIZED,
                    Blocks.COPPER_BLOCK.weathering().oxidized().defaultBlockState(), settings("oxidized_copper_stairs", Blocks.COPPER_BLOCK.weathering().oxidized())));
    public static final Block WEATHERED_COPPER_STAIRS = registerBlock("weathered_copper_stairs",
            new UncutWeatheringCopperStairBlock(WeatheringCopper.WeatherState.WEATHERED,
                    Blocks.COPPER_BLOCK.weathering().weathered().defaultBlockState(), settings("weathered_copper_stairs", Blocks.COPPER_BLOCK.weathering().weathered())));
    public static final Block EXPOSED_COPPER_STAIRS = registerBlock("exposed_copper_stairs",
            new UncutWeatheringCopperStairBlock(WeatheringCopper.WeatherState.EXPOSED,
                    Blocks.COPPER_BLOCK.weathering().exposed().defaultBlockState(), settings("exposed_copper_stairs", Blocks.COPPER_BLOCK.weathering().exposed())));
    public static final Block COPPER_STAIRS = registerBlock("copper_stairs",
            new UncutWeatheringCopperStairBlock(WeatheringCopper.WeatherState.UNAFFECTED,
                    Blocks.COPPER_BLOCK.weathering().unaffected().defaultBlockState(), settings("copper_stairs", Blocks.COPPER_BLOCK.weathering().unaffected())));

    public static final Block WAXED_OXIDIZED_COPPER_STAIRS = registerBlock("waxed_oxidized_copper_stairs",
            new CopperStairBlock(WeatheringCopper.WeatherState.OXIDIZED,
                    Blocks.COPPER_BLOCK.waxed().oxidized().defaultBlockState(), settings("waxed_oxidized_copper_stairs", Blocks.COPPER_BLOCK.waxed().oxidized())));
    public static final Block WAXED_WEATHERED_COPPER_STAIRS = registerBlock("waxed_weathered_copper_stairs",
            new CopperStairBlock(WeatheringCopper.WeatherState.WEATHERED,
                    Blocks.COPPER_BLOCK.waxed().weathered().defaultBlockState(), settings("waxed_weathered_copper_stairs", Blocks.COPPER_BLOCK.waxed().weathered())));
    public static final Block WAXED_EXPOSED_COPPER_STAIRS = registerBlock("waxed_exposed_copper_stairs",
            new CopperStairBlock(WeatheringCopper.WeatherState.EXPOSED,
                    Blocks.COPPER_BLOCK.waxed().exposed().defaultBlockState(), settings("waxed_exposed_copper_stairs", Blocks.COPPER_BLOCK.waxed().exposed())));
    public static final Block WAXED_COPPER_STAIRS = registerBlock("waxed_copper_stairs",
            new CopperStairBlock(WeatheringCopper.WeatherState.UNAFFECTED,
                    Blocks.COPPER_BLOCK.waxed().unaffected().defaultBlockState(), settings("waxed_copper_stairs", Blocks.COPPER_BLOCK.waxed().unaffected())));

    public static final Block OXIDIZED_COPPER_SLAB = registerBlock("oxidized_copper_slab",
            new UncutWeatheringCopperSlabBlock(WeatheringCopper.WeatherState.OXIDIZED, settings("oxidized_copper_slab", Blocks.COPPER_BLOCK.weathering().oxidized())));
    public static final Block WEATHERED_COPPER_SLAB = registerBlock("weathered_copper_slab",
            new UncutWeatheringCopperSlabBlock(WeatheringCopper.WeatherState.WEATHERED, settings("weathered_copper_slab", Blocks.COPPER_BLOCK.weathering().weathered())));
    public static final Block EXPOSED_COPPER_SLAB = registerBlock("exposed_copper_slab",
            new UncutWeatheringCopperSlabBlock(WeatheringCopper.WeatherState.EXPOSED, settings("exposed_copper_slab", Blocks.COPPER_BLOCK.weathering().exposed())));
    public static final Block COPPER_SLAB = registerBlock("copper_slab",
            new UncutWeatheringCopperSlabBlock(WeatheringCopper.WeatherState.UNAFFECTED, settings("copper_slab", Blocks.COPPER_BLOCK.weathering().unaffected())));

    public static final Block WAXED_OXIDIZED_COPPER_SLAB = registerBlock("waxed_oxidized_copper_slab",
            new CopperSlabBlock(WeatheringCopper.WeatherState.OXIDIZED, settings("waxed_oxidized_copper_slab", Blocks.COPPER_BLOCK.waxed().oxidized())));
    public static final Block WAXED_WEATHERED_COPPER_SLAB = registerBlock("waxed_weathered_copper_slab",
            new CopperSlabBlock(WeatheringCopper.WeatherState.WEATHERED, settings("waxed_weathered_copper_slab", Blocks.COPPER_BLOCK.waxed().weathered())));
    public static final Block WAXED_EXPOSED_COPPER_SLAB = registerBlock("waxed_exposed_copper_slab",
            new CopperSlabBlock(WeatheringCopper.WeatherState.EXPOSED, settings("waxed_exposed_copper_slab", Blocks.COPPER_BLOCK.waxed().exposed())));
    public static final Block WAXED_COPPER_SLAB = registerBlock("waxed_copper_slab",
            new CopperSlabBlock(WeatheringCopper.WeatherState.UNAFFECTED, settings("waxed_copper_slab", Blocks.COPPER_BLOCK.waxed().unaffected())));




    private static void addItemsToBuildingBlocksItemGroup(Output entries) {
        entries.accept(COAL_STAIRS);
        entries.accept(COAL_SLAB);
        entries.accept(IRON_STAIRS);
        entries.accept(IRON_SLAB);
        entries.accept(GOLD_STAIRS);
        entries.accept(GOLD_SLAB);
        entries.accept(REDSTONE_STAIRS);
        entries.accept(REDSTONE_SLAB);
        entries.accept(EMERALD_STAIRS);
        entries.accept(EMERALD_SLAB);
        entries.accept(LAPIS_STAIRS);
        entries.accept(LAPIS_SLAB);
        entries.accept(DIAMOND_STAIRS);
        entries.accept(DIAMOND_SLAB);
        entries.accept(NETHERITE_STAIRS);
        entries.accept(NETHERITE_SLAB);
        entries.accept(CHISELED_QUARTZ_STAIRS);
        entries.accept(CHISELED_QUARTZ_SLAB);
        entries.accept(QUARTZ_BRICK_STAIRS);
        entries.accept(QUARTZ_BRICK_SLAB);
        entries.accept(AMETHYST_STAIRS);
        entries.accept(AMETHYST_SLAB);

        entries.accept(COPPER_STAIRS);
        entries.accept(COPPER_SLAB);
        entries.accept(EXPOSED_COPPER_STAIRS);
        entries.accept(EXPOSED_COPPER_SLAB);
        entries.accept(WEATHERED_COPPER_STAIRS);
        entries.accept(WEATHERED_COPPER_SLAB);
        entries.accept(OXIDIZED_COPPER_STAIRS);
        entries.accept(OXIDIZED_COPPER_SLAB);

        entries.accept(WAXED_COPPER_STAIRS);
        entries.accept(WAXED_COPPER_SLAB);
        entries.accept(WAXED_EXPOSED_COPPER_STAIRS);
        entries.accept(WAXED_EXPOSED_COPPER_SLAB);
        entries.accept(WAXED_WEATHERED_COPPER_STAIRS);
        entries.accept(WAXED_WEATHERED_COPPER_SLAB);
        entries.accept(WAXED_OXIDIZED_COPPER_STAIRS);
        entries.accept(WAXED_OXIDIZED_COPPER_SLAB);
    }
    private static void addItemsToRedstoneItemGroup(Output entries) {
        entries.accept(REDSTONE_STAIRS);
        entries.accept(REDSTONE_SLAB);
    }


    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(SolidGoldStairsMod.MOD_ID, name), block);
    }
    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(SolidGoldStairsMod.MOD_ID, name),
                new BlockItem(block, new Item.Properties().setId(ResourceKey.create(net.minecraft.core.registries.Registries.ITEM, Identifier.fromNamespaceAndPath(SolidGoldStairsMod.MOD_ID, name))).useBlockDescriptionPrefix()));
    }

    private static Block registerBlockNetherite(String name, Block block) {
        registerBlockItemNetherite(name, block);
        return Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(SolidGoldStairsMod.MOD_ID, name), block);
    }

    private static Item registerBlockItemNetherite(String name, Block block) {
        return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(SolidGoldStairsMod.MOD_ID, name),
                new BlockItem(block, new Item.Properties().setId(ResourceKey.create(net.minecraft.core.registries.Registries.ITEM, Identifier.fromNamespaceAndPath(SolidGoldStairsMod.MOD_ID, name))).useBlockDescriptionPrefix().fireResistant()));
    }


    private static BlockBehaviour.Properties settings(String name, Block base) {
        return BlockBehaviour.Properties.ofFullCopy(base).setId(ResourceKey.create(
                net.minecraft.core.registries.Registries.BLOCK,
                Identifier.fromNamespaceAndPath(SolidGoldStairsMod.MOD_ID, name)));
    }

    public static void registerModBlocks() {
        SolidGoldStairsMod.LOGGER.info("Registering ModBlocks for " + SolidGoldStairsMod.MOD_ID);

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register(ModBlocks::addItemsToBuildingBlocksItemGroup);
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.REDSTONE_BLOCKS).register(ModBlocks::addItemsToRedstoneItemGroup);
    }
}
