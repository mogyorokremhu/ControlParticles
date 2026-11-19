package com.controlparticles.screen;

import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ScreenEffectsScreen extends BaseScreen {
	public ScreenEffectsScreen(Screen last, Options options) {
		super(last, options, "Screen effects (client-side)");
	}

	@Override
	protected void addOptions() {
		list.addHeader(Component.literal("Only changes what you see. The effects still apply to you."));
		list.addBig(hideToggle("Darkness (Warden pulses)", "Removes the dark pulsing and fog of the Darkness effect.",
			settings().hideDarkness, v -> settings().hideDarkness = v));
		list.addBig(hideToggle("Blindness", "Removes the close-in fog of Blindness.",
			settings().hideBlindness, v -> settings().hideBlindness = v));
		list.addBig(hideToggle("Nausea", "Removes the wobbling screen of Nausea.",
			settings().hideNausea, v -> settings().hideNausea = v));
	}
}
