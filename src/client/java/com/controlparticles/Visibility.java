package com.controlparticles;

import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.network.chat.Component;

/** How much of something should be shown. Order matters: later = more restrictive. */
public enum Visibility {
	SHOW("Shown"),
	REDUCED("Reduced"),
	HIDE("Hidden");

	private final String label;

	Visibility(String label) {
		this.label = label;
	}

	public Component text() {
		return Component.literal(label);
	}

	public Visibility or(Visibility other) {
		return other != null && other.ordinal() > ordinal() ? other : this;
	}

	/** Rolls the dice for a single particle/event. REDUCED keeps roughly one in four. */
	public boolean roll() {
		return switch (this) {
			case SHOW -> true;
			case HIDE -> false;
			case REDUCED -> ThreadLocalRandom.current().nextInt(4) == 0;
		};
	}

	public static Visibility orShow(Visibility v) {
		return v == null ? SHOW : v;
	}
}
