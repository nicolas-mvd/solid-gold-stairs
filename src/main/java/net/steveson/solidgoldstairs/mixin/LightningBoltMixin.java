package net.steveson.solidgoldstairs.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.block.state.BlockState;
import net.steveson.solidgoldstairs.block.custom.SteveHasWeatheringCopper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import java.util.Optional;

@Mixin(LightningBolt.class)
public class LightningBoltMixin {
    @WrapOperation(method = "clearCopperOnLightningStrike", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/WeatheringCopper;getFirst(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/state/BlockState;"))
    private static BlockState first(BlockState state, Operation<BlockState> original) {
        if (state.getBlock() instanceof SteveHasWeatheringCopper) {
            return SteveHasWeatheringCopper.getFirstBlock(state.getBlock()).withPropertiesOf(state);
        }
        return original.call(state);
    }

    @WrapOperation(method = "randomStepCleaningCopper", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/WeatheringCopper;getPrevious(Lnet/minecraft/world/level/block/state/BlockState;)Ljava/util/Optional;"))
    private static Optional<BlockState> previous(BlockState state, Operation<Optional<BlockState>> original) {
        if (state.getBlock() instanceof SteveHasWeatheringCopper copper) {
            return copper.getPreviousBlockGeneric(state.getBlock()).map(block -> block.withPropertiesOf(state));
        }
        return original.call(state);
    }
}
