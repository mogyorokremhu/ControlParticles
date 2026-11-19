package com.controlparticles.mixin;

import com.controlparticles.ParticleFilter;
import com.controlparticles.ConfigManager;
import com.controlparticles.WalkingStyle;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;

/** The single choke point every normal particle goes through. */
@Mixin(ParticleEngine.class)
public abstract class ParticleEngineMixin {
	@WrapMethod(method = "createParticle")
	private Particle controlparticles$filter(ParticleOptions options, double x, double y, double z, double xa, double ya, double za,
		Operation<Particle> original) {
		if (!ParticleFilter.visibilityOf(options).roll()) {
			return null;
		}
		if (ParticleFilter.WALKING.get() && options.getType() == ParticleTypes.BLOCK) {
			WalkingStyle style = ConfigManager.settings().walkingStyle;
			if (style.replacement() != null) {
				options = style.replacement();
			}
		}
		return original.call(options, x, y, z, xa, ya, za);
	}
}
