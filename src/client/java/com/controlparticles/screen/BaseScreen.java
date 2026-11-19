package com.controlparticles.screen;

import com.controlparticles.ConfigManager;
import com.controlparticles.Settings;
import com.controlparticles.Visibility;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.Component;

/** Shared plumbing for all the settings screens. */
public abstract class BaseScreen extends OptionsSubScreen {
	protected BaseScreen(Screen last, Options options, String title) {
		super(last, options, Component.literal(title));
	}

	@Override
	public void removed() {
		ConfigManager.save();
		super.removed();
	}

	protected static Settings settings() {
		return ConfigManager.settings();
	}

	protected static CycleButton<Visibility> visibility(String name, String tooltip, Visibility current, Consumer<Visibility> setter) {
		CycleButton.Builder<Visibility> builder = CycleButton.builder(Visibility::text, current).withValues(Visibility.values());
		if (tooltip != null && !tooltip.isEmpty()) {
			Tooltip t = Tooltip.create(Component.literal(tooltip));
			builder.withTooltip(v -> t);
		}
		return builder.create(Component.literal(name), (button, value) -> setter.accept(value));
	}

	/** A button bound to one entry of a settings map; SHOW removes the entry again to keep the config tidy. */
	protected static CycleButton<Visibility> mapEntry(String name, String tooltip, Map<String, Visibility> map, String key) {
		return visibility(name, tooltip, Settings.get(map, key), v -> Settings.put(map, key, v));
	}

	protected static CycleButton<Boolean> hideToggle(String name, String tooltip, boolean current, Consumer<Boolean> setter) {
		Tooltip t = Tooltip.create(Component.literal(tooltip));
		return CycleButton.booleanBuilder(Component.literal("Hidden"), Component.literal("Shown"), current)
			.withTooltip(v -> t)
			.create(Component.literal(name), (button, value) -> setter.accept(value));
	}

	protected static Button open(String label, Screen screen, net.minecraft.client.Minecraft mc) {
		return Button.builder(Component.literal(label), b -> mc.gui.setScreen(screen)).width(310).build();
	}

	/** "minecraft:some_thing" -> "Some Thing" (other namespaces keep their prefix). */
	public static String pretty(String id) {
		int colon = id.indexOf(':');
		String ns = colon < 0 ? "minecraft" : id.substring(0, colon);
		String path = colon < 0 ? id : id.substring(colon + 1);
		StringBuilder sb = new StringBuilder();
		for (String part : path.split("[_/]")) {
			if (part.isEmpty()) continue;
			if (sb.length() > 0) sb.append(' ');
			sb.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
		}
		return ns.equals("minecraft") ? sb.toString() : ns + ": " + sb;
	}
}
