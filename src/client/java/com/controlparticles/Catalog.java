package com.controlparticles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SpellParticleOption;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

/** Static knowledge: which particles belong to which category, which effects belong to which group. */
public final class Catalog {
	public record Category(String id, String name, String description, List<String> particles) {}

	public record EffectGroup(String id, String name, List<String> effects) {}

	public static final List<Category> CATEGORIES = new ArrayList<>();
	public static final List<EffectGroup> EFFECT_GROUPS = new ArrayList<>();
	/** particle type id -> categories it belongs to */
	private static final Map<String, List<String>> TYPE_TO_CATEGORIES = new HashMap<>();
	/** effect id -> group ids */
	private static final Map<String, List<String>> EFFECT_TO_GROUPS = new HashMap<>();

	private static Map<Integer, String> effectByColor;
	private static Map<String, String> effectByParticleType;

	private static void cat(String id, String name, String description, String... particles) {
		List<String> ids = new ArrayList<>();
		for (String p : particles) {
			ids.add("minecraft:" + p);
		}
		CATEGORIES.add(new Category(id, name, description, ids));
		for (String p : ids) {
			TYPE_TO_CATEGORIES.computeIfAbsent(p, k -> new ArrayList<>()).add(id);
		}
	}

	private static void group(String id, String name, String... effects) {
		List<String> ids = new ArrayList<>();
		for (String e : effects) {
			ids.add("minecraft:" + e);
		}
		EFFECT_GROUPS.add(new EffectGroup(id, name, ids));
		for (String e : ids) {
			EFFECT_TO_GROUPS.computeIfAbsent(e, k -> new ArrayList<>()).add(id);
		}
	}

	static {
		// --- PvP / combat ---
		cat("damage_hearts", "Damage hearts (PvP)", "The little hearts that fly off when you hit something.", "damage_indicator");
		cat("crits", "Critical hit stars", "The star burst on a critical hit.", "crit");
		cat("enchant_hit", "Enchanted hit sparkles", "Blue sparkles from enchanted weapons.", "enchanted_hit");
		cat("sweep", "Sweep attack", "The white arc from sword sweeps.", "sweep_attack");
		cat("totem", "Totem of Undying", "The green burst when a totem pops.", "totem_of_undying");
		cat("explosions", "Explosions", "Explosion clouds and emitters.", "explosion", "explosion_emitter");
		cat("fireworks", "Fireworks", "Firework sparks and flashes.", "firework", "flash");
		cat("mob_poof", "Mob poof", "Puff of smoke when mobs die or spawn.", "poof");
		// --- potions ---
		cat("potion_swirls", "Potion swirls", "The swirls around entities with effects (all effects).", "entity_effect", "witch");
		cat("potion_splash", "Potion splash clouds", "Clouds from splash/lingering potions.", "effect", "instant_effect");
		// --- weather ---
		cat("rain_splash", "Rain splashes", "The little splashes where rain hits the ground.", "rain");
		// --- world ---
		cat("fire_smoke", "Fire & smoke", "Flames, smoke, campfire smoke, lava pops.", "flame", "small_flame", "soul_fire_flame", "copper_fire_flame",
			"smoke", "large_smoke", "white_smoke", "lava", "campfire_cosy_smoke", "campfire_signal_smoke");
		cat("drips", "Drips", "Dripping water, lava, honey and friends.", "dripping_water", "falling_water", "dripping_lava", "falling_lava", "landing_lava",
			"dripping_honey", "falling_honey", "landing_honey", "falling_nectar", "dripping_obsidian_tear", "falling_obsidian_tear", "landing_obsidian_tear",
			"dripping_dripstone_water", "falling_dripstone_water", "dripping_dripstone_lava", "falling_dripstone_lava");
		cat("water", "Water & bubbles", "Splashes, bubbles, bubble columns and fishing.", "splash", "bubble", "bubble_pop", "bubble_column_up",
			"current_down", "underwater", "fishing");
		cat("ambient", "Ambient world", "Ash, spores, leaves, fireflies, snow and mycelium.", "ash", "white_ash", "crimson_spore", "warped_spore",
			"spore_blossom_air", "falling_spore_blossom", "mycelium", "firefly", "snowflake", "cherry_leaves", "pale_oak_leaves", "red_poplar_leaves",
			"orange_poplar_leaves", "yellow_poplar_leaves");
		cat("portals_magic", "Portals & magic", "Portals, end rods, enchanting glyphs.", "portal", "reverse_portal", "end_rod", "enchant", "nautilus",
			"vault_connection");
		cat("emotes", "Mob emotes", "Hearts, villager moods, notes, sneezes.", "heart", "angry_villager", "happy_villager", "note", "sneeze", "composter");
		cat("block_dust", "Block dust", "All block-material particles (walking, landing, etc.).", "block", "block_marker", "dust_pillar", "block_crumble");
		cat("redstone", "Redstone & colored dust", "Redstone dust and colour-fading dust.", "dust", "dust_color_transition");
		cat("items", "Item crumbs", "Eating, breaking and thrown item particles.", "item", "item_slime", "item_cobweb", "item_snowball");
		cat("sculk", "Sculk & warden", "Sculk souls, charges, shriek and sonic boom.", "sculk_soul", "sculk_charge", "sculk_charge_pop", "shriek", "sonic_boom");
		cat("gusts", "Wind & gusts", "Wind charge and breeze gust particles.", "gust", "small_gust", "gust_emitter_large", "gust_emitter_small");
		cat("trial", "Trial chambers & omens", "Trial spawners, vaults, omens, infestation.", "trial_spawner_detection", "trial_spawner_detection_ominous",
			"vault_connection", "ominous_spawning", "trial_omen", "raid_omen", "infested");

		group("health_generation", "Health generation", "instant_health", "health_boost", "regeneration", "absorption", "saturation");
		group("harmful", "Damage & decay", "instant_damage", "poison", "wither", "hunger", "weakness", "mining_fatigue", "unluck");
		group("movement", "Movement", "speed", "slowness", "jump_boost", "levitation", "slow_falling", "dolphins_grace");
		group("combat_buffs", "Combat buffs", "strength", "resistance", "fire_resistance", "haste");
		group("vision", "Vision & stealth", "invisibility", "night_vision", "blindness", "darkness", "glowing", "nausea");
		group("utility", "Utility & luck", "water_breathing", "conduit_power", "luck", "hero_of_the_village");
		group("omens", "Omens & oddities", "bad_omen", "raid_omen", "trial_omen", "infested", "oozing", "weaving", "wind_charged");
	}

