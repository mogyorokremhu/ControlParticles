package com.controlparticles.mixin;

import com.controlparticles.ParticleFilter;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

/** Marks the particles created while an entity sprints/walks so they can be restyled or thinned out. */
@Mixin(Entity.class)
public abstract class EntityMixin {
	@WrapMethod(method = "spawnSprintParticle")
	private void controlparticles$walking(Operation<Void> original) {
		ParticleFilter.WALKING.set(Boolean.TRUE);
		try {
			original.call();
		} finally {
			ParticleFilter.WALKING.set(Boolean.FALSE);
		}
	}
}
