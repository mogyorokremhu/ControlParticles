package com.controlparticles;

import net.minecraft.core.particles.ParticleOptions;

/** Decides what happens to each particle the client is asked to create. */
public final class ParticleFilter {
	/** True while the game is spawning a sprint/walk particle on this thread. */
	public static final ThreadLocal<Boolean> WALKING = ThreadLocal.withInitial(() -> Boolean.FALSE);

	public static Visibility visibilityOf(ParticleOptions options) {
		Settings s = ConfigManager.settings();
		String type = Catalog.typeId(options);
		Visibility v = Settings.get(s.particles, type);
		for (String category : Catalog.categoriesOfType(type)) {
			v = v.or(s.categories.get(category));
		}
		if (!s.effects.isEmpty() || !s.effectGroups.isEmpty()) {
			String effect = Catalog.effectOf(options);
			if (effect != null) {
				v = v.or(s.effects.get(effect));
				for (String group : Catalog.groupsOfEffect(effect)) {
					v = v.or(s.effectGroups.get(group));
				}
			}
		}
		if (WALKING.get()) {
			v = v.or(s.walking);
		}
		return v;
	}

	private ParticleFilter() {}
}
