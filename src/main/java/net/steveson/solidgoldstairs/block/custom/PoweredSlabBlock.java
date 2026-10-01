package net.steveson.solidgoldstairs.block.custom;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.steveson.solidgoldstairs.util.ModTags;

public class PoweredSlabBlock extends SlabBlock {
    public PoweredSlabBlock(Properties settings) {
        super(settings);
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public int getDirectSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        if (state.getValue(TYPE) == SlabType.DOUBLE && world.getBlockState(pos.relative(direction.getOpposite())).getBlock() == Blocks.COMPARATOR) {
            return 15;
        }
        if (state.getValue(TYPE) == SlabType.BOTTOM && world.getBlockState(pos.relative(direction.getOpposite())).getBlock() == Blocks.COMPARATOR) {
            return 7;
        }
        return 0;
    }

    @Override
    public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        if (state.getValue(TYPE) == SlabType.DOUBLE) {
            return 15;
        }
        if (state.getValue(TYPE) == SlabType.BOTTOM && direction != Direction.DOWN) {
            return 7;
        }
        if (state.getValue(TYPE) == SlabType.TOP) {
            if (direction == Direction.DOWN) {
                return 7;
            }
            if (!world.getBlockState(pos.relative(direction.getOpposite())).is(ModTags.Blocks.LOW_REDSTONE_COMPONENTS)) {
                return 7;
            }
        }
        return 0;
    }
}
