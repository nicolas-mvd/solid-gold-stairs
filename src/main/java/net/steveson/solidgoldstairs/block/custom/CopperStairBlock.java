package net.steveson.solidgoldstairs.block.custom;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.steveson.solidgoldstairs.block.ModBlocks;

import java.util.Optional;

public class CopperStairBlock extends StairBlock {
    private final WeatheringCopper.WeatherState weatheringState;

    public CopperStairBlock(WeatheringCopper.WeatherState weatheringState, BlockState baseBlockState, Properties settings) {
        super(baseBlockState, settings);
        this.weatheringState = weatheringState;
    }

    public WeatheringCopper.WeatherState getWeatheringState() {
        return this.weatheringState;
    }

    /**
     * Gets the unwaxed variant of a waxed copper slab block.
     */
    public static Optional<Block> getUnwaxedBlock(Block block) {
        if (block == ModBlocks.WAXED_COPPER_STAIRS) {
            return Optional.of(ModBlocks.COPPER_STAIRS);
        } else if (block == ModBlocks.WAXED_EXPOSED_COPPER_STAIRS) {
            return Optional.of(ModBlocks.EXPOSED_COPPER_STAIRS);
        } else if (block == ModBlocks.WAXED_WEATHERED_COPPER_STAIRS) {
            return Optional.of(ModBlocks.WEATHERED_COPPER_STAIRS);
        } else if (block == ModBlocks.WAXED_OXIDIZED_COPPER_STAIRS) {
            return Optional.of(ModBlocks.OXIDIZED_COPPER_STAIRS);
        }
        return Optional.empty();
    }


    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        // Check if player is using an axe on a waxed slab - dewax it
        if (stack.is(ItemTags.AXES)) {
            Optional<Block> unwaxedBlock = getUnwaxedBlock(state.getBlock());

            if (unwaxedBlock.isPresent()) {
                world.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1, 1);
                world.levelEvent(player, 3004, pos, 0); // WAX_OFF particles

                if (!world.isClientSide()) {
                    BlockState newState = unwaxedBlock.get().withPropertiesOf(state);
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
}
