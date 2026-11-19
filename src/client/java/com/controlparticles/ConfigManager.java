package com.controlparticles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import net.fabricmc.loader.api.FabricLoader;

public final class ConfigManager {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("controlparticles.json");

	private static class FileData {
		Settings settings = new Settings();
		Map<String, Settings> customPresets = new LinkedHashMap<>();
	}

	private static FileData data = new FileData();

	public static Settings settings() {
		return data.settings;
	}

	public static Map<String, Settings> customPresets() {
		return data.customPresets;
	}

	public static void load() {
		if (!Files.exists(FILE)) {
			save();
			return;
		}
		try (Reader reader = Files.newBufferedReader(FILE)) {
			FileData loaded = GSON.fromJson(reader, FileData.class);
			if (loaded != null) {
				if (loaded.settings == null) loaded.settings = new Settings();
				if (loaded.customPresets == null) loaded.customPresets = new LinkedHashMap<>();
				loaded.settings.sanitize();
				loaded.customPresets.values().forEach(Settings::sanitize);
				data = loaded;
			}
		} catch (IOException | RuntimeException e) {
			ControlParticlesClient.LOGGER.error("Could not read {}, using defaults", FILE, e);
		}
	}

	public static void save() {
		try {
			Files.createDirectories(FILE.getParent());
			try (Writer writer = Files.newBufferedWriter(FILE)) {
				GSON.toJson(data, writer);
			}
		} catch (IOException e) {
			ControlParticlesClient.LOGGER.error("Could not write {}", FILE, e);
		}
	}

	/** Every preset by name, built-in first. */
	public static Map<String, Settings> allPresets() {
		Map<String, Settings> all = new LinkedHashMap<>(Presets.builtIn());
		all.putAll(data.customPresets);
		return all;
	}

	public static boolean isBuiltIn(String name) {
		return Presets.builtIn().containsKey(name);
	}

	public static boolean applyPreset(String name) {
		Settings p = allPresets().get(name);
		if (p == null) return false;
		data.settings.copyFrom(p);
		save();
		return true;
	}

	/** @return false if the name clashes with a built-in preset */
	public static boolean saveCustomPreset(String name) {
		if (isBuiltIn(name)) return false;
		data.customPresets.put(name, data.settings.copy());
		save();
		return true;
	}

	public static boolean deleteCustomPreset(String name) {
		boolean removed = data.customPresets.remove(name) != null;
		if (removed) save();
		return removed;
	}

	private ConfigManager() {}
}
