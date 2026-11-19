package com.controlparticles.mixin;

import com.controlparticles.ConfigManager;
import com.controlparticles.Settings;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Hides the screen distortion of darkness / nausea. Client side only: the effect itself is untouched. */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Inject(method = "getEffectBlendFactor", at = @At("HEAD"), cancellable = true)
	private void controlparticles$hideVisuals(Holder<MobEffect> effect, float partialTicks, CallbackInfoReturnable<Float> cir) {
		LivingEntity self = (LivingEntity) (Object) this;
		if (!self.level().isClientSide()) {
			return;
		}
		Settings s = ConfigManager.settings();
		if ((s.hideDarkness && effect.equals(MobEffects.DARKNESS)) || (s.hideNausea && effect.equals(MobEffects.NAUSEA))) {
			cir.setReturnValue(0.0F);
		}
	}
}