	public static List<String> categoriesOfType(String typeId) {
		return TYPE_TO_CATEGORIES.getOrDefault(typeId, List.of());
	}

	public static List<String> groupsOfEffect(String effectId) {
		return EFFECT_TO_GROUPS.getOrDefault(effectId, List.of());
	}

	private static int rgb(float r, float g, float b) {
		return (Math.round(r * 255) << 16) | (Math.round(g * 255) << 8) | Math.round(b * 255);
	}

	private static int colorOf(ParticleOptions options) {
		if (options instanceof ColorParticleOption c) {
			return rgb(c.getRed(), c.getGreen(), c.getBlue());
		}
		if (options instanceof SpellParticleOption s) {
			return rgb(s.getRed(), s.getGreen(), s.getBlue());
		}
		return -1;
	}

	public static String typeId(ParticleOptions options) {
		Identifier id = BuiltInRegistries.PARTICLE_TYPE.getKey(options.getType());
		return id == null ? "" : id.toString();
	}

	private static void buildEffectLookup() {
		effectByColor = new HashMap<>();
		effectByParticleType = new HashMap<>();
		for (MobEffect effect : BuiltInRegistries.MOB_EFFECT) {
			Identifier key = BuiltInRegistries.MOB_EFFECT.getKey(effect);
			if (key == null) continue;
			Holder<MobEffect> holder = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect);
			ParticleOptions options;
			try {
				options = effect.createParticleOptions(new MobEffectInstance(holder));
			} catch (RuntimeException e) {
				continue;
			}
			int color = colorOf(options);
			if (color >= 0) {
				effectByColor.putIfAbsent(color, key.toString());
			} else {
				effectByParticleType.putIfAbsent(typeId(options), key.toString());
			}
		}
	}

	/** Which effect (if any) produced this particle? */
	public static String effectOf(ParticleOptions options) {
		if (effectByColor == null) buildEffectLookup();
		int color = colorOf(options);
		if (color >= 0) {
			return effectByColor.get(color);
		}
		return effectByParticleType.get(typeId(options));
	}

	/** All registered effect ids, in registry order. */
	public static Map<String, MobEffect> allEffects() {
		Map<String, MobEffect> out = new LinkedHashMap<>();
		for (MobEffect effect : BuiltInRegistries.MOB_EFFECT) {
			Identifier key = BuiltInRegistries.MOB_EFFECT.getKey(effect);
			if (key != null) out.put(key.toString(), effect);
		}
		return out;
	}

	private Catalog() {}
}
