package net.steveson.solidgoldstairs.block.custom;

import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

/** Exposes the protected vanilla stair constructor for mineral variants. */
public class MineralStairBlock extends StairBlock {
    public MineralStairBlock(BlockState baseState, Properties properties) {
        super(baseState, properties);
    }
}
