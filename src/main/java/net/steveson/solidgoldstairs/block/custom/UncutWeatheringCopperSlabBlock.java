package net.steveson.solidgoldstairs.block.custom;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.steveson.solidgoldstairs.block.ModBlocks;
import net.steveson.solidgoldstairs.util.WeatheringHelper;

import java.util.Optional;

public class UncutWeatheringCopperSlabBlock extends SlabBlock implements WeatheringCopper, SteveHasWeatheringCopper {

    private final WeatheringCopper.WeatherState weatheringState;

    public UncutWeatheringCopperSlabBlock(WeatherState weatheringState, Properties settings) {
        super(settings);
        this.weatheringState = weatheringState;
    }

    /**
     * Provides "our own" oxidation chain for copper slabs.
     */
    public static Optional<Block> getNextBlock(Block block) {
        if (block == ModBlocks.COPPER_SLAB) {
            return Optional.of(ModBlocks.EXPOSED_COPPER_SLAB);
        } else if (block == ModBlocks.EXPOSED_COPPER_SLAB) {
            return Optional.of(ModBlocks.WEATHERED_COPPER_SLAB);
        } else if (block == ModBlocks.WEATHERED_COPPER_SLAB) {
            return Optional.of(ModBlocks.OXIDIZED_COPPER_SLAB);
        }
        return Optional.empty();
    }

    /**
     * Gets the previous oxidation stage (for scraping with axe).
     */
    public static Optional<Block> getPreviousBlock(Block block) {
        if (block == ModBlocks.EXPOSED_COPPER_SLAB) {
            return Optional.of(ModBlocks.COPPER_SLAB);
        } else if (block == ModBlocks.WEATHERED_COPPER_SLAB) {
            return Optional.of(ModBlocks.EXPOSED_COPPER_SLAB);
        } else if (block == ModBlocks.OXIDIZED_COPPER_SLAB) {
            return Optional.of(ModBlocks.WEATHERED_COPPER_SLAB);
        }
        return Optional.empty();
    }

    /**
     * Gets the waxed variant of a copper slab.
     */
    public static Optional<Block> getWaxedBlock(Block block) {
        if (block == ModBlocks.COPPER_SLAB) {
            return Optional.of(ModBlocks.WAXED_COPPER_SLAB);
        } else if (block == ModBlocks.EXPOSED_COPPER_SLAB) {
            return Optional.of(ModBlocks.WAXED_EXPOSED_COPPER_SLAB);
        } else if (block == ModBlocks.WEATHERED_COPPER_SLAB) {
            return Optional.of(ModBlocks.WAXED_WEATHERED_COPPER_SLAB);
        } else if (block == ModBlocks.OXIDIZED_COPPER_SLAB) {
            return Optional.of(ModBlocks.WAXED_OXIDIZED_COPPER_SLAB);
        }
        return Optional.empty();
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        // Check if player is using an axe on a waxed slab - dewax it
        if (stack.is(Items.HONEYCOMB)) {
            Optional<Block> waxedBlock = getWaxedBlock(state.getBlock());

            if (waxedBlock.isPresent()) {
                world.playSound(player, pos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1, 1);
                world.levelEvent(player, 3003, pos, 0); // WAX_ON particles

                if (!world.isClientSide()) {
                    BlockState newState = waxedBlock.get().withPropertiesOf(state);
                    world.setBlockAndUpdate(pos, newState);
                    if (!player.isCreative()) {
                        stack.shrink(1);
                    }
                }

                return InteractionResult.SUCCESS;
            }
        }

        // Check if player is using an axe on a waxed slab - dewax it
        if (stack.is(ItemTags.AXES)) {
            Optional<Block> previousBlock = getPreviousBlock(state.getBlock());

            if (previousBlock.isPresent()) {
                world.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1, 1);
                world.levelEvent(player, 3005, pos, 0); // SCRAPE particles

                if (!world.isClientSide()) {
                    BlockState newState = previousBlock.get().withPropertiesOf(state);
                    world.setBlockAndUpdate(pos, newState);
                    if (!player.isCreative()) {
                        stack.hurtAndBreak(1, player, hand);
                    }
                }

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        WeatheringHelper.tryWeather(state, world, pos, random, UncutWeatheringCopperSlabBlock::getNextBlock);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return WeatheringHelper.canWeather(state, UncutWeatheringCopperSlabBlock::getNextBlock);
    }

    @Override
    public WeatherState getAge() {
        return getWeatheringState();
    }

    public WeatheringCopper.WeatherState getWeatheringState() {
        return this.weatheringState;
    }

    @Override
    public Optional<Block> getPreviousBlockGeneric(Block block) {
        return getPreviousBlock(block);
    }

    @Override
    public Optional<Block> getNextBlockGeneric(Block block) {
        return getNextBlock(block);
    }
}
