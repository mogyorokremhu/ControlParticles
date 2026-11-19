package com.controlparticles;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;

/** What the sprinting / walking particles are made of. */
public enum WalkingStyle {
	BLOCK("Block break particles (vanilla)", null),
	CLOUD("White clouds", ParticleTypes.CLOUD),
	POOF("Poof puffs", ParticleTypes.POOF),
	DUST_PLUME("Dust plume", ParticleTypes.DUST_PLUME);

	private final String label;
	private final ParticleOptions replacement;

	WalkingStyle(String label, ParticleOptions replacement) {
		this.label = label;
		this.replacement = replacement;
	}

	public Component text() {
		return Component.literal(label);
	}

	/** The particle to use instead of the vanilla one, or null to keep vanilla. */
	public ParticleOptions replacement() {
		return replacement;
	}
}
