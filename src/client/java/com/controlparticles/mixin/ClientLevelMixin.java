package com.controlparticles.mixin;

import com.controlparticles.ConfigManager;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Block breaking / block hitting debris are spawned directly, not through createParticle. */
@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin {
	@Inject(method = "addDestroyBlockEffect", at = @At("HEAD"), cancellable = true)
	private void controlparticles$breaking(BlockPos pos, BlockState state, CallbackInfo ci) {
		if (!ConfigManager.settings().blockBreaking.roll()) {
			ci.cancel();
		}
	}

	@Inject(method = "addBreakingParticles", at = @At("HEAD"), cancellable = true)
	private void controlparticles$hitting(BlockPos pos, Direction direction, BlockState state, CallbackInfo ci) {
		if (!ConfigManager.settings().blockHitting.roll()) {
			ci.cancel();
		}
	}
}
