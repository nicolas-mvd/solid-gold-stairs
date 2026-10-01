package net.steveson.solidgoldstairs.mixin;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.core.Direction;
import net.steveson.solidgoldstairs.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.level.block.StairBlock.*;

@Mixin(RedStoneWireBlock.class)
public class RedstoneWireBlockMixin {
    @Inject(method = "shouldConnectTo(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;)Z", at = @At(value = "HEAD"), cancellable = true)
    private static void connectsTo(BlockState state, Direction dir, CallbackInfoReturnable<Boolean> cir) {
        if (state.is(ModBlocks.REDSTONE_STAIRS)) {
            if (state.getValue(HALF) == Half.TOP) {
                if (state.getValue(FACING) == dir) {
                    cir.setReturnValue(false);
                }
                if (state.getValue(FACING).getCounterClockWise() == dir && state.getValue(SHAPE) == StairsShape.OUTER_LEFT) {
                    cir.setReturnValue(false);
                }
                if (state.getValue(FACING).getClockWise() == dir && state.getValue(SHAPE) == StairsShape.OUTER_RIGHT) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}
