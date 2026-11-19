package com.controlparticles.mixin;

import com.controlparticles.ConfigManager;
import com.controlparticles.Settings;
import net.minecraft.client.renderer.fog.environment.MobEffectFogEnvironment;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.FogType;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Removes the close-in fog of the blindness and darkness effects. */
@Mixin(MobEffectFogEnvironment.class)
public abstract class MobEffectFogEnvironmentMixin {
	@Inject(method = "isApplicable", at = @At("HEAD"), cancellable = true)
	private void controlparticles$noEffectFog(@Nullable FogType fogType, Entity entity, CallbackInfoReturnable<Boolean> cir) {
		Holder<MobEffect> effect = ((MobEffectFogEnvironment) (Object) this).getMobEffect();
		Settings s = ConfigManager.settings();
		if ((s.hideBlindness && effect.equals(MobEffects.BLINDNESS)) || (s.hideDarkness && effect.equals(MobEffects.DARKNESS))) {
			cir.setReturnValue(false);
		}
	}
}
