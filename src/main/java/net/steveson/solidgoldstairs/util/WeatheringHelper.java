package net.steveson.solidgoldstairs.util;

import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;


import java.util.Optional;
import java.util.function.Function;

// Most of this file is copied from a mod called Copper Age Backport by Smallinger, which is allowed under the MIT License.
// The rest is splicing in a method from the vanilla code to check neighboring blocks before oxidation.

/**
 * Utility class for handling custom weathering/oxidation logic.
 * Use this when blocks can't be registered in vanilla's immutable WeatheringCopper maps.
 */
public class WeatheringHelper {

    /**
     * Vanilla copper oxidation probability
     */
    public static final float OXIDATION_CHANCE = 0.05688889F;

    /**
     * Applies weathering/oxidation to a block with vanilla probability.
     *
     * @param state Current block state
     * @param level Server level
     * @param pos Block position
     * @param random RandomSource source
     * @param getNextBlock Function that returns the next oxidation stage
     * @return true if oxidation occurred, false otherwise
     */
    public static boolean tryWeather(BlockState state, ServerLevel level, BlockPos pos, RandomSource random,
                                     Function<Block, Optional<Block>> getNextBlock) {
        Optional<Block> nextBlock = getNextBlock.apply(state.getBlock());

        if (nextBlock.isPresent() && random.nextFloat() < OXIDATION_CHANCE) {
//            BlockState newState = nextBlock.get().withPropertiesOf(state);
//            level.setBlockAndUpdate(pos, newState);
            applyChangeOverTime2(state, level, pos, random, nextBlock.get());
            return true;
        }

        return false;
    }

    // this method is the one copied from the vanilla code that checks its neighbors to influence weathering probability.
    private static void applyChangeOverTime2(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom, Block nextBlock) {
        Block pBlock = pState.getBlock();

        int i;
        Enum<?> agedness;
        if (pBlock instanceof ChangeOverTimeBlock) {
            agedness = ((ChangeOverTimeBlock) pBlock).getAge();
            i = agedness.ordinal();
        }
        else {
            return;
        }
        int j = 0;
        int k = 0;

        for(BlockPos blockpos : BlockPos.withinManhattan(pPos, 4, 4, 4)) {
            int l = blockpos.distManhattan(pPos);
            if (l > 4) {
                break;
            }

            if (!blockpos.equals(pPos)) {
                BlockState blockstate = pLevel.getBlockState(blockpos);
                Block block = blockstate.getBlock();
                if (block instanceof ChangeOverTimeBlock) {
                    Enum<?> oenum = ((ChangeOverTimeBlock)block).getAge();
                    if (agedness.getClass() == oenum.getClass()) {
                        int i1 = oenum.ordinal();
                        if (i1 < i) {
                            return;
                        }

                        if (i1 > i) {
                            ++k;
                        } else {
                            ++j;
                        }
                    }
                }
            }
        }

        float f = (float)(k + 1) / (float)(k + j + 1);
        float f1 = f * f * getChanceModifier(agedness);
        if (pRandom.nextFloat() < f1) {
            BlockState newState = nextBlock.withPropertiesOf(pState);
            pLevel.setBlockAndUpdate(pPos, newState);

//            this.getNext(pState).ifPresent((p_153039_) -> {
//                pLevel.setBlockAndUpdate(pPos, p_153039_);
//            });
        }

    }

    static float getChanceModifier(Enum<?> pAge) {
        return pAge == WeatheringCopper.WeatherState.UNAFFECTED ? 0.75F : 1.0F;
    }

    /**
     * Checks if a block can weather/oxidize.
     *
     * @param state Current block state
     * @param getNextBlock Function that returns the next oxidation stage
     * @return true if the block has a next stage
     */
    public static boolean canWeather(BlockState state, Function<Block, Optional<Block>> getNextBlock) {
        return getNextBlock.apply(state.getBlock()).isPresent();
    }

    /**
     * Checks if weathering should occur (with vanilla probability).
     * Use this when you need to handle the state change yourself (e.g., for multi-block structures).
     *
     * @param state Current block state
     * @param level Server level
     * @param pos Block position
     * @param random RandomSource source
     * @return true if weathering should occur
     */
    public static boolean shouldWeather(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        return random.nextFloat() < OXIDATION_CHANCE;
    }
}
