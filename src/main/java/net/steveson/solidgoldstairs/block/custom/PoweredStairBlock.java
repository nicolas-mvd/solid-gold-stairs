package net.steveson.solidgoldstairs.block.custom;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.steveson.solidgoldstairs.util.ModTags;

public class PoweredStairBlock extends StairBlock {
    public PoweredStairBlock(BlockState baseBlockState, Properties settings) {
        super(baseBlockState, settings);
    }

    // Needs to be true for getDirectSignal to work. Is not directional.
    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        if (state.getValue(HALF) == Half.TOP) {
            if (direction == Direction.UP && world.getBlockState(pos.below()).is(ModTags.Blocks.LOW_REDSTONE_COMPONENTS)) {
                return 0;
            }
            if (state.getValue(FACING) == direction) {
                if (!world.getBlockState(pos.relative(direction.getOpposite())).is(ModTags.Blocks.LOW_REDSTONE_COMPONENTS)) {
                    return 11;
                }
                return 0;
            }
            if (state.getValue(SHAPE) == StairsShape.OUTER_LEFT) {
                if (state.getValue(FACING).getCounterClockWise() == direction) {
                    if (!world.getBlockState(pos.relative(direction.getOpposite())).is(ModTags.Blocks.LOW_REDSTONE_COMPONENTS)) {
                        return 11;
                    }
                    return 0;
                }
            }
            if (state.getValue(SHAPE) == StairsShape.OUTER_RIGHT) {
                if (state.getValue(FACING).getClockWise() == direction) {
                    if (!world.getBlockState(pos.relative(direction.getOpposite())).is(ModTags.Blocks.LOW_REDSTONE_COMPONENTS)) {
                        return 11;
                    }
                    return 0;
                }
            }
        }
        return 11;
    }

    @Override
    public int getDirectSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        if (world.getBlockState(pos.relative(direction.getOpposite())).getBlock() == Blocks.COMPARATOR) {
            if (state.getValue(HALF) == Half.TOP) {
                if (state.getValue(FACING) == direction) {
                    return 0;
                }
                if (state.getValue(SHAPE) == StairsShape.OUTER_LEFT) {
                    if (state.getValue(FACING).getCounterClockWise() == direction) {
                        return 0;
                    }
                }
                if (state.getValue(SHAPE) == StairsShape.OUTER_RIGHT) {
                    if (state.getValue(FACING).getClockWise() == direction) {
                        return 0;
                    }
                }
            }
            return 11;
        }
        return 0;
    }
}
