package com.controlparticles.screen;

import com.controlparticles.ConfigManager;
import com.controlparticles.Presets;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class PresetsScreen extends BaseScreen {
	public PresetsScreen(Screen last, Options options) {
		super(last, options, "Presets");
	}

	@Override
	protected void addOptions() {
		list.addHeader(Component.literal("Built-in"));
		for (String name : Presets.builtIn().keySet()) {
			list.addBig(Button.builder(Component.literal(name), b -> apply(name)).width(310)
				.tooltip(Tooltip.create(Component.literal(Presets.DESCRIPTIONS.get(name)))).build());
		}
		list.addHeader(Component.literal("Your presets"));
		for (String name : ConfigManager.customPresets().keySet()) {
			Button apply = Button.builder(Component.literal(name), b -> apply(name)).width(150).build();
			Button delete = Button.builder(Component.literal("Delete"), b -> {
				ConfigManager.deleteCustomPreset(name);
				rebuildWidgets();
			}).width(150).build();
			list.addSmall(apply, delete);
		}
		list.addBig(Button.builder(Component.literal("Save current settings as a new preset"), b -> {
			int n = 1;
			while (ConfigManager.customPresets().containsKey("Custom " + n)) n++;
			ConfigManager.saveCustomPreset("Custom " + n);
			rebuildWidgets();
		}).width(310).tooltip(Tooltip.create(Component.literal("Saved as \"Custom N\". Use /controlparticles preset save <name> to pick a name."))).build());
	}

	private void apply(String name) {
		ConfigManager.applyPreset(name);
		minecraft.gui.setScreen(lastScreen);
	}
}
