package net.steveson.solidgoldstairs.block.custom;


import net.minecraft.world.level.block.Block;

import java.util.Optional;

// This interface creates generic methods to allow the LightningBoltMixin to de-age copper on lightning strike.

public interface SteveHasWeatheringCopper {

     public Optional<Block> getPreviousBlockGeneric(Block block);

     public Optional<Block> getNextBlockGeneric(Block block);

    static Block getFirstBlock(Block pBlock) {
        Block block = pBlock;

        if (block instanceof SteveHasWeatheringCopper){
            SteveHasWeatheringCopper copperBlock = (SteveHasWeatheringCopper) block;
            for(Optional<Block> block1 = copperBlock.getPreviousBlockGeneric(pBlock); block1.isPresent(); block1 = copperBlock.getPreviousBlockGeneric(block1.get())) {
                block = block1.get();
            }
        }
        return block;
    }
}
