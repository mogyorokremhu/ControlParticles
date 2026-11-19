package com.controlparticles;

import java.util.LinkedHashMap;
import java.util.Map;

/** Built-in presets. Custom presets live in the config file. */
public final class Presets {
	public static final Map<String, String> DESCRIPTIONS = new LinkedHashMap<>();
	private static final Map<String, Settings> BUILT_IN = new LinkedHashMap<>();

	private static Settings make(String name, String description) {
		Settings s = new Settings();
		BUILT_IN.put(name, s);
		DESCRIPTIONS.put(name, description);
		return s;
	}

	private static void hide(Settings s, Visibility v, String... categories) {
		for (String c : categories) {
			s.categories.put(c, v);
		}
	}

	static {
		make("Vanilla", "Everything exactly like the base game.");

		Settings pvp = make("PvP Clean", "No damage hearts, crits, sweeps or enchant sparkles; calmer potion swirls.");
		hide(pvp, Visibility.HIDE, "damage_hearts", "crits", "enchant_hit", "sweep");
		hide(pvp, Visibility.REDUCED, "potion_swirls", "mob_poof");
		pvp.walking = Visibility.REDUCED;

		Settings perf = make("Performance", "Thins out the particles that cost the most frames.");
		hide(perf, Visibility.REDUCED, "ambient", "fire_smoke", "drips", "potion_swirls", "potion_splash", "explosions", "fireworks", "block_dust", "water", "gusts");
		hide(perf, Visibility.HIDE, "rain_splash");
		perf.walking = Visibility.REDUCED;
		perf.rainStreaks = Visibility.REDUCED;
		perf.snowStreaks = Visibility.REDUCED;
		perf.blockBreaking = Visibility.REDUCED;

		Settings weather = make("No Weather Clutter", "Rain and snow streaks and rain splashes gone.");
		hide(weather, Visibility.HIDE, "rain_splash");
		weather.rainStreaks = Visibility.HIDE;
		weather.snowStreaks = Visibility.HIDE;

		Settings screen = make("Clear Screen", "No darkness, blindness or nausea visuals (client-side only).");
		screen.hideDarkness = true;
		screen.hideBlindness = true;
		screen.hideNausea = true;

		Settings calm = make("Calm", "Quiet world: no walking dust, weather, potion swirls or ambient particles.");
		hide(calm, Visibility.HIDE, "potion_swirls", "rain_splash", "ambient", "drips", "mob_poof", "emotes", "damage_hearts");
		hide(calm, Visibility.REDUCED, "fire_smoke", "block_dust", "water", "crits", "sweep");
		calm.walking = Visibility.HIDE;
		calm.rainStreaks = Visibility.REDUCED;
		calm.snowStreaks = Visibility.REDUCED;

		Settings minimal = make("Minimal", "Almost every particle and visual effect off.");
		for (Catalog.Category c : Catalog.CATEGORIES) {
			minimal.categories.put(c.id(), Visibility.HIDE);
		}
		for (Catalog.EffectGroup g : Catalog.EFFECT_GROUPS) {
			minimal.effectGroups.put(g.id(), Visibility.HIDE);
		}
		minimal.walking = Visibility.HIDE;
		minimal.blockHitting = Visibility.HIDE;
		minimal.rainStreaks = Visibility.HIDE;
		minimal.snowStreaks = Visibility.HIDE;
		minimal.hideDarkness = true;
		minimal.hideBlindness = true;
		minimal.hideNausea = true;
	}

	public static Map<String, Settings> builtIn() {
		return BUILT_IN;
	}

	private Presets() {}
}
