package com.controlparticles.screen;

import com.controlparticles.ConfigManager;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MainScreen extends BaseScreen {
	public MainScreen(Screen last, Options options) {
		super(last, options, "Control Particles");
	}

	@Override
	protected void addOptions() {
		list.addBig(open("Presets", new PresetsScreen(this, options), minecraft));
		list.addBig(open("Particle categories", new CategoriesScreen(this, options), minecraft));
		list.addBig(open("Potion & effect particles", new EffectsScreen(this, options), minecraft));
		list.addBig(open("Walking, breaking & block particles", new WalkingScreen(this, options), minecraft));
		list.addBig(open("Rain & snow", new WeatherScreen(this, options), minecraft));
		list.addBig(open("Screen effects (client-side)", new ScreenEffectsScreen(this, options), minecraft));
		list.addBig(open("Every particle type...", new AllParticlesScreen(this, options), minecraft));
		list.addBig(Button.builder(Component.literal("Reset everything to vanilla"), b -> {
			ConfigManager.applyPreset("Vanilla");
			minecraft.gui.setScreen(new MainScreen(lastScreen, options));
		}).width(310).tooltip(Tooltip.create(Component.literal("Shows every particle and effect again. Your saved presets are kept."))).build());
	}
}
