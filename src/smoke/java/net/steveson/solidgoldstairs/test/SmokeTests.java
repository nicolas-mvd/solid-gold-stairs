package net.steveson.solidgoldstairs.test;

import com.mojang.authlib.GameProfile;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.steveson.solidgoldstairs.block.ModBlocks;
import net.steveson.solidgoldstairs.block.custom.UncutWeatheringCopperSlabBlock;
import net.steveson.solidgoldstairs.block.custom.UncutWeatheringCopperStairBlock;
import net.steveson.solidgoldstairs.util.WeatheringHelper;
import org.slf4j.LoggerFactory;
import java.util.UUID;
import java.util.List;

public class SmokeTests implements ModInitializer {
    private int assertions;
    private void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
        assertions++;
    }

    @Override public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            // Defense against accidentally loading the test JAR on a live server.
            var root = server.getWorldPath(LevelResource.ROOT).toAbsolutePath().normalize();
            if (!root.toString().startsWith("/var/tmp/sgs-test-")) {
                throw new IllegalStateException("Integration tests require /var/tmp/sgs-test-*; refusing " + root);
            }
            try {
                run(server);
                LoggerFactory.getLogger("solid_gold_stairs_smoke").info("SGS_SMOKE_PASS: {} assertions", assertions);
            } catch (Throwable error) {
                LoggerFactory.getLogger("solid_gold_stairs_smoke").error("SGS_SMOKE_FAIL", error);
            }
            server.halt(false);
        });
    }

    private void run(MinecraftServer server) throws Exception {
        ServerLevel level = server.overworld();
        BlockPos pos = new BlockPos(0, 300, 0);
        long count = BuiltInRegistries.BLOCK.keySet().stream().filter(id -> id.getNamespace().equals("solid_gold_stairs")).count();
        check(count == 38, "38 registered blocks");
        long recipes = server.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.id().identifier().getNamespace().equals("solid_gold_stairs")).count();
        check(recipes == 84, "84 recipes, got " + recipes);
        for (var id : BuiltInRegistries.BLOCK.keySet()) {
            if (!id.getNamespace().equals("solid_gold_stairs")) continue;
            Block block = BuiltInRegistries.BLOCK.getValue(id);
            var state = block.defaultBlockState();
            check(state.is(BlockTags.MINEABLE_WITH_PICKAXE), id + " mining tag");
            check(new ItemStack(block).getItem() != Items.AIR, id + " block item");
            for (String category : List.of("crafting", "stonecutting")) {
                var key = ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath("solid_gold_stairs", category + "/" + id.getPath()));
                check(server.getRecipeManager().byKey(key).isPresent(), id + " " + category);
            }
            int expected = 1;
            if (block instanceof SlabBlock) {
                state = state.setValue(SlabBlock.TYPE, SlabType.DOUBLE); expected = 2;
            }
            var drops = Block.getDrops(state, level, pos, null);
            check(drops.size() == 1 && drops.getFirst().is(block.asItem()) && drops.getFirst().getCount() == expected, id + " loot");
            level.setBlockAndUpdate(pos, state);
            check(level.getBlockState(pos).equals(state), id + " placement/state preservation");
        }
        check(server.fuelValues().burnDuration(new ItemStack(ModBlocks.COAL_STAIRS)) == 16000, "coal stair fuel");
        check(server.fuelValues().burnDuration(new ItemStack(ModBlocks.COAL_SLAB)) == 8000, "coal slab fuel");
        check(FlammableBlockRegistry.getDefaultInstance().get(ModBlocks.COAL_STAIRS).getBurnOdds() == 5, "coal stairs burn");
        check(FlammableBlockRegistry.getDefaultInstance().get(ModBlocks.COAL_SLAB).getIgniteOdds() == 5, "coal slabs ignite");
        check(ModBlocks.NETHERITE_STAIRS.getExplosionResistance() == Blocks.NETHERITE_BLOCK.getExplosionResistance(), "netherite stair blast resistance");
        check(ModBlocks.NETHERITE_SLAB.getExplosionResistance() == Blocks.NETHERITE_BLOCK.getExplosionResistance(), "netherite slab blast resistance");
        var lava = level.damageSources().lava();
        check(!new ItemStack(ModBlocks.NETHERITE_STAIRS).canBeHurtBy(lava), "netherite stair item lava immunity");
        check(!new ItemStack(ModBlocks.NETHERITE_SLAB).canBeHurtBy(lava), "netherite slab item lava immunity");

        var stairs = ModBlocks.REDSTONE_STAIRS.defaultBlockState();
        level.setBlockAndUpdate(pos, stairs);
        check(stairs.getSignal(level, pos, Direction.NORTH) == 11, "redstone stairs signal 11");
        var slab = ModBlocks.REDSTONE_SLAB.defaultBlockState();
        check(slab.getSignal(level, pos, Direction.NORTH) == 7, "redstone slab signal 7");
        check(slab.setValue(SlabBlock.TYPE, SlabType.DOUBLE).getSignal(level, pos, Direction.NORTH) == 15, "double slab signal 15");
        check(slab.setValue(SlabBlock.TYPE, SlabType.TOP).getSignal(level, pos, Direction.DOWN) == 7, "top slab signal 7");
        check(slab.setValue(SlabBlock.TYPE, SlabType.TOP).isSignalSource(), "top slab emits power");
        level.setBlockAndUpdate(pos.below().below(), Blocks.STONE.defaultBlockState());
        level.setBlockAndUpdate(pos.below(), Blocks.REDSTONE_WIRE.defaultBlockState());
        check(stairs.setValue(StairBlock.HALF, Half.TOP).getSignal(level, pos, Direction.UP) == 0, "top stairs avoid non-touching dust below");
        level.setBlockAndUpdate(pos.below(), Blocks.AIR.defaultBlockState());
        level.setBlockAndUpdate(pos.below().below(), Blocks.AIR.defaultBlockState());

        ServerPlayer player = new ServerPlayer(server, level, new GameProfile(UUID.randomUUID(), "SGSSmoke"), ClientInformation.createDefault());
        var hit = new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, false);
        for (Block[] chain : new Block[][] {
            {ModBlocks.COPPER_SLAB, ModBlocks.EXPOSED_COPPER_SLAB, ModBlocks.WEATHERED_COPPER_SLAB, ModBlocks.OXIDIZED_COPPER_SLAB,
             ModBlocks.WAXED_COPPER_SLAB, ModBlocks.WAXED_EXPOSED_COPPER_SLAB, ModBlocks.WAXED_WEATHERED_COPPER_SLAB, ModBlocks.WAXED_OXIDIZED_COPPER_SLAB},
            {ModBlocks.COPPER_STAIRS, ModBlocks.EXPOSED_COPPER_STAIRS, ModBlocks.WEATHERED_COPPER_STAIRS, ModBlocks.OXIDIZED_COPPER_STAIRS,
             ModBlocks.WAXED_COPPER_STAIRS, ModBlocks.WAXED_EXPOSED_COPPER_STAIRS, ModBlocks.WAXED_WEATHERED_COPPER_STAIRS, ModBlocks.WAXED_OXIDIZED_COPPER_STAIRS}
        }) {
            for (int stage = 0; stage < 4; stage++) {
                var state = chain[stage].defaultBlockState();
                if (state.getBlock() instanceof SlabBlock) state = state.setValue(SlabBlock.TYPE, SlabType.TOP).setValue(SlabBlock.WATERLOGGED, true);
                else state = state.setValue(StairBlock.HALF, Half.TOP).setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.WATERLOGGED, true);
                level.setBlockAndUpdate(pos, state);
                var honeycomb = new ItemStack(Items.HONEYCOMB, 2);
                player.setItemInHand(InteractionHand.MAIN_HAND, honeycomb);
                check(state.useItemOn(honeycomb, level, player, InteractionHand.MAIN_HAND, hit).consumesAction(), "wax action");
                var waxed = level.getBlockState(pos);
                check(waxed.equals(chain[stage + 4].withPropertiesOf(state)), "wax preserves orientation/waterlogging");
                check(honeycomb.getCount() == 1, "wax consumes honeycomb");
                var axe = new ItemStack(Items.IRON_AXE);
                player.setItemInHand(InteractionHand.MAIN_HAND, axe);
                check(waxed.useItemOn(axe, level, player, InteractionHand.MAIN_HAND, hit).consumesAction(), "unwax action");
                check(level.getBlockState(pos).equals(state), "unwax preserves state");
                check(axe.getDamageValue() == 1, "unwax damages axe");
                if (stage > 0) {
                    state.useItemOn(axe, level, player, InteractionHand.MAIN_HAND, hit);
                    check(level.getBlockState(pos).equals(chain[stage - 1].withPropertiesOf(state)), "scrape one stage");
                    check(axe.getDamageValue() == 2, "scrape damages axe");
                }
                if (stage < 3) {
                    level.setBlockAndUpdate(pos, state);
                    for (int attempt = 0; attempt < 1000 && level.getBlockState(pos).is(chain[stage]); attempt++) {
                        if (chain[stage] instanceof UncutWeatheringCopperSlabBlock) WeatheringHelper.tryWeather(state, level, pos, level.getRandom(), UncutWeatheringCopperSlabBlock::getNextBlock);
                        else WeatheringHelper.tryWeather(state, level, pos, level.getRandom(), UncutWeatheringCopperStairBlock::getNextBlock);
                    }
                    check(level.getBlockState(pos).equals(chain[stage + 1].withPropertiesOf(state)), "oxidation preserves state");
                }
                level.setBlockAndUpdate(pos, state);
                var lightning = LightningBolt.class.getDeclaredMethod("clearCopperOnLightningStrike", Level.class, BlockPos.class);
                lightning.setAccessible(true); lightning.invoke(null, level, pos);
                check(level.getBlockState(pos).equals(chain[0].withPropertiesOf(state)), "lightning restores first stage");
            }
        }
        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
    }
}
