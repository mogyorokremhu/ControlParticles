package com.controlparticles;

import java.util.LinkedHashMap;
import java.util.Map;

/** Everything the player can configure. Plain data so it serialises to JSON and can be copied into presets. */
public class Settings {
	/** Per particle type, keyed by registry id (e.g. "minecraft:crit"). */
	public Map<String, Visibility> particles = new LinkedHashMap<>();
	/** Per particle category, keyed by {@link Catalog.Category#id}. */
	public Map<String, Visibility> categories = new LinkedHashMap<>();
	/** Per mob effect (potion swirls, splash clouds...), keyed by registry id. */
	public Map<String, Visibility> effects = new LinkedHashMap<>();
	/** Per effect group, keyed by {@link Catalog.EffectGroup#id}. */
	public Map<String, Visibility> effectGroups = new LinkedHashMap<>();

	public Visibility walking = Visibility.SHOW;
	public WalkingStyle walkingStyle = WalkingStyle.BLOCK;
	public Visibility blockBreaking = Visibility.SHOW;
	public Visibility blockHitting = Visibility.SHOW;

	public Visibility rainStreaks = Visibility.SHOW;
	public Visibility snowStreaks = Visibility.SHOW;

	/** Client-side only visual effects. true = hide the effect's screen visuals. */
	public boolean hideDarkness = false;
	public boolean hideBlindness = false;
	public boolean hideNausea = false;

	public static Visibility get(Map<String, Visibility> map, String key) {
		Visibility v = map.get(key);
		return v == null ? Visibility.SHOW : v;
	}

	public static void put(Map<String, Visibility> map, String key, Visibility v) {
		if (v == Visibility.SHOW) {
			map.remove(key);
		} else {
			map.put(key, v);
		}
	}

	public Settings copy() {
		Settings s = new Settings();
		s.copyFrom(this);
		return s;
	}

	public void copyFrom(Settings o) {
		particles = new LinkedHashMap<>(o.particles);
		categories = new LinkedHashMap<>(o.categories);
		effects = new LinkedHashMap<>(o.effects);
		effectGroups = new LinkedHashMap<>(o.effectGroups);
		walking = o.walking;
		walkingStyle = o.walkingStyle;
		blockBreaking = o.blockBreaking;
		blockHitting = o.blockHitting;
		rainStreaks = o.rainStreaks;
		snowStreaks = o.snowStreaks;
		hideDarkness = o.hideDarkness;
		hideBlindness = o.hideBlindness;
		hideNausea = o.hideNausea;
	}

	/** Fixes up nulls that can appear when a hand-edited config is missing fields. */
	public void sanitize() {
		if (particles == null) particles = new LinkedHashMap<>();
		if (categories == null) categories = new LinkedHashMap<>();
		if (effects == null) effects = new LinkedHashMap<>();
		if (effectGroups == null) effectGroups = new LinkedHashMap<>();
		if (walking == null) walking = Visibility.SHOW;
		if (walkingStyle == null) walkingStyle = WalkingStyle.BLOCK;
		if (blockBreaking == null) blockBreaking = Visibility.SHOW;
		if (blockHitting == null) blockHitting = Visibility.SHOW;
		if (rainStreaks == null) rainStreaks = Visibility.SHOW;
		if (snowStreaks == null) snowStreaks = Visibility.SHOW;
		particles.values().removeIf(v -> v == null);
		categories.values().removeIf(v -> v == null);
		effects.values().removeIf(v -> v == null);
		effectGroups.values().removeIf(v -> v == null);
	}
}
